package com.example.bank_application;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class remittance_history_Adapter extends RecyclerView.Adapter<remittance_history_Adapter.CustomViewHolder> {
    private ArrayList<remittance_history_data> list = null;
    private Activity context=null;
    public remittance_history_Adapter(Activity context, ArrayList<remittance_history_data> list){
        this.context = context;
        this.list = list;
    }
    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView money, addressName, date, kinds;
        public CustomViewHolder(View v) {
            super(v);
            this.money = (TextView) v.findViewById(R.id.history_list_money);
            this.addressName = (TextView) v.findViewById(R.id.history_list_address_name);
            this.date = (TextView) v.findViewById(R.id.history_list_date);
            this.kinds = (TextView) v.findViewById(R.id.history_list_kind);
        }
    }
    @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.remittance_history_list,null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.money.setText(String.valueOf(list.get(position).getMoney()));
        holder.addressName.setText(list.get(position).getReceiveName());
        holder.date.setText(list.get(position).getDate());
        if(list.get(position).getAddress().equals(list.get(position).getReceiveAddress())){
            holder.kinds.setText("입금");
        }else{
            holder.kinds.setText("송금");
        }

    }
    @Override
    public int getItemCount() {
        return (null !=list ? list.size():0);
    }
}
