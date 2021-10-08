package com.example.bank_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class home_event_Adapter extends RecyclerView.Adapter<home_event_Adapter.MyViewHolder> {
    private Context  context;
    private String[] ImageUrl;
    public home_event_Adapter(Context context, String[] ImageUrl){
        this.context = context;
        this.ImageUrl = ImageUrl;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_event_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView eventView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            eventView = itemView.findViewById(R.id.home_event_viewpager);
        }
        public void bindSliderImage(String imageUrl){
            Glide.with(context)
                    .load(imageUrl)
                    .into(eventView);
        }
    }
}
