package com.example.busguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BusActivity extends AppCompatActivity {

    private Button button_map;
    private Button button_bussearch;
    private Button button_busline;
    private Button button_history;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        button_map=findViewById(R.id.button_map);
        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        button_bussearch=findViewById(R.id.button_bussearch);
        button_bussearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusActivity.this, RoutePlanActivity.class);
                startActivity(intent);
            }
        });
        button_busline=findViewById(R.id.button_busline);
        button_busline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusActivity.this,BusLineActivity.class);
                startActivity(intent);
            }
        });
        button_history=findViewById(R.id.button_history);
        button_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(BusActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
