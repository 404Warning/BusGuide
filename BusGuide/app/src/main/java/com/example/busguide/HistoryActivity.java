package com.example.busguide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.busguide.adapter.HistoryAdapter;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity {

    private Button clear;
    private RecyclerView history;
    private List<RoutePlanHistory> routePlanHistories=new ArrayList<>();
    private HistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        routePlanHistories=LitePal.findAll(RoutePlanHistory.class);
        history=findViewById(R.id.history);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        history.setLayoutManager(layoutManager);
        history.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter=new HistoryAdapter(routePlanHistories);
        history.setAdapter(adapter);
        clear=findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LitePal.deleteAll(RoutePlanHistory.class);
                onCreate(null);
            }
        });

    }

}
