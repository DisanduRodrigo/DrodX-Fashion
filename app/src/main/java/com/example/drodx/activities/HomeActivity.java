package com.example.drodx.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.drodx.MainActivity;
import com.example.drodx.R;
import com.google.firebase.auth.FirebaseAuth;

//public class HomeActivity extends AppCompatActivity {
//
//    ProgressBar progressBar;
//    FirebaseAuth auth;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_home);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        auth = FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressbar_splash);
//        progressBar.setVisibility(View.GONE);
//        if(auth.getCurrentUser()!=null){
//            progressBar.setVisibility(View.VISIBLE);
//            startActivity(new Intent(HomeActivity.this, MainActivity.class));
//            Toast.makeText(this,"Please wait you are already logged in",Toast.LENGTH_LONG).show();
//            finish();
//        }
//    }
//
//    public void login(View view) {
//        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
//
//    }
//
//    public void registration(View view) {
//        startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
//    }
//    public void adminLogin(View view) {
//        startActivity(new Intent(HomeActivity.this, AdminLoginActivity.class));
//    }
//}

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;


import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getInsets(Type.systemBars()).left,
                    insets.getInsets(Type.systemBars()).top,
                    insets.getInsets(Type.systemBars()).right,
                    insets.getInsets(Type.systemBars()).bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar_splash);
        progressBar.setVisibility(View.GONE);

        // Check if phone is flipped (landscape mode)
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Please rotate your phone to portrait mode", Toast.LENGTH_LONG).show();
        } else {
            // If user is logged in, go to MainActivity
            if (auth.getCurrentUser() != null) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                Toast.makeText(this, "Please wait, you are already logged in", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Detect orientation change and show alert if in landscape mode
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Please rotate your phone to portrait mode to continue ", Toast.LENGTH_LONG).show();
        }
    }

    public void login(View view) {
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(HomeActivity.this, RegistrationActivity.class));
    }

    public void adminLogin(View view) {
        startActivity(new Intent(HomeActivity.this, AdminLoginActivity.class));
    }
}

