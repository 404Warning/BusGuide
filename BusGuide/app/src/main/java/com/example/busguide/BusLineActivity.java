package com.example.busguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.busguide.adapter.BusLineAdapter;

import java.util.ArrayList;
import java.util.List;

public class BusLineActivity extends Activity implements OnGetBusLineSearchResultListener,OnGetPoiSearchResultListener{

    private EditText city;
    private EditText busline;
    private RecyclerView busmsg;
    private PoiSearch mPoiSearch;
    private BusLineSearch mBusLineSearch;
    private String ETcity;
    private String ETbusline;
    private List<String> buslineList=new ArrayList<>();
    private BusLineAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_line);
        city=findViewById(R.id.city);
        busline=findViewById(R.id.busline);
        busmsg=findViewById(R.id.busmsg);
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        mBusLineSearch=BusLineSearch.newInstance();
        mBusLineSearch.setOnGetBusLineSearchResultListener(this);
        busmsg.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        busmsg.setLayoutManager(new LinearLayoutManager(this));
        adapter=new BusLineAdapter(buslineList);
        busmsg.setAdapter(adapter);
    }

    public void onClick(View view){
        ETcity=city.getText().toString();
        ETbusline=busline.getText().toString();
        mPoiSearch.searchInCity(new PoiCitySearchOption().city(ETcity).keyword(ETbusline).scope(2));
        InputMethodManager inputMethodManager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
        mBusLineSearch.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onGetBusLineResult(BusLineResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(BusLineActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        String title;
        buslineList.clear();
        List<BusLineResult.BusStation> stations = result.getStations();
        for (BusLineResult.BusStation busStation : stations) {
            title = busStation.getTitle();
            buslineList.add(title);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(BusLineActivity.this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        //遍历所有POI，找到类型为公交线路的POI
        for (PoiInfo poi : poiResult.getAllPoi()) {
            if (poi.getPoiDetailInfo().getTag().equals("公交线路")) {
                mBusLineSearch.searchBusLine(new BusLineSearchOption().city(ETcity).uid(poi.uid));
                break;
            }
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
