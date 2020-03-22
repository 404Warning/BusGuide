package com.example.busguide.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busguide.R;

import java.util.List;

public class RoutePlanAdapter extends RecyclerView.Adapter<RoutePlanAdapter.ViewHolder> {

        private List<String> mRouteList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView transitsteps;

        public ViewHolder(View view){
            super(view);
            transitsteps=view.findViewById(R.id.item);
        }
    }

    public RoutePlanAdapter(List<String> routeList){

        mRouteList=routeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_step_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String step=mRouteList.get(position);
        holder.transitsteps.setText(step);
    }

    @Override
    public int getItemCount() {
        return mRouteList.size();
    }
}
