package com.example.drodx;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.drodx.activities.LoginActivity;
import com.example.drodx.activities.RegistrationActivity;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_panel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button edit_user_btn = findViewById(R.id.user_admin_panel_btn);
        edit_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanelActivity.this, ManageUsersActivity.class));
            }
        });
        Button edit_ManageCategory_btn = findViewById(R.id.category_admin_panel_btn);
        edit_ManageCategory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanelActivity.this, ManageCategoriesActivity.class));
            }
        });

      //  PieChart pieChart1 = findViewById(R.id.piechart);
//
//        ArrayList<PieEntry> pieEntryList = new ArrayList<>();
//        pieEntryList.add(new PieEntry(35,"Monday"));
//        pieEntryList.add(new PieEntry(40,"Tuesday"));
//        pieEntryList.add(new PieEntry(15,"Wednesday"));
//        pieEntryList.add(new PieEntry(10,"Thursday"));
//        pieEntryList.add(new PieEntry(10,"Friday"));
//        pieEntryList.add(new PieEntry(10,"Saturday"));
//        pieEntryList.add(new PieEntry(10,"Sunday"));
//
//        //set Entry list
//        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"Days");
//
//        //setColors
//        ArrayList<Integer> colorArrayList = new ArrayList<>();
//        colorArrayList.add(getColor(R.color.colorItem1));
//        colorArrayList.add(getColor(R.color.colorItem2));
//        colorArrayList.add(getColor(R.color.colorItem3));
//        colorArrayList.add(getColor(R.color.colorItem4));
//        pieDataSet.setColors(colorArrayList);
//
//
//        PieData pieData = new PieData();
//
//        //set pie data set
//        pieData.setDataSet(pieDataSet);
//        pieData.setValueTextSize(18);
//        pieData.setValueTextColor(getColor(R.color.black));
//
//        //set pie data
//        pieChart1.setData(pieData);
//
//        pieChart1.animateY(2000, Easing.EaseInCirc);
//
//
//        pieChart1.setCenterText("Daily Revenue");
//        pieChart1.setCenterTextColor(getColor(R.color.color1));
//        pieChart1.setCenterTextSize(18);
//
//
//        pieChart1.setDescription(null);
//        pieChart1.invalidate();

        BarChart barChart1 = findViewById(R.id.barChart);

        // Create BarEntries similar to PieChart data
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        // Adding data (same as PieChart data)
        entries.add(new BarEntry(0, 35));  // Monday
        entries.add(new BarEntry(1, 40));  // Tuesday
        entries.add(new BarEntry(2, 15));  // Wednesday
        entries.add(new BarEntry(3, 20));  // Thursday
        entries.add(new BarEntry(4, 10));  // Friday
        entries.add(new BarEntry(5, 10));  // Saturday
        entries.add(new BarEntry(6, 200));  // Sunday

        // Adding labels for each bar
        labels.add("Monday");
        labels.add("Tuesday");
        labels.add("Wednesday");
        labels.add("Thursday");
        labels.add("Friday");
        labels.add("Saturday");
        labels.add("Sunday");

        // Create a BarDataSet
        BarDataSet dataSet = new BarDataSet(entries, "Daily Earnings");
        dataSet.setColor(Color.GRAY);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        // Set the data to the BarChart
        BarData barData = new BarData(dataSet);
        barChart1.setData(barData);
        barChart1.invalidate();  // Refresh the chart

        // Customize X-axis
        XAxis xAxis = barChart1.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        // Customize Y-axis
        YAxis leftAxis = barChart1.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        barChart1.getAxisRight().setEnabled(false);  // Disable right axis

        // Animation and description
        barChart1.animateY(1000);  // Animation on Y-axis
        barChart1.getDescription().setEnabled(false);  // Disable description

    }
}
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.github.mikephil.charting.charts.PieChart;
//import com.github.mikephil.charting.data.PieData;
//import com.github.mikephil.charting.data.PieDataSet;
//import com.github.mikephil.charting.data.PieEntry;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.Map;

//public class AdminPanelActivity extends AppCompatActivity {
//
//    private BarChart barChart;
//    FirebaseFirestore firestore;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_admin_panel);
//
//        // Set up the Bar Chart
//        barChart = findViewById(R.id.barChart);
//        firestore = FirebaseFirestore.getInstance();
//
//        Button editUserBtn = findViewById(R.id.user_admin_panel_btn);
//        editUserBtn.setOnClickListener(view -> startActivity(new Intent(AdminPanelActivity.this, ManageUsersActivity.class)));
//
//        // Fetch and update the Bar Chart with payment data
//        fetchAndUpdateBarChart();
//
//        new Thread (new Runnable() {
//            @Override
//            public void run() {
//                FirebaseFirestore firestoreFirestore = FirebaseFirestore.getInstance();
//                firestoreFirestore.collection("Payments").get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if(task.isSuccessful()){
//                                    QuerySnapshot querySnapshot = task.getResult();
//                                    if(querySnapshot != null && !querySnapshot.isEmpty()){
//                                        for(DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()){
//                                            Log.i("data",String.valueOf(documentSnapshot.get("date")));
//                                            Object total = documentSnapshot.get("total");
//                                            String date = (String) documentSnapshot .get("date");
//                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//                                            String todayDate = sdf.format(new Date());
//
//                                            if(total instanceof String){
//                                                try {
//                                                    if(date !=null && date .equals(todayDate)){
//                                                        todayEarn += Float.parseFloat((String) totalField);
//
//                                                    }
//                                                    totalearn += Float.parseFloat((String) totalField);
//                                                }catch (NumberFormatException e){
//                                                    Log.e("data","error parsing total value",e);
//                                                }
//                                            }else if(totalField instanceof Number){
//                                                totalEarn += ((Number) totalField).floatValue();
//
//                                            }
//
//                                        }
//                                        Log.i("total",String.valueOf(totalEarn));
//
//                                        runOnUiThread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                setupBarChart();
//                                            }
//                                        });
//
//                                    }else{
//                                        Log.i("total","no dociments found in payments coollection");
//                                    }
//                                }else{
//                                    Log.i("total","error getting documents",task.getException());
//                                }
//                            }
//                        });
//            }
//        }).start();
//    }
//
//    private void fetchAndUpdateBarChart() {
//
//
//        // Query the Payments collection to get all payment data
//        firestore.collection("Payments").get()
//                .addOnSuccessListener(queryDocumentSnapshots -> {
//                    if (queryDocumentSnapshots.isEmpty()) {
//                        Log.d("Firestore", "No payments found.");
//                        barChart.clear();
//                        return;
//                    }
//
//                    // Map to store revenue data for each day of the week
//                    Map<String, Float> revenueByDay = new HashMap<>();
//                    initializeDaysMap(revenueByDay); // Initialize with 0 revenue for each day
//
//                    // Process each payment document
//                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                        String dateStr = document.getString("date");
//                        Long amount = document.getLong("amount"); // Retrieve amount as Long
//
//                        Log.d("Firestore", "Fetched Payment - Date: " + dateStr + ", Amount: " + amount);
//
//                        // Check if the document contains valid data
//                        if (amount != null && dateStr != null) {
//                            try {
//                                // Parse the date string into a Date object
//                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.setTime(sdf.parse(dateStr));
//
//                                // Get the day of the week (Monday, Tuesday, etc.)
//                                String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, getResources().getConfiguration().locale);
//
//                                // Add the payment amount to the corresponding day
//                                revenueByDay.put(dayOfWeek, revenueByDay.get(dayOfWeek) + amount.floatValue());
//                            } catch (ParseException e) {
//                                Log.e("Firestore", "Date parsing failed", e);
//                            }
//                        }
//                    }
//
//                    // Update the Bar Chart with the revenue data
//                    updateBarChart(revenueByDay);
//                })
//                .addOnFailureListener(e -> Log.e("Firestore", "Failed to fetch payment data", e));
//    }
//
//    // Initialize the revenue map with 0 revenue for each day of the week
//    private void initializeDaysMap(Map<String, Float> revenueByDay) {
//        revenueByDay.put("Monday", 0f);
//        revenueByDay.put("Tuesday", 0f);
//        revenueByDay.put("Wednesday", 0f);
//        revenueByDay.put("Thursday", 0f);
//        revenueByDay.put("Friday", 0f);
//        revenueByDay.put("Saturday", 0f);
//        revenueByDay.put("Sunday", 0f);
//    }
//
//    private void updateBarChart(Map<String, Float> revenueByDay) {
//        ArrayList<BarEntry> barEntries = new ArrayList<>();
//
//        // Add BarEntry for each day with revenue
//        int index = 0;
//        for (Map.Entry<String, Float> entry : revenueByDay.entrySet()) {
//            if (entry.getValue() > 0) { // Only add days with revenue
//                barEntries.add(new BarEntry(index++, entry.getValue()));
//            }
//        }
//
//        // Create BarDataSet for the bar chart
//        BarDataSet barDataSet = new BarDataSet(barEntries, "Daily Revenue");
//        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Set chart color
//        barDataSet.setValueTextSize(14f);
//        barDataSet.setValueTextColor(getColor(R.color.black));
//
//        // Create BarData and set it on the BarChart
//        BarData barData = new BarData(barDataSet);
//
//        // Customize BarChart
//        barChart.setData(barData);
//        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(new ArrayList<>(revenueByDay.keySet())));
//        barChart.animateY(2000); // Animation for Bar Chart
//        barChart.invalidate(); // Refresh the chart
//
//
//
//    }
//
//
//}

//import android.graphics.Color;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Button;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.QuerySnapshot;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;

//public class AdminPanelActivity extends AppCompatActivity {
//
//    private BarChart barChart;
//    private FirebaseFirestore firestore;
//    private Map<String, Float> earningsMap = new HashMap<>(); // To store earnings per day
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_panel);
//
//        // Initialize UI components
//        barChart = findViewById(R.id.barChart);
//        firestore = FirebaseFirestore.getInstance();
//
//        Button editUserBtn = findViewById(R.id.user_admin_panel_btn);
//        editUserBtn.setOnClickListener(view ->
//                startActivity(new Intent(AdminPanelActivity.this, ManageUsersActivity.class))
//        );
//
//        // Fetch and display data
//        fetchPaymentData();
//    }
//
//    private void fetchPaymentData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                firestore.collection("Payments").get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    QuerySnapshot querySnapshot = task.getResult();
//                                    if (querySnapshot != null && !querySnapshot.isEmpty()) {
//                                        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
//                                            Object totalObj = documentSnapshot.get("total");
//                                            String date = documentSnapshot.getString("date");
//
//                                            if (date != null && totalObj != null) {
//                                                float amount = 0;
//                                                if (totalObj instanceof Number) {
//                                                    amount = ((Number) totalObj).floatValue();
//                                                } else if (totalObj instanceof String) {
//                                                    try {
//                                                        amount = Float.parseFloat((String) totalObj);
//                                                    } catch (NumberFormatException e) {
//                                                        Log.e("Error", "Failed to parse total value", e);
//                                                        continue;
//                                                    }
//                                                }
//
//                                                // Accumulate earnings for the same date
//                                                earningsMap.put(date, earningsMap.getOrDefault(date, 0f) + amount);
//                                            }
//                                        }
//
//                                        runOnUiThread(() -> setupBarChart());
//                                    } else {
//                                        Log.i("Payments", "No documents found in Payments collection.");
//                                    }
//                                } else {
//                                    Log.e("Firestore", "Error fetching payments", task.getException());
//                                }
//                            }
//                        });
//            }
//        }).start();
//    }
//
//    private void setupBarChart() {
//        List<BarEntry> entries = new ArrayList<>();
//        List<String> labels = new ArrayList<>();
//        int index = 0;
//
//        for (Map.Entry<String, Float> entry : earningsMap.entrySet()) {
//            entries.add(new BarEntry(index, entry.getValue()));
//            labels.add(entry.getKey()); // Date as X-axis label
//            index++;
//        }
//
//        BarDataSet dataSet = new BarDataSet(entries, "Daily Earnings");
//        dataSet.setColor(Color.BLUE);
//        dataSet.setValueTextColor(Color.BLACK);
//        dataSet.setValueTextSize(12f);
//
//        BarData barData = new BarData(dataSet);
//        barChart.setData(barData);
//        barChart.invalidate();
//
//        // Customize X-axis
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//
//        // Customize Y-axis
//        YAxis leftAxis = barChart.getAxisLeft();
//        leftAxis.setAxisMinimum(0f);
//        barChart.getAxisRight().setEnabled(false);
//
//        // Animation and description
//        barChart.animateY(1000);
//        barChart.getDescription().setEnabled(false);
//    }
//}


