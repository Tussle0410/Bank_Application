package com.example.bank_application;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class financial_Adapter extends RecyclerView.Adapter<financial_Adapter.CustomViewHolder> {
    private Activity activity=null;
    private ArrayList<financial_data> list=null;
    public financial_Adapter(Activity activity,ArrayList<financial_data> list){
        this.activity = activity;
        this.list = list;
    }
    class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView name,description,interestRate,interestCycle;
        public CustomViewHolder(View view){
            super(view);
            this.name = (TextView) view.findViewById(R.id.financial_recycle_name);
            this.description = (TextView) view.findViewById(R.id.financial_recycle_description);
            this.interestRate = (TextView) view.findViewById(R.id.financial_recycle_interestRate);
            this.interestCycle = (TextView) view.findViewById(R.id.financial_recycle_interestCycle);
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.financial_recycle_item,null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.description.setText(list.get(position).getDescription());
        holder.interestCycle.setText(String.valueOf(list.get(position).getInterestCycle()));
        holder.interestRate.setText(String.valueOf(list.get(position).getInterestRate()));
    }

    @Override
    public int getItemCount() {
        return (null !=list ? list.size() : 0);
    }
}
