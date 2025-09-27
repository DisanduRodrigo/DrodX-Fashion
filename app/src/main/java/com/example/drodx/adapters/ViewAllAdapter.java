package com.example.drodx.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drodx.R;
import com.example.drodx.activities.DetailedActivity;
import com.example.drodx.models.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

   Context context;
   List<ViewAllModel> list;

    public ViewAllAdapter(Context context, List<ViewAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.viewAllImg);
        holder.name.setText(list.get(position).getName());
        holder.rating.setText(list.get(position).getRating());
        holder.description.setText(list.get(position).getDescription());
        holder.price.setText(list.get(position).getPrice());
       // holder.price.setText(list.get(position).getPrice()+"/Kg");

//        if(list.get(position).getType().equals("egg")){
//            holder.price.setText(list.get(position).getPrice()+"/dozen");
//        }
//        if(list.get(position).getType().equals("milk")){
//            holder.price.setText(list.get(position).getPrice()+"/litre");
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detail",list.get(position));
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView viewAllImg;
        TextView name,description,rating,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            viewAllImg = itemView.findViewById(R.id.view_all_img);
            name = itemView.findViewById(R.id.view_all_name);
            description = itemView.findViewById(R.id.view_all_description);
            price = itemView.findViewById(R.id.view_all_price);
            rating = itemView.findViewById(R.id.view_all_rating);
        }
    }
}
