package com.example.drodx.adapters;

import static android.content.Intent.getIntent;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drodx.R;
import com.example.drodx.activities.LoginActivity;
import com.example.drodx.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder>{

   Context context;
   List<MyCartModel>  cartModelList;
   int totalPrice = 0;

   FirebaseFirestore firestore;
   FirebaseAuth auth;

    public interface UpdateTotalAmountListener {
        void onTotalAmountUpdated(List<MyCartModel> updatedCartList);
    }

    private UpdateTotalAmountListener updateTotalAmountListener;

    // Setter method to initialize listener
    public void setUpdateTotalAmountListener(UpdateTotalAmountListener listener) {
        this.updateTotalAmountListener = listener;
    }


    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(cartModelList.get(position).getProductImg()).into(holder.productImg);
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(String.valueOf(cartModelList.get(position).getTotalQuantity()));
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart")
                        .document(cartModelList.get(position).getDocumentId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Remove the item from the list
                                    cartModelList.remove(position);

                                    // Notify the adapter about item removal
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, cartModelList.size());

                                    // Notify the fragment to update the total price
                                    if (updateTotalAmountListener != null) {
                                        updateTotalAmountListener.onTotalAmountUpdated(cartModelList);
                                    }

                                    Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error deleting item: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });


        //////



//        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
//                        .collection("AddToCart")
//                        .document(cartModelList.get(position).getDocumentId())
//                        .delete()
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//                                       cartModelList.remove(cartModelList.get(position));
//                                       notifyDataSetChanged();
//                                    Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show();
//
////                                    // Remove the item from the list
////                                    cartModelList.remove(position);
//
//                                    // Notify the adapter about item removal
//                                    notifyItemRemoved(position);
//                                    notifyItemRangeChanged(position, cartModelList.size());
//
//
//
//                                }else{
//                                    Toast.makeText(context,"Error in Delete Item"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//            }
//        });



        ///////



//        deleteButton.setOnClickListener(v -> {
//            db.collection("CurrentUser")
//                    .document(auth.getCurrentUser().getUid())
//                    .collection("AddToCart")
//                    .document(cartModelList.get(position).getDocumentId()) // Get document ID from model
//                    .delete()
//                    .addOnSuccessListener(aVoid -> {
//                        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
//
//                        // Remove the item from the list
//                        cartModelList.remove(position);
//
//                        // Notify the adapter about item removal
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, cartModelList.size());
//
//                        // Check if cart is empty and update UI
//                        if (cartModelList.isEmpty()) {
//                            ((Activity) context).findViewById(R.id.constraint_cart).setVisibility(View.GONE);
//                            ((Activity) context).findViewById(R.id.constraint_empty_cart).setVisibility(View.VISIBLE);
//                        }
//
//                    })
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
//                    });
//        });
//

//        //pass total amount to My Cart Fragment
//        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
//        Intent intent = new Intent("MyTotalAmount");
//        intent.putExtra("totalAmount",totalPrice);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImg,deleteItem;
       TextView name,price,date,time,quantity,totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImg = itemView.findViewById(R.id.product_img);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time  = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deleteItem = itemView.findViewById(R.id.delete);


        }
    }


}
