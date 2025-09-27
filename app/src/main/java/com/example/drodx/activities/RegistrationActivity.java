package com.example.drodx.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.drodx.R;
import com.example.drodx.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    Button btn_signUp;
    EditText name, email, password;
    TextView txt_signIn;


    FirebaseAuth auth;
    FirebaseDatabase database;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_signUp = findViewById(R.id.reg_btn);
        name = findViewById(R.id.name_reg);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        txt_signIn = findViewById(R.id.txt_sign_in);

        progressBar = findViewById(R.id.progressbar_reg);
        progressBar.setVisibility(View.GONE);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        txt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createUser();
                progressBar.setVisibility(View.VISIBLE);

            }
        });


    }

//    private void createUser() {
//        String userName = name.getText().toString();
//        String userEmail = email.getText().toString();
//        String userPassword = password.getText().toString();
//
//
//        if(TextUtils.isEmpty(userName)){
//            Toast.makeText(this,"Name is Empty! Please enter your name",Toast.LENGTH_SHORT).show();
//            return;
//        }if(TextUtils.isEmpty(userEmail)){
//            Toast.makeText(this,"Email is Empty! Please enter your Email",Toast.LENGTH_SHORT).show();
//            return;
//        }if(TextUtils.isEmpty(userPassword)){
//            Toast.makeText(this,"Password is Empty! Please enter your Password",Toast.LENGTH_SHORT).show();
//            return;
//        }if(userPassword.length()<6){
//            Toast.makeText(this,"Password must be greater than 6 characters",Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        //Create User
//        auth.createUserWithEmailAndPassword(userEmail,userPassword)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            UserModel userModel= new UserModel(userName,userEmail,userPassword);
//                            String id = task.getResult().getUser().getUid();
//                            database.getReference().child("Users").child(id).setValue(userModel);
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(RegistrationActivity.this,"Registration Successful",Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                        }else{
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(RegistrationActivity.this,"Something went wrong:"+task.getException(),Toast.LENGTH_LONG).show();
//
//                        }
//                    }
//                });
//    }
//}


    private void createUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(this, "Name is Empty! Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(this, "Email is Empty! Please enter your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            Toast.makeText(this, "Password is Empty! Please enter your Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create User in Firebase Authentication
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Get Firebase User ID (UID)
                            String userId = task.getResult().getUser().getUid();

                            // Create a new user object with userId
//                            UserModel userModel = new UserModel(userId, userName, userEmail, userPassword);
                            UserModel userModel = new UserModel(userId, userName, userEmail, userPassword, "", "", "");

                            // Save user in Firebase Database under "Users"
                            database.getReference().child("Users").child(userId).setValue(userModel)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrationActivity.this, "Failed to save user data", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegistrationActivity.this, "Something went wrong: " + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
//public class RegistrationActivity extends AppCompatActivity {
//
//    Button btn_signUp;
//    EditText name, email, password;
//    TextView txt_signIn;
//    ProgressBar progressBar;
//
//    FirebaseAuth auth;
//    FirebaseFirestore firestore; // Firestore instance
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//
//        btn_signUp = findViewById(R.id.reg_btn);
//        name = findViewById(R.id.name_reg);
//        email = findViewById(R.id.email_reg);
//        password = findViewById(R.id.password_reg);
//        txt_signIn = findViewById(R.id.txt_sign_in);
//        progressBar = findViewById(R.id.progressbar_reg);
//        progressBar.setVisibility(View.GONE);
//
//        auth = FirebaseAuth.getInstance();
//        firestore = FirebaseFirestore.getInstance(); // Initialize Firestore
//
//        txt_signIn.setOnClickListener(view ->
//                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class))
//        );
//
//        btn_signUp.setOnClickListener(view -> {
//            progressBar.setVisibility(View.VISIBLE);
//            createUser();
//        });
//    }
//
//    private void createUser() {
//        String userName = name.getText().toString().trim();
//        String userEmail = email.getText().toString().trim();
//        String userPassword = password.getText().toString().trim();
//
//        if (TextUtils.isEmpty(userName)) {
//            showToast("Name is Empty! Please enter your name");
//            return;
//        }
//        if (TextUtils.isEmpty(userEmail)) {
//            showToast("Email is Empty! Please enter your Email");
//            return;
//        }
//        if (TextUtils.isEmpty(userPassword)) {
//            showToast("Password is Empty! Please enter your Password");
//            return;
//        }
//        if (userPassword.length() < 6) {
//            showToast("Password must be greater than 6 characters");
//            return;
//        }
//
//        auth.createUserWithEmailAndPassword(userEmail, userPassword)
//                .addOnCompleteListener(task -> {
//                    progressBar.setVisibility(View.GONE);
//                    if (task.isSuccessful()) {
//
//                        FirebaseUser user = auth.getCurrentUser();
//                        if (user != null) {
//                            saveUserToFirestore(user.getUid(), userName, userEmail);
//                        }
//                        showToast("Registration Successful");
//                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                    } else {
//                        showToast("Something went wrong: " + task.getException().getMessage());
//                    }
//                });
//    }
//
//    private void saveUserToFirestore(String userId, String userName, String userEmail) {
//        Map<String, Object> userMap = new HashMap<>();
//        userMap.put("uid", userId);
//        userMap.put("name", userName);
//        userMap.put("email", userEmail);
//        userMap.put("createdAt", System.currentTimeMillis());
//
//        firestore.collection("users").document(userId)
//                .set(userMap)
//                .addOnSuccessListener(aVoid -> showToast("User added to Firestore"))
//                .addOnFailureListener(e -> showToast("Firestore Error: " + e.getMessage()));
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
//    }
//}
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;

//public class RegistrationActivity extends AppCompatActivity {
//
//    Button btn_signUp;
//    EditText name, email, password;
//    TextView txt_signIn;
//    ProgressBar progressBar;
//
//    FirebaseAuth auth;
//    FirebaseFirestore db; // Firestore instance
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//
//        btn_signUp = findViewById(R.id.reg_btn);
//        name = findViewById(R.id.name_reg);
//        email = findViewById(R.id.email_reg);
//        password = findViewById(R.id.password_reg);
//        txt_signIn = findViewById(R.id.txt_sign_in);
//        progressBar = findViewById(R.id.progressbar_reg);
//        progressBar.setVisibility(View.GONE);
//
//        auth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance(); // Initialize Firestore
//
//        txt_signIn.setOnClickListener(view ->
//                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class))
//        );
//
//        btn_signUp.setOnClickListener(view -> {
//            progressBar.setVisibility(View.VISIBLE);
//            createUser();
//        });
//    }
//
//    private void createUser() {
//        String userName = name.getText().toString().trim();
//        String userEmail = email.getText().toString().trim();
//        String userPassword = password.getText().toString().trim();
//
//        if (TextUtils.isEmpty(userName)) {
//            showToast("Name is Empty! Please enter your name");
//            return;
//        }
//        if (TextUtils.isEmpty(userEmail)) {
//            showToast("Email is Empty! Please enter your Email");
//            return;
//        }
//        if (TextUtils.isEmpty(userPassword)) {
//            showToast("Password is Empty! Please enter your Password");
//            return;
//        }
//        if (userPassword.length() < 6) {
//            showToast("Password must be greater than 6 characters");
//            return;
//        }
//
//        auth.createUserWithEmailAndPassword(userEmail, userPassword)
//                .addOnCompleteListener(task -> {
//                    progressBar.setVisibility(View.GONE);
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        if (user != null) {
//                            UserModel userModel = new UserModel(userName, userEmail, userPassword);
//                            saveUserToFirestore(user.getUid(), userModel);
////                            String id = task.getResult().getUser().getUid();
////                            database.getReference().child("Users").child(id).setValue(userModel);
//                        }
//                        showToast("Registration Successful");
//                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                    } else {
//                        showToast("Something went wrong: " + task.getException().getMessage());
//                    }
//                });
//    }
//
//    private void saveUserToFirestore(String userId, UserModel userModel) {
//        db.collection("users").document(userId)
//                .set(userModel)
//                .addOnSuccessListener(aVoid -> showToast("User added to Firestore"))
//                .addOnFailureListener(e -> showToast("Firestore Error: " + e.getMessage()));
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
//    }
//}

//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class RegistrationActivity extends AppCompatActivity {
//
//    Button btn_signUp;
//    EditText name, email, password;
//    TextView txt_signIn;
//    ProgressBar progressBar;
//
//    FirebaseAuth auth;
//    FirebaseDatabase database;
//    FirebaseFirestore firestore;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_registration);
//
//        btn_signUp = findViewById(R.id.reg_btn);
//        name = findViewById(R.id.name_reg);
//        email = findViewById(R.id.email_reg);
//        password = findViewById(R.id.password_reg);
//        txt_signIn = findViewById(R.id.txt_sign_in);
//        progressBar = findViewById(R.id.progressbar_reg);
//        progressBar.setVisibility(View.GONE);
//
//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        firestore = FirebaseFirestore.getInstance(); // Firestore instance
//
//        txt_signIn.setOnClickListener(view ->
//                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class))
//        );
//
//        btn_signUp.setOnClickListener(view -> {
//            progressBar.setVisibility(View.VISIBLE);
//            createUser();
//        });
//    }
//
//    private void createUser() {
//        String userName = name.getText().toString().trim();
//        String userEmail = email.getText().toString().trim();
//        String userPassword = password.getText().toString().trim();
//
//        if (TextUtils.isEmpty(userName)) {
//            showToast("Name is Empty! Please enter your name");
//            return;
//        }
//        if (TextUtils.isEmpty(userEmail)) {
//            showToast("Email is Empty! Please enter your Email");
//            return;
//        }
//        if (TextUtils.isEmpty(userPassword)) {
//            showToast("Password is Empty! Please enter your Password");
//            return;
//        }
//        if (userPassword.length() < 6) {
//            showToast("Password must be greater than 6 characters");
//            return;
//        }
//
//        auth.createUserWithEmailAndPassword(userEmail, userPassword)
//                .addOnCompleteListener(task -> {
//                    progressBar.setVisibility(View.GONE);
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        if (user != null) {
//                            String userId = user.getUid();
//                            UserModel userModel = new UserModel(userName, userEmail, userPassword);
//
//                            // Save to Firestore
//                            saveUserToFirestore(userId, userModel);
//
//                            // Save to Realtime Database
//                            saveUserToRealtimeDB(userId, userModel);
//                        }
//                        showToast("Registration Successful");
//                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
//                    } else {
//                        showToast("Something went wrong: " + task.getException().getMessage());
//                    }
//                });
//    }
//
//    private void saveUserToFirestore(String userId, UserModel userModel) {
//        firestore.collection("users").document(userId)
//                .set(userModel)
//                .addOnSuccessListener(aVoid -> showToast("User added to Firestore"))
//                .addOnFailureListener(e -> showToast("Firestore Error: " + e.getMessage()));
//    }
//
//    private void saveUserToRealtimeDB(String userId, UserModel userModel) {
//        DatabaseReference userRef = database.getReference().child("Users").child(userId);
//        userRef.setValue(userModel)
//                .addOnSuccessListener(aVoid -> showToast("User added to Realtime DB"))
//                .addOnFailureListener(e -> showToast("Database Error: " + e.getMessage()));
//    }
//
//    private void showToast(String message) {
//        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_SHORT).show();
//    }
//}
