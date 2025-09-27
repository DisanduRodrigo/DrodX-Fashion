package com.example.drodx;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drodx.activities.AddressActivity;
import com.example.drodx.activities.PlacedOrderActivity;
import com.example.drodx.adapters.MyCartAdapter;
import com.example.drodx.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

RecyclerView recyclerView;
MyCartAdapter cartAdapter;
List<MyCartModel> cartModelList;
ProgressBar progressBar;
Button buyNow;
int totalBill;
ConstraintLayout constraintCart, constraintEmptyCart;
TextView aboveTotalAmount;

FirebaseFirestore db;
FirebaseAuth auth;


    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_my_cart, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        constraintCart = root.findViewById(R.id.constraint_cart);
        constraintEmptyCart = root.findViewById(R.id.constraint_empty_cart);
        //progressBar = root.findViewById(R.id.progressbar);
      //  progressBar.setVisibility(View.VISIBLE);
     //   layout.setVisibility(View.VISIBLE);

        aboveTotalAmount = root.findViewById(R.id.cart_total_price);

//        LocalBroadcastManager.getInstance(getActivity())
//                .registerReceiver(MessageReceiver,new IntentFilter("MyTotalAmount"));

        recyclerView = root.findViewById(R.id.recyclerView);
       // recyclerView.setVisibility(View.GONE);
        buyNow = root.findViewById(R.id.buy_now);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);

        cartAdapter.setUpdateTotalAmountListener(new MyCartAdapter.UpdateTotalAmountListener() {
            @Override
            public void onTotalAmountUpdated(List<MyCartModel> updatedCartList) {
                calculateTotalAmount(updatedCartList);
            }
        });


//        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
//                .collection("AddToCart")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
//
//                                String documentId = documentSnapshot.getId();
//
//                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
//
//                                cartModel.setDocumentId(documentId);
//
//                                cartModelList.add(cartModel);
//                                cartAdapter.notifyDataSetChanged();
//                                //progressBar.setVisibility(View.GONE);
//                                layout.setVisibility(View.GONE);
//                                recyclerView.setVisibility(View.VISIBLE);
//                            }
//
//                            calculateTotalAmount(cartModelList);
//
//                        }
//                    }
//                });

//        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
//                .collection("AddToCart")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            if (!task.getResult().isEmpty()) {  // If cart has items
//                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
//
//                                    String documentId = documentSnapshot.getId();
//                                    MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
//                                    cartModel.setDocumentId(documentId);
//
//                                    cartModelList.add(cartModel);
//                                    cartAdapter.notifyDataSetChanged();
//                                }
//
//                                calculateTotalAmount(cartModelList);
//
//                                // Hide progress & show cart items
//                                layoutemptycart.setVisibility(View.GONE);
//                                layoutcart.setVisibility(View.VISIBLE);
//
//                            } else {  //  If cart is empty
//                                layoutemptycart.setVisibility(View.VISIBLE);
//                                layoutcart.setVisibility(View.GONE);
//                            }
//                        } else {  //  If fetching data fails
//                            Toast.makeText(getContext(), "Failed to load cart items!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
        if (auth.getCurrentUser() != null) {  //  Prevents null UID crash
            db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                    .collection("AddToCart")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (task.getResult() != null && !task.getResult().isEmpty()) { //  Cart has items
                                    cartModelList.clear();

                                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                        String documentId = documentSnapshot.getId();
                                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);

                                        if (cartModel != null) {  //  Prevents null object crash
                                            cartModel.setDocumentId(documentId);
                                            cartModelList.add(cartModel);
                                        }
                                    }

                                    cartAdapter.notifyDataSetChanged();
                                    calculateTotalAmount(cartModelList);

                                    //  SHOW CART & HIDE EMPTY MESSAGE
                                    constraintCart.setVisibility(View.VISIBLE);
                                    constraintEmptyCart.setVisibility(View.GONE);
                                    //recyclerView.setVisibility(View.VISIBLE);
                                } else {  //  Empty Cart Case
                                    constraintCart.setVisibility(View.GONE);
                                    constraintEmptyCart.setVisibility(View.VISIBLE);
                                   // recyclerView.setVisibility(View.GONE);
                                }
                            } else {  //  Firestore Fetch Failure Handling
                                Toast.makeText(requireContext(), "Failed to load cart!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(requireContext(), "User not logged in!", Toast.LENGTH_SHORT).show();
        }



        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                Intent intent = new Intent(getContext(), AddressActivity.class);
                intent.putExtra("itemList", (Serializable) cartModelList);
                startActivity(intent);

            }
        });


        return root;
    }

//    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
//
//
//        double totalAmount = 0.0;
//        for(MyCartModel myCartModel : cartModelList){
//            totalAmount += myCartModel.getTotalPrice();
//        }
//
//        aboveTotalAmount.setText("Total Amount : "+ totalAmount);
//
//    }

    private void calculateTotalAmount(List<MyCartModel> cartList) {
        double totalAmount = 0.0;
        for (MyCartModel item : cartList) {
            totalAmount += item.getTotalPrice();
        }

        aboveTotalAmount.setText("Total Amount: Rs. " + totalAmount);

        // Show empty cart UI if no items remain
        if (cartList.isEmpty()) {
            constraintCart.setVisibility(View.GONE);
            constraintEmptyCart.setVisibility(View.VISIBLE);
          //  recyclerView.setVisibility(View.GONE);
        } else {
            constraintCart.setVisibility(View.VISIBLE);
            constraintEmptyCart.setVisibility(View.GONE);
         //   recyclerView.setVisibility(View.VISIBLE);
        }
    }



}
//
//    public BroadcastReceiver MessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            int totalBill = intent.getIntExtra("totalAmount",0);
// aboveTotalAmount.setText("Total Bill : Rs. "+totalBill+".00");
//        }
//    };
