package com.example.drodx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drodx.adapters.CategoryManageAdapter;
import com.example.drodx.adapters.NavCategoryAdapter;
import com.example.drodx.models.NavCategoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
//public class ManageCategoriesActivity extends AppCompatActivity {
//    Toolbar toolbar;
//    RecyclerView manageCategoryRec;
//    List<NavCategoryModel> categoryModelList;
//    CategoryManageAdapter categoryManageAdapter;
//    FirebaseFirestore db;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_manage_categories);
//
//        // Apply window insets (This part should only handle UI padding)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Initialize Firestore
//        db = FirebaseFirestore.getInstance();
//
//        // Set up the toolbar
//        toolbar = findViewById(R.id.CatToolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Initialize RecyclerView
//        manageCategoryRec = findViewById(R.id.manageCategory_rec);
//        manageCategoryRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//
//        // Initialize Adapter & List
//        categoryModelList = new ArrayList<>();
//        categoryManageAdapter = new CategoryManageAdapter(this, categoryModelList);
//        manageCategoryRec.setAdapter(categoryManageAdapter);
//
//        // Attach ItemTouchHelper to RecyclerView
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(CategoryManageAdapter.getItemTouchHelperCallback(categoryManageAdapter));
//        itemTouchHelper.attachToRecyclerView(manageCategoryRec);
//
//        // Fetch categories from Firestore
//        db.collection("NavCategory")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (QueryDocumentSnapshot document: task.getResult()){
//                                NavCategoryModel navCategoryModel = document.toObject(NavCategoryModel.class);
//                                categoryModelList.add(navCategoryModel);
//                                categoryManageAdapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            Toast.makeText(ManageCategoriesActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
//}


public class ManageCategoriesActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView manageCategoryRec;
    List<NavCategoryModel> categoryModelList;
    CategoryManageAdapter categoryManageAdapter;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_categories);

        // Apply window insets (This part should only handle UI padding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Set up the toolbar
        toolbar = findViewById(R.id.CatToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize RecyclerView
        manageCategoryRec = findViewById(R.id.manageCategory_rec);
        manageCategoryRec.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        // Initialize Adapter & List
        categoryModelList = new ArrayList<>();
        categoryManageAdapter = new CategoryManageAdapter(this, categoryModelList);
        manageCategoryRec.setAdapter(categoryManageAdapter);

        // Attach ItemTouchHelper to RecyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(CategoryManageAdapter.getItemTouchHelperCallback(categoryManageAdapter));
        itemTouchHelper.attachToRecyclerView(manageCategoryRec);

        // Fetch categories from Firestore
        db.collection("NavCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                NavCategoryModel navCategoryModel = document.toObject(NavCategoryModel.class);
                                categoryModelList.add(navCategoryModel);
                                categoryManageAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(ManageCategoriesActivity.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
