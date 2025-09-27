package com.example.drodx.activities;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.drodx.MainActivity;
import com.example.drodx.R;
import com.example.drodx.models.PaymentModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;

//public class PaymentActivity extends AppCompatActivity {
//
//
//    private static final String TAG = "PayHereDemo";
//
//    double amount = 0.0;
//    Toolbar toolbar;
//    TextView subTotal,discount,shipping,total;
//
//    private TextView textView;
//
//    private final ActivityResultLauncher<Intent>payHereLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result ->{
//                if(result.getResultCode()== Activity.RESULT_OK && result.getData() !=null){
//                    Intent data =result.getData();
//                    if(data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)){
//                        Serializable serializable = data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
//                        if(serializable instanceof PHConstants){
//                            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) serializable;
//                            String msg = response.isSuccess() ? "Payment Success:" +response.getData() : "Payment Failed:" +response;
//                            Log.d(TAG,msg);
//                            textView.setText(msg);
//                        }
//                    }
//                }else if (result.getResultCode() ==Activity.RESULT_CANCELED){
//                    textView.setText("User Cancelled the Request");
//                }
//            }
//    );
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_payment);
////        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
////            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
////            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
////            return insets;
////        });
//
//
//        //Toolbar
//        toolbar = findViewById(R.id.payment_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//
//
//        amount = getIntent().getDoubleExtra("amount",0.0);
//
//        subTotal = findViewById(R.id.sub_total);
//        discount = findViewById(R.id.discount);
//        shipping = findViewById(R.id.shipping);
//        total = findViewById(R.id.total_amount);
//
//        subTotal.setText("Rs."+ amount);
//       shipping.setText("100");
//      // total.setText(amount+shipping);
//
//
//        Button payButton = findViewById(R.id.pay_btn);
//        textView = findViewById(R.id.textView);
//
//
//        payButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                initiatePayment();
//            }
//        });
//
//
//    }
//
//    private void initiatePayment() {
//        InitRequest req = new InitRequest();
//        req.setMerchantId("1227381");       // Merchant ID
//        req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
//        req.setAmount(amount);             // Final Amount to be charged
//        req.setOrderId("230000123");        // Unique Reference ID
//        req.setItemsDescription("Door bell wireless");  // Item description title
//        req.setCustom1("This is the custom message 1");
//        req.setCustom2("This is the custom message 2");
//        req.getCustomer().setFirstName("Saman");
//        req.getCustomer().setLastName("Perera");
//        req.getCustomer().setEmail("disandurodrigo@gmail.com");
//        req.getCustomer().setPhone("+94771234567");
//        req.getCustomer().getAddress().setAddress("No.1, Galle Road");
//        req.getCustomer().getAddress().setCity("Colombo");
//        req.getCustomer().getAddress().setCountry("Sri Lanka");
//
////Optional Params
//
//        //req.setNotifyUrl(“xxxx”);           // Notifiy Url
//        req.getCustomer().getDeliveryAddress().setAddress("No.2, Kandy Road");
//        req.getCustomer().getDeliveryAddress().setCity("Kadawatha");
//        req.getCustomer().getDeliveryAddress().setCountry("Sri Lanka");
//        req.getItems().add(new Item(null, "Door bell wireless", 1, amount));
//
//        Intent intent = new Intent(this, PHMainActivity.class);
//        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
//
//        //Enable sandbox mode
//        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
//
//        payHereLauncher.launch(intent);
//    }
//
//}

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

//public class PaymentActivity extends AppCompatActivity {
//
//    private static final String TAG = "PayHereDemo";
//    private double amount = 0.0;
//    private Toolbar toolbar;
//    private TextView subTotal, discount, shipping, total, textView;
//
//    private final ActivityResultLauncher<Intent> payHereLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
//                    Intent data = result.getData();
//                    if (data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
//                        Serializable serializable = data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
//                        if (serializable instanceof PHResponse) {
//                            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) serializable;
//                            if (response.isSuccess()) {
//                                Log.d(TAG, "✅ Payment Success: " + response.getData());
//                                Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
//
//
//
//                                // Redirect to HomeFragment
//                                redirectToHome();
//                            } else {
//                                Log.d(TAG, "❌ Payment Failed: " + response);
//                                Toast.makeText(PaymentActivity.this, "Payment Failed!", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }
//                } else if (result.getResultCode() == RESULT_CANCELED) {
//                    textView.setText("User Cancelled the Request");
//                }
//            }
//    );
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
//
//        // Toolbar setup
//        toolbar = findViewById(R.id.payment_toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        // Get amount from Intent
//        amount = getIntent().getDoubleExtra("amount", 0.0);
//
//        // UI Elements
//        subTotal = findViewById(R.id.sub_total);
//        discount = findViewById(R.id.discount);
//        shipping = findViewById(R.id.shipping);
//        total = findViewById(R.id.total_amount);
//        textView = findViewById(R.id.textView);
//
//        // Calculate total (Example: Adding Rs. 100 for shipping)
//        double shippingCost = 100.0;
//        double totalAmount = amount + shippingCost;
//
//        subTotal.setText("Rs. " + amount);
//        shipping.setText("Rs. " + shippingCost);
//        total.setText("Rs. " + totalAmount);
//
//        // Payment Button
//        Button payButton = findViewById(R.id.pay_btn);
//        payButton.setOnClickListener(view -> initiatePayment());
//    }
//
//    private void initiatePayment() {
//        InitRequest req = new InitRequest();
//        req.setMerchantId("1227381");
//        req.setCurrency("LKR");
//        req.setAmount(amount);
//        req.setOrderId("230000123");
//        req.setItemsDescription("Fashion Items");
//        req.getCustomer().setFirstName("Saman");
//        req.getCustomer().setLastName("Perera");
//        req.getCustomer().setEmail("disandurodrigo@gmail.com");
//        req.getCustomer().setPhone("+94771234567");
//        req.getCustomer().getAddress().setAddress("No.1, Galle Road");
//        req.getCustomer().getAddress().setCity("Colombo");
//        req.getCustomer().getAddress().setCountry("Sri Lanka");
//
//        Intent intent = new Intent(this, PHMainActivity.class);
//        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
//        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
//
//        payHereLauncher.launch(intent);
//    }
//
//
//
//    private void redirectToHome() {
//        new android.os.Handler().postDelayed(() -> {
//            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
//            intent.putExtra("fragment", "HomeFragment");
//            startActivity(intent);
//            finish(); // Close PaymentActivity
//            Log.d(TAG, "➡️ Redirecting to HomeFragment");
//        }, 2000); // Delay for 2 seconds
//    }
//}
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;



import java.io.Serializable;



public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PayHereDemo";
    private static final String CHANNEL_ID = "payment_channel";
    private double amount = 0.0;
    private Toolbar toolbar;
    private TextView subTotal, discount, shipping, total, textView;

    private final ActivityResultLauncher<Intent> payHereLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    if (data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
                        Serializable serializable = data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
                        if (serializable instanceof PHResponse) {
                            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) serializable;
                            if (response.isSuccess()) {
                                double totalPaidAmount = amount + 100.0; // Include shipping cost
                                Log.d(TAG, "✅ Payment Success: " + response.getData());
                                Toast.makeText(PaymentActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();

                                // Show notification
                                showPaymentNotification();

                                // Save to Firestore
                                saveToDatabase(totalPaidAmount);

                                // Redirect to HomeFragment
                                redirectToHome();
                            } else {
                                Log.d(TAG, "❌ Payment Failed: " + response);
                                Toast.makeText(PaymentActivity.this, "Payment Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else if (result.getResultCode() == RESULT_CANCELED) {
                    textView.setText("User Cancelled the Request");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // Toolbar setup
        toolbar = findViewById(R.id.payment_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get amount from Intent
        amount = getIntent().getDoubleExtra("amount", 0.0);

        // UI Elements
        subTotal = findViewById(R.id.sub_total);
        discount = findViewById(R.id.discount);
        shipping = findViewById(R.id.shipping);
        total = findViewById(R.id.total_amount);
        textView = findViewById(R.id.textView);

        // Calculate total (Adding Rs. 100 for shipping)
        double shippingCost = 100.0;
        double totalAmount = amount + shippingCost;

        subTotal.setText("Rs. " + amount);
        shipping.setText("Rs. " + shippingCost);
        total.setText("Rs. " + totalAmount);

        // Payment Button
        Button payButton = findViewById(R.id.pay_btn);
        payButton.setOnClickListener(view -> initiatePayment());
    }

    private void initiatePayment() {
        double shippingCost = 100.0; // Example shipping cost
        double totalAmount = amount + shippingCost; // Calculate total with shipping

        InitRequest req = new InitRequest();
        req.setMerchantId("1227381");
        req.setCurrency("LKR");
        req.setAmount(totalAmount); // Set the total amount including shipping
        req.setOrderId("230000123");
        req.setItemsDescription("Fashion Items");

        // Customer details
        req.getCustomer().setFirstName("Saman");
        req.getCustomer().setLastName("Perera");
        req.getCustomer().setEmail("disandurodrigo@gmail.com");
        req.getCustomer().setPhone("+94771234567");
        req.getCustomer().getAddress().setAddress("No.1, Galle Road");
        req.getCustomer().getAddress().setCity("Colombo");
        req.getCustomer().getAddress().setCountry("Sri Lanka");

        // Launch PayHere Payment
        Intent intent = new Intent(this, PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);

        payHereLauncher.launch(intent);
    }

    private void showPaymentNotification() {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // Create notification channel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Payment Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Intent to redirect to MainActivity
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                100,
                intent,
                PendingIntent.FLAG_IMMUTABLE
        );

        // Notification action
        NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                R.drawable.shop,
                "View",
                pendingIntent
        ).build();

        // Build notification
        Notification notification = new NotificationCompat.Builder(PaymentActivity.this, CHANNEL_ID)
                .setContentTitle("Payment Successful")
                .setContentText("Your payment was successfully completed.")
                .setSmallIcon(R.drawable.shop)
                .addAction(action)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(1, notification);
    }

//    private void saveToDatabase(double paidAmount) {
//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//
//        // Get the current date
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
//
//        // Create a payment data object
//        Map<String, Object> paymentData = new HashMap<>();
//        paymentData.put("date", currentDate);
//        paymentData.put("amount", paidAmount);
//
//        // Add payment details to Firestore
//        firestore.collection("Payments").add(paymentData)
//                .addOnSuccessListener(documentReference ->
//                        Log.d(TAG, "✅ Payment details saved successfully!")
//                )
//                .addOnFailureListener(e ->
//                        Log.e(TAG, "❌ Failed to save payment details", e)
//                );
//    }
private void saveToDatabase(double paidAmount) {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    // Get the current date
    String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

    // Convert the amount to long
    long amountLong = (long) paidAmount;  // or use (double) if you want to keep it as double

    // Create a PaymentModel object with amount as long
    PaymentModel payment = new PaymentModel(currentDate, amountLong);

    // Add payment details to Firestore
    firestore.collection("Payments").add(payment)
            .addOnSuccessListener(documentReference ->
                    Log.d(TAG, "✅ Payment details saved successfully!")
            )
            .addOnFailureListener(e ->
                    Log.e(TAG, "❌ Failed to save payment details", e)
            );
}


    private void redirectToHome() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            intent.putExtra("fragment", "HomeFragment");
            startActivity(intent);
            finish(); // Close PaymentActivity
            Log.d(TAG, "➡️ Redirecting to HomeFragment");
        }, 2000); // Delay for 2 seconds
    }
}
