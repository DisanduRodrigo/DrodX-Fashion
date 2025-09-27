package com.example.drodx.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drodx.R;
import com.example.drodx.models.AllProductsModel;


import java.util.List;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ViewHolder> {


    Context context;
    List<AllProductsModel> allProductsModelList;

    public AllProductsAdapter(Context context, List<AllProductsModel> allProductsModelList) {
        this.context = context;
        this.allProductsModelList = allProductsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_products_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductsAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(allProductsModelList.get(position).getImg_url()).into(holder.allPImg);
        holder.name.setText(allProductsModelList.get(position).getName());
        holder.rating.setText(allProductsModelList.get(position).getRating());
        holder.description.setText(allProductsModelList.get(position).getDescription());
        holder.price.setText(allProductsModelList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return allProductsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView allPImg;
        TextView name,description,rating,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            allPImg = itemView.findViewById(R.id.allP_img);
            name = itemView.findViewById(R.id.allP_name);
            description = itemView.findViewById(R.id.allP_dec);
            price = itemView.findViewById(R.id.allP_price);
            rating = itemView.findViewById(R.id.allP_rating);
        }
    }
}
