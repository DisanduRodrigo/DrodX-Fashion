package com.example.drodx.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.drodx.AdminPanelActivity;
import com.example.drodx.MainActivity;
import com.example.drodx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//public class AdminLoginActivity extends AppCompatActivity {
//    Button btn_adminSignIn;
//    EditText email, password;
//    ProgressBar progressBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_login);
//
//        btn_adminSignIn = findViewById(R.id.adminLogin_btn);
//        email = findViewById(R.id.adminEmail_login);
//        password = findViewById(R.id.adminPassword_login);
//        progressBar = findViewById(R.id.progressbar_login);
//        progressBar.setVisibility(View.GONE);
//
//        btn_adminSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loginUser();
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    private void loginUser() {
//        String userEmail = email.getText().toString().trim();
//        String userPassword = password.getText().toString().trim();
//
//        // Validate email and password
//        if (TextUtils.isEmpty(userEmail)) {
//            Toast.makeText(this, "Email is Empty! Please enter your Email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(userPassword)) {
//            Toast.makeText(this, "Password is Empty! Please enter your Password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (userPassword.length() < 6) {
//            Toast.makeText(this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Authenticate via SQLite (admin login logic)
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DatabaseHelper databaseHelper = new DatabaseHelper(AdminLoginActivity.this, "drodx.db", null, 1);
//                SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();
//
//                // Check if the email and password match an entry in the database
//                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM admin WHERE email = ? AND password = ?",
//                        new String[]{userEmail, userPassword});
//
//                if (cursor.moveToFirst()) {
//                    // Successful login
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(AdminLoginActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(AdminLoginActivity.this, AdminPanelActivity.class)); // Admin panel
//                        }
//                    });
//                } else {
//                    // Failed login
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressBar.setVisibility(View.GONE);
//                            Toast.makeText(AdminLoginActivity.this, "Invalid Credentials!", Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//
//                // Close the cursor and database to prevent memory leaks
//                cursor.close();
//                sqLiteDatabase.close();
//            }
//        }).start();
//    }
//}
//
//class DatabaseHelper extends SQLiteOpenHelper {
//
//    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("CREATE TABLE admin (\n" +
//                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                "    email    TEXT    NOT NULL,\n" +
//                "    password TEXT    NOT NULL\n" +
//                ");");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        // Handle database upgrade if needed
//    }
//}
public class AdminLoginActivity extends AppCompatActivity {
    Button btn_adminSignIn;
    EditText email, password;
    ProgressBar progressBar;
    FirebaseAuth auth; // FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        btn_adminSignIn = findViewById(R.id.adminLogin_btn);
        email = findViewById(R.id.adminEmail_login);
        password = findViewById(R.id.adminPassword_login);
        progressBar = findViewById(R.id.progressbar_login);
        progressBar.setVisibility(View.GONE);

        btn_adminSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        // Validate email and password
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

        // Attempt Firebase Authentication first
        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Firebase login successful
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminLoginActivity.this, "Login Successful !", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AdminLoginActivity.this, AdminPanelActivity.class)); // Admin panel
                        } else {
                            // Firebase login failed, try SQLite fallback
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminLoginActivity.this, "Firebase login failed, trying SQLite...", Toast.LENGTH_SHORT).show();
                            loginWithSQLite(userEmail, userPassword);  // Try SQLite login
                        }
                    }
                });
    }

    // SQLite fallback login
    private void loginWithSQLite(final String userEmail, final String userPassword) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper databaseHelper = new DatabaseHelper(AdminLoginActivity.this, "drodx.db", null, 1);
                SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

                // Check if the email and password match an entry in the database
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM admin WHERE email = ? AND password = ?",
                        new String[]{userEmail, userPassword});

                if (cursor.moveToFirst()) {
                    // Successful SQLite login
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminLoginActivity.this, "Login Successful with SQLite!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(AdminLoginActivity.this, AdminPanelActivity.class)); // Admin panel
                        }
                    });
                } else {
                    // Failed SQLite login
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(AdminLoginActivity.this, "Invalid Credentials! Please try again.", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                // Close the cursor and database to prevent memory leaks
                cursor.close();
                sqLiteDatabase.close();
            }
        }).start();
    }
}

class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE admin (\n" +
                "    id       INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    email    TEXT    NOT NULL,\n" +
                "    password TEXT    NOT NULL\n" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Handle database upgrade if needed
    }
}
