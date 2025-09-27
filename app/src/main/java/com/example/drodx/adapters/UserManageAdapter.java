package com.example.drodx.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.drodx.R;
import com.example.drodx.models.NavCategoryDetailedModel;
import com.example.drodx.models.UserModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

//public class UserManageAdapter  extends RecyclerView.Adapter<UserManageAdapter.ViewHolder> {
//
//    Context context;
//    List<UserModel> userModelList;
//
//    public UserManageAdapter(Context context, List<UserModel> userModelList) {
//        this.context = context;
//        this.userModelList = userModelList;
//    }
//
//    @NonNull
//    @Override
//    public UserManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_item,parent,false));
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull UserManageAdapter.ViewHolder holder, int position) {
//
//       Glide.with(context).load(userModelList.get(position).getProfileImg()).into(holder.userImg);
//       holder.name.setText(userModelList.get(position).getName());
//       holder.email.setText(userModelList.get(position).getEmail());
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return userModelList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageView userImg;
//        TextView name,email;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            userImg = itemView.findViewById(R.id.manageUserImg);
//            name = itemView.findViewById(R.id.manageUserName);
//            email = itemView.findViewById(R.id.manageUserEmail);
//
//        }
//    }
//}
public class UserManageAdapter extends RecyclerView.Adapter<UserManageAdapter.ViewHolder> {

    private Context context;
    private List<UserModel> userModelList;
    private DatabaseReference usersRef; // Firebase Database reference

    public UserManageAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
        this.usersRef = FirebaseDatabase.getInstance().getReference().child("Users"); // Reference to Firebase DB
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = userModelList.get(position);
        Glide.with(context).load(userModelList.get(position).getProfileImg()).into(holder.userImg);
        holder.userName.setText(userModel.getName());
        holder.userEmail.setText(userModel.getEmail());

        // Remove user when button is clicked
        holder.removeButton.setOnClickListener(v -> {
            String userId = userModel.getUserId();
            if (userId != null) {
                usersRef.child(userId).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "User Removed", Toast.LENGTH_SHORT).show();
                            userModelList.remove(position);
                            notifyItemRemoved(position);
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed to remove user", Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userImg;
        TextView userName, userEmail;
        Button removeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImg = itemView.findViewById(R.id.manageUserImg);
            userName = itemView.findViewById(R.id.manageUserName);
            userEmail = itemView.findViewById(R.id.manageUserEmail);
            removeButton = itemView.findViewById(R.id.user_remove_btn);
        }
    }
}

