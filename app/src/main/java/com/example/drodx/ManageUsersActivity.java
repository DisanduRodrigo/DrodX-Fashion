package com.example.drodx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drodx.activities.LoginActivity;
import com.example.drodx.adapters.NavCategoryAdapter;
import com.example.drodx.adapters.UserManageAdapter;
import com.example.drodx.models.NavCategoryModel;
import com.example.drodx.models.UserModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
//
//public class ManageUsersActivity extends AppCompatActivity {
//    Toolbar toolbar;
//    RecyclerView manageUserRec;
//    List<UserModel> userModelList;
//    UserManageAdapter userManageAdapter;
//
//    FirebaseDatabase database;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_manage_users);
//
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Initialize RecyclerView
//        manageUserRec = findViewById(R.id.manageUser_rec);
//        manageUserRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//
//        // Initialize Firebase Database reference
//        database = FirebaseDatabase.getInstance();
//        DatabaseReference usersRef = database.getReference().child("Users");
//
//        // Initialize the list and adapter
//        userModelList = new ArrayList<>();
//        userManageAdapter = new UserManageAdapter(this, userModelList);
//        manageUserRec.setAdapter(userManageAdapter);
//
//        // Fetch data from Realtime Database
//        fetchUsersFromDatabase(usersRef);
//    }
//
////    private void fetchUsersFromDatabase(DatabaseReference usersRef) {
////        usersRef.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                // Clear the list to avoid duplicates
////                userModelList.clear();
////                // Loop through each user in the database
////                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
////                    // Get the UserModel object from the snapshot
////                    UserModel userModel = userSnapshot.getValue(UserModel.class);
////                    if (userModel != null) {
////                        // Add the user to the list
////                        userModelList.add(userModel);
////                    }
////                }
////                // Notify the adapter that data has changed
////                userManageAdapter.notifyDataSetChanged();
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////                Log.e("ManageUsersActivity", "Error fetching data: " + error.getMessage());
////                Toast.makeText(ManageUsersActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
//private void fetchUsersFromDatabase(DatabaseReference usersRef) {
//    usersRef.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            userModelList.clear();
//            for (DataSnapshot userSnapshot : snapshot.getChildren()) {
//                UserModel userModel = userSnapshot.getValue(UserModel.class);
//                if (userModel != null) {
//                    // Set the Firebase key as the user ID
//                    userModel.setUserId(userSnapshot.getKey());
//                    userModelList.add(userModel);
//                }
//            }
//            userManageAdapter.notifyDataSetChanged();
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            Log.e("ManageUsersActivity", "Error fetching data: " + error.getMessage());
//            Toast.makeText(ManageUsersActivity.this, "Error fetching data", Toast.LENGTH_SHORT).show();
//        }
//    });
//}
//}
public class ManageUsersActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView manageUserRec;
    List<UserModel> userModelList;
    UserManageAdapter userManageAdapter;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        if (currentUser == null) {
            // If the user is not authenticated, show a message and redirect to login
            Toast.makeText(this, "User not authenticated. Please log in.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ManageUsersActivity.this, LoginActivity.class)); // Redirect to login page
            finish();
            return;
        }

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference().child("Users");

        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize RecyclerView
        manageUserRec = findViewById(R.id.manageUser_rec);
        manageUserRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // Initialize the list and adapter for RecyclerView
        userModelList = new ArrayList<>();
        userManageAdapter = new UserManageAdapter(this, userModelList);
        manageUserRec.setAdapter(userManageAdapter);

        // Fetch data from Realtime Database
        fetchUsersFromDatabase(usersRef);
    }

    private void fetchUsersFromDatabase(DatabaseReference usersRef) {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModelList.clear();
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    UserModel userModel = userSnapshot.getValue(UserModel.class);
                    if (userModel != null) {
                        userModel.setUserId(userSnapshot.getKey()); // Set the Firebase key as userId
                        userModelList.add(userModel);  // Add user to the list
                    }
                }
                userManageAdapter.notifyDataSetChanged();  // Notify adapter that the data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ManageUsersActivity", "Error fetching data: " + error.getMessage());
                Toast.makeText(ManageUsersActivity.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
