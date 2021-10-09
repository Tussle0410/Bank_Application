package com.example.bank_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;


public class home_viewPager_Adapter extends RecyclerView.Adapter<home_viewPager_Adapter.MyViewHolder> {
    private Context  context;
    private String[] ImageUrl;
    public home_viewPager_Adapter(Context context, String[] ImageUrl){
        this.context = context;
        this.ImageUrl = ImageUrl;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_event_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Glide 부르기
        holder.bindSliderImage(ImageUrl[position]);
    }

    @Override
    public int getItemCount() {
        return ImageUrl.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView eventView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            eventView = itemView.findViewById(R.id.home_event_item);
        }
        public void bindSliderImage(String imageUrl){       //Glide 함수
            Glide.with(context)
                    .load(imageUrl)     //Url 가져오기
                    .into(eventView);   //Url 이미지 설정하기
        }
    }
}
