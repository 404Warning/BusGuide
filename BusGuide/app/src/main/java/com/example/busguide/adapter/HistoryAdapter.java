package com.example.busguide.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busguide.R;
import com.example.busguide.RoutePlanHistory;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<RoutePlanHistory> mRoutePlanHistoryList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView historymsg;

        public ViewHolder(@NonNull View view) {
            super(view);
            historymsg=view.findViewById(R.id.historyitem);
        }
    }

    public HistoryAdapter(List<RoutePlanHistory> routePlanHistoryList){
        mRoutePlanHistoryList=routePlanHistoryList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RoutePlanHistory routePlanHistory=mRoutePlanHistoryList.get(position);
        holder.historymsg.setText(routePlanHistory.getRoute());
    }

    @Override
    public int getItemCount() {
        return mRoutePlanHistoryList.size();
    }


}
