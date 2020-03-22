package com.example.busguide;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteLine;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.example.busguide.adapter.RoutePlanAdapter;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class RoutePlanActivity extends AppCompatActivity {

    private EditText city;
    private EditText start;
    private EditText end;
    private RecyclerView routeplan;
    private RoutePlanSearch mSearch;
    private StringBuilder msg;
    private StringBuilder tempbustline;
    private List<String> routeList=new ArrayList<>();
    private RoutePlanAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        LitePal.getDatabase();
        start=findViewById(R.id.start);
        end=findViewById(R.id.end);
        city=findViewById(R.id.city);
        routeplan=findViewById(R.id.all_route);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(listener);
        routeplan.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        routeplan.setLayoutManager(new LinearLayoutManager(this));
        adapter=new RoutePlanAdapter(routeList);
        routeplan.setAdapter(adapter);
    }

    public void onClick(View view){
        PlanNode stNode = PlanNode.withCityNameAndPlaceName(city.getText().toString(),start.getText().toString());
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(city.getText().toString(),end.getText().toString());
        mSearch.transitSearch((new TransitRoutePlanOption())
                .from(stNode)
                .to(enNode)
                .city(city.getText().toString()));
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
            routeList.clear();
            if (transitRouteResult == null || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(RoutePlanActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                AlertDialog.Builder builder = new AlertDialog.Builder(RoutePlanActivity.this);
                builder.setTitle("提示");
                builder.setMessage("检索地址有歧义，请重新设置。");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return;
            }
            msg=new StringBuilder();
            tempbustline=new StringBuilder();
            if (transitRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                int i=1;
                int temp=0;
                int min=9999999;
                String bestline = null;//最短路径历史记录
                List<TransitRouteLine> transitRouteLines = transitRouteResult.getRouteLines();//获取规划路线列表
                for(TransitRouteLine transitRouteLine : transitRouteLines){
                    msg.append("乘车方案").append(i).append(":").append("线路总长：").append(transitRouteLine.getDistance()).append("米。");
                    tempbustline.append("起点：").append(start.getText().toString()).append("  ").append("终点:").append(end.getText().toString()).append("。").append("线路总长：").append(transitRouteLine.getDistance()).append("米。");
                    temp=transitRouteLine.getDistance();//该路线距离
                    List<TransitRouteLine.TransitStep> transitSteps = transitRouteLine.getAllStep();
                    for (TransitRouteLine.TransitStep transitStep : transitSteps) {
                        msg.append(transitStep.getInstructions());
                        tempbustline.append(transitStep.getInstructions());
                    }
                    if(temp<min) bestline=tempbustline.toString();
                    routeList.add(msg.toString());
                    i+=1;
                    msg.setLength(0);
                    tempbustline.setLength(0);
                    adapter.notifyDataSetChanged();
                }
                RoutePlanHistory routePlanHistory=new RoutePlanHistory();
                routePlanHistory.setRoute(bestline);
                routePlanHistory.save();
            }
        }

        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
    }
}
