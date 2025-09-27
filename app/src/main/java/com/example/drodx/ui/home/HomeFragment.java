package com.example.drodx.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drodx.R;
import com.example.drodx.adapters.AllProductsAdapter;
import com.example.drodx.adapters.HomeAdapter;
import com.example.drodx.adapters.PopularAdapters;
import com.example.drodx.adapters.RecommendedAdapter;
import com.example.drodx.adapters.ViewAllAdapter;
import com.example.drodx.models.AllProductsModel;
import com.example.drodx.models.HomeCategory;
import com.example.drodx.models.PopularModel;
import com.example.drodx.models.RecommendedModel;
import com.example.drodx.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//
//public class HomeFragment extends Fragment {
//
//
//    ScrollView scrollView;
//    ProgressBar progressBar;
//
//    RecyclerView popularRec,homeCatRec,recommendedRec,allProductsRec;
//    FirebaseFirestore db;
//
//
//    //popular items
//    List<PopularModel> popularModelList;
//    PopularAdapters popularAdapters;
//
//    ///Search View
//    EditText search_box;
//    private List<ViewAllModel> viewAllModelList;
//    private RecyclerView recyclerViewSearch;
//    private ViewAllAdapter viewAllAdapter;
//
//    //Home Category
//    List<HomeCategory> categoryList;
//    HomeAdapter homeAdapter;
//
//    //Recommended
//    List<RecommendedModel> recommendedModelList;
//    RecommendedAdapter recommendedAdapter;
//
//    //AllProducts
//    List<AllProductsModel> allProductsModelList;
//    AllProductsAdapter allProductsAdapter;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_home,container,false);
//
//        db = FirebaseFirestore.getInstance();
//
//
//        popularRec = root.findViewById(R.id.pop_rec);
//        homeCatRec = root.findViewById(R.id.explore_rec);
//        recommendedRec = root.findViewById(R.id.recommended_rec);
//        allProductsRec = root.findViewById(R.id.allProducts_rec);
//
//        scrollView = root.findViewById(R.id.scroll_view);
//        progressBar = root.findViewById(R.id.progressbar_home);
//
//        progressBar.setVisibility(View.VISIBLE);
//        scrollView.setVisibility(View.GONE);
//
//        //Popular Items
//           popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
//           popularModelList = new ArrayList<>();
//           popularAdapters = new PopularAdapters(getActivity(),popularModelList);
//           popularRec.setAdapter(popularAdapters);
//
//           db.collection("PopularProducts")
//                .get()
//                   .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                       @Override
//                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                           if(task.isSuccessful()){
//                               for (QueryDocumentSnapshot document: task.getResult()){
//
//                                   PopularModel popularModel = document.toObject(PopularModel.class);
//                                   popularModelList.add(popularModel);
//                                   popularAdapters.notifyDataSetChanged();
//
//                                   progressBar.setVisibility(View.GONE);
//                                   scrollView.setVisibility(View.VISIBLE);
//
//                               }
//                           }else{
//                               Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
//                           }
//                       }
//                   });
//        //Home Category
//        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
//        categoryList = new ArrayList<>();
//        homeAdapter = new HomeAdapter(getActivity(),categoryList);
//        homeCatRec.setAdapter(homeAdapter);
//
//        db.collection("HomeCategory")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (QueryDocumentSnapshot document: task.getResult()){
//
//                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
//                                categoryList.add(homeCategory);
//                                homeAdapter.notifyDataSetChanged();
//
//                            }
//                        }else{
//                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//
//        //Recommended
//        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
//        recommendedModelList = new ArrayList<>();
//        recommendedAdapter = new RecommendedAdapter(getActivity(), recommendedModelList);
//        recommendedRec.setAdapter(recommendedAdapter);
//
//        db.collection("Recomended")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (QueryDocumentSnapshot document: task.getResult()){
//
//                              RecommendedModel recommendedModel = document.toObject(RecommendedModel.class);
//                                   recommendedModelList.add(recommendedModel);
//                                   recommendedAdapter.notifyDataSetChanged();
//                           }
//                        }else{
//                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
////AllProducts Items
//        allProductsRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
//        allProductsModelList = new ArrayList<>();
//        allProductsAdapter = new AllProductsAdapter(getActivity(), allProductsModelList);
//        allProductsRec.setAdapter(allProductsAdapter);
//
//        db.collection("Recomended")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (QueryDocumentSnapshot document: task.getResult()){
//
//                                AllProductsModel allProductsModel = document.toObject(AllProductsModel.class);
//                                allProductsModelList.add(allProductsModel);
//                                allProductsAdapter.notifyDataSetChanged();
//                            }
//                        }else{
//                            Toast.makeText(getActivity(), "Error"+task.getException(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//
//        //Search View
//        recyclerViewSearch = root.findViewById(R.id.search_rec);
//        search_box = root.findViewById(R.id.search_box);
//        viewAllModelList = new ArrayList<>();
//        viewAllAdapter = new ViewAllAdapter(getContext(),viewAllModelList);
//        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerViewSearch.setAdapter(viewAllAdapter);
//        recyclerViewSearch.setHasFixedSize(true);
//        search_box.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//                if (editable.toString().isEmpty()){
//                    viewAllModelList.clear();
//                    viewAllAdapter.notifyDataSetChanged();
//                }else {
//                    searchProduct(editable.toString());
//                }
//
//            }
//        });
//
//
//        return root;
//
//    }
//
//    private void searchProduct(String type) {
//
//
//        if (!type.isEmpty()){
//            db.collection("AllProducts").whereEqualTo("type",type).get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                            if (task.isSuccessful() && task.getResult()!= null){
//
//                                viewAllModelList.clear();
//                                viewAllAdapter.notifyDataSetChanged();
//                                for (DocumentSnapshot doc : task.getResult().getDocuments()){
//                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
//                                    viewAllModelList.add(viewAllModel);
//                                    viewAllAdapter.notifyDataSetChanged();
//
//                                }
//                            }
//                        }
//                    });
//        }
//
//    }
//}
public class HomeFragment extends Fragment {

    ScrollView scrollView;
    ProgressBar progressBar;

    RecyclerView popularRec, homeCatRec, recommendedRec, allProductsRec;
    FirebaseFirestore db;

    // Popular items
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;

    // Search View
    EditText search_box;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;

    // Home Category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    // Recommended
    List<RecommendedModel> recommendedModelList;
    RecommendedAdapter recommendedAdapter;

    // All Products
    List<AllProductsModel> allProductsModelList;
    AllProductsAdapter allProductsAdapter;

    private BroadcastReceiver networkReceiver;
    private boolean isInternetAvailable = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById(R.id.explore_rec);
        recommendedRec = root.findViewById(R.id.recommended_rec);
        allProductsRec = root.findViewById(R.id.allProducts_rec);

        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbar_home);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        // Popular Items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(), popularModelList);
        popularRec.setAdapter(popularAdapters);

        // Home Category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(), categoryList);
        homeCatRec.setAdapter(homeAdapter);

        // Recommended
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        recommendedModelList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(), recommendedModelList);
        recommendedRec.setAdapter(recommendedAdapter);

        // All Products
        allProductsRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        allProductsModelList = new ArrayList<>();
        allProductsAdapter = new AllProductsAdapter(getActivity(), allProductsModelList);
        allProductsRec.setAdapter(allProductsAdapter);

        // Search View
        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), viewAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) {
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                } else {
                    searchProduct(editable.toString());
                }
            }
        });

        // Initialize the network receiver
        networkReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                isInternetAvailable = checkInternetConnection();
                if (!isInternetAvailable) {
                    Toast.makeText(getContext(), "No internet connection. Please turn on your internet.", Toast.LENGTH_LONG).show();
                } else {
                    // Retry fetching data from Firestore when internet is back
                    fetchDataFromFirestore("PopularProducts", popularModelList, popularAdapters, PopularModel.class);
                    fetchDataFromFirestore("HomeCategory", categoryList, homeAdapter, HomeCategory.class);
                    fetchDataFromFirestore("Recomended", recommendedModelList, recommendedAdapter, RecommendedModel.class);
                    fetchDataFromFirestore("AllProducts", allProductsModelList, allProductsAdapter, AllProductsModel.class);
                }
            }
        };

        // Register the receiver to listen for connectivity changes
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        requireActivity().registerReceiver(networkReceiver, filter);

        // Initial fetch if internet is available
        if (isInternetAvailable) {
            fetchDataFromFirestore("PopularProducts", popularModelList, popularAdapters, PopularModel.class);
            fetchDataFromFirestore("HomeCategory", categoryList, homeAdapter, HomeCategory.class);
            fetchDataFromFirestore("Recomended", recommendedModelList, recommendedAdapter, RecommendedModel.class);
            fetchDataFromFirestore("AllProducts", allProductsModelList, allProductsAdapter, AllProductsModel.class);
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister the receiver when the fragment is destroyed
        if (networkReceiver != null) {
            requireActivity().unregisterReceiver(networkReceiver);
        }
    }

    // Method to check for internet connection
    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }

    // Generic method to fetch data from Firestore
    private <T> void fetchDataFromFirestore(String collectionName, List<T> modelList, RecyclerView.Adapter adapter, Class<T> modelClass) {
        db.collection(collectionName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                T model = document.toObject(modelClass);
                                modelList.add(model);
                                adapter.notifyDataSetChanged();
                            }
                            progressBar.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Method to search for products
    private void searchProduct(String type) {
        if (!type.isEmpty()) {
            db.collection("AllProducts").whereEqualTo("type", type).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                viewAllModelList.clear();
                                viewAllAdapter.notifyDataSetChanged();
                                for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                    ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                                    viewAllModelList.add(viewAllModel);
                                    viewAllAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }
}
