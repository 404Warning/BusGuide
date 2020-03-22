package com.example.busguide.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.busguide.R;

import java.util.List;

public class BusLineAdapter extends RecyclerView.Adapter<BusLineAdapter.ViewHolder> {

    private List<String> mbuslineList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView buslinemsteps;

        public ViewHolder(View view){
            super(view);
            buslinemsteps=view.findViewById(R.id.buslineitem);
        }
    }

    public BusLineAdapter(List<String> buslineList){
        mbuslineList=buslineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_busline_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String step=mbuslineList.get(position);
        holder.buslinemsteps.setText(step);
    }

    @Override
    public int getItemCount() {
        return mbuslineList.size();
    }
}
