package com.example.drodx.ui.profile;

import static android.app.PendingIntent.getActivity;
import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.drodx.MainActivity;
import com.example.drodx.R;
import com.example.drodx.models.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
public class UpdateProfileActivity extends AppCompatActivity {
    CircleImageView profileImg;
    EditText name, email, number, address;
    Button updateBtn;
    Toolbar toolbar;


    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    private Uri profileImgUri; // Store updated profile image URI
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = findViewById(R.id.update_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Spinner spinner = findViewById(R.id.update_spinner);
//// Array with 9 provinces
//        String[] provinces = {"Select Province" ,
//                "Eastern Province" ,
//                "Central Province",
//                "Southern Province",
//                "Northern Province",
//                "Eastern Province",
//                "North Western Province",
//                "North Central Province",
//                "Uva Province",
//                "Sabaragamuwa Province"};

// Create an adapter using the custom layout
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                this,
//                R.layout.spinner_item,  // Custom layout for selected item
//                provinces
//        );
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);  // Custom dropdown

// Apply the adapter to the Spinner
//        spinner.setAdapter(adapter);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        profileImg = findViewById(R.id.update_profile_img);
        name = findViewById(R.id.update_profile_name);
        email = findViewById(R.id.update_profile_email);
        number = findViewById(R.id.update_profile_number);
        address = findViewById(R.id.update_profile_address);
        updateBtn = findViewById(R.id.update_btn);

        String userId = auth.getUid();
        if (userId == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load user data from Firebase
        database.getReference().child("Users").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);
                        if (userModel != null) {
                            Glide.with(UpdateProfileActivity.this).load(userModel.getProfileImg()).into(profileImg);
                            name.setText(userModel.getName());
                            email.setText(userModel.getEmail());
                           number.setText(userModel.getPhone());
                            address.setText(userModel.getAddress());

                            // Store the current profile image URL
                            if (userModel.getProfileImg() != null) {
                                profileImgUri = Uri.parse(userModel.getProfileImg());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UpdateProfileActivity.this, "Failed to load profile!", Toast.LENGTH_SHORT).show();
                    }
                });

        // Initialize the image picker
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        if (selectedImageUri != null) {
                            profileImg.setImageURI(selectedImageUri);
                            uploadImageToFirebase(selectedImageUri);
                        }
                    }
                }
        );

        profileImg.setOnClickListener(view -> pickImage());

        updateBtn.setOnClickListener(view -> updateProfile());
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void uploadImageToFirebase(Uri uri) {
        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference reference = storage.getReference()
                .child("profile_pictures")
                .child(auth.getUid() + ".jpg");

        reference.putFile(uri)
                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                    profileImgUri = downloadUri; // Store the new profile image URL
                    database.getReference().child("Users").child(auth.getUid())
                            .child("profileImg").setValue(downloadUri.toString());
                    Toast.makeText(UpdateProfileActivity.this, "Profile Image Updated", Toast.LENGTH_SHORT).show();
                }))
                .addOnFailureListener(e -> Toast.makeText(this, "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

//    private void updateProfile() {
//        String updatedName = name.getText().toString().trim();
//        String updatedEmail = email.getText().toString().trim();
//        String updatedNumber = number.getText().toString().trim();
//        String updatedAddress = address.getText().toString().trim();
//
//        if (updatedName.isEmpty() || updatedEmail.isEmpty()) {
//            Toast.makeText(this, "Name and Email must not be empty", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        String userId = auth.getUid();
//        if (userId == null) {
//            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Reference to User in Firebase Database
//        DatabaseReference userRef = database.getReference().child("Users").child(userId);
//
//        // Fetch existing user data
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserModel userModel = snapshot.getValue(UserModel.class);
//                if (userModel == null) {
//                    Toast.makeText(UpdateProfileActivity.this, "User data not found!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Update fields only if they are empty in the database
//                if (userModel.getPhone() == null || userModel.getPhone().isEmpty()) {
//                    userRef.child("phone").setValue(updatedNumber);
//                }
//                if (userModel.getAddress() == null || userModel.getAddress().isEmpty()) {
//                    userRef.child("address").setValue(updatedAddress);
//                }
//
//                // Update name and email
//                userRef.child("name").setValue(updatedName);
//                userRef.child("email").setValue(updatedEmail);
//
//                Toast.makeText(UpdateProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(UpdateProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

    private void updateProfile() {
        String updatedName = name.getText().toString().trim();
        String updatedEmail = email.getText().toString().trim();
        String updatedNumber = number.getText().toString().trim();
        String updatedAddress = address.getText().toString().trim();

        if (updatedName.isEmpty() || updatedEmail.isEmpty()) {
            Toast.makeText(this, "Name and Email must not be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = auth.getUid();
        if (userId == null) {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to User in Firebase Database
        DatabaseReference userRef = database.getReference().child("Users").child(userId);

        // Fetch existing user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (userModel == null) {
                    Toast.makeText(UpdateProfileActivity.this, "User data not found!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Set existing phone and address in fields if available
                if (userModel.getPhone() != null && !userModel.getPhone().isEmpty()) {
                    number.setText(userModel.getPhone());
                }
                if (userModel.getAddress() != null && !userModel.getAddress().isEmpty()) {
                    address.setText(userModel.getAddress());
                }

                // Only update the fields if they have changed
                Map<String, Object> updates = new HashMap<>();
                if (!userModel.getName().equals(updatedName)) {
                    updates.put("name", updatedName);
                }
                if (!userModel.getEmail().equals(updatedEmail)) {
                    updates.put("email", updatedEmail);
                }
                if (!updatedNumber.isEmpty() && !updatedNumber.equals(userModel.getPhone())) {
                    updates.put("phone", updatedNumber);
                }
                if (!updatedAddress.isEmpty() && !updatedAddress.equals(userModel.getAddress())) {
                    updates.put("address", updatedAddress);
                }

                if (!updates.isEmpty()) {
                    userRef.updateChildren(updates)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                                    // Navigate to ProfileFragment
                                    Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                                    intent.putExtra("openProfile", true);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(UpdateProfileActivity.this, "Update Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(UpdateProfileActivity.this, "No changes detected", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//
//        // Ensure profileImgUri is not null before updating
//        String profileImageUrl = profileImgUri != null ? profileImgUri.toString() : "";

//        UserModel updatedUserModel = new UserModel(
//                auth.getUid(),
//                updatedName,
//                updatedEmail,
//                updatedNumber,
//                updatedAddress,
//                profileImageUrl
//        );

//        database.getReference().child("Users").child(auth.getUid())
//             //   .setValue(updatedUserModel)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//                        Toast.makeText(UpdateProfileActivity.this, "Profile update failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}

//public class UpdateProfileActivity extends AppCompatActivity {
//    CircleImageView profileImg;
//    EditText name, email, number, address;
//    Button updateBtn;
//
//
//    FirebaseStorage storage;
//    FirebaseAuth auth;
//    FirebaseDatabase database;
//
//    private Uri profileImgUri; //
//    private ActivityResultLauncher<Intent> imagePickerLauncher;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_update_profile);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//
//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        storage = FirebaseStorage.getInstance();
//
//        profileImg = findViewById(R.id.update_profile_img);
//        name = findViewById(R.id.update_profile_name);
//        email = findViewById(R.id.update_profile_email);
//        number = findViewById(R.id.update_profile_number);
//        address = findViewById(R.id.update_profile_address);
//        updateBtn = findViewById(R.id.update_btn);
//
//
//        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        UserModel userModel = snapshot.getValue(UserModel.class);
//
//                        // Check if userModel is not null and then populate fields
//                        if (userModel != null) {
//                            Glide.with(UpdateProfileActivity.this).load(userModel.getProfileImg()).into(profileImg);
//                            name.setText(userModel.getName());
//                            email.setText(userModel.getEmail());
//                            number.setText(userModel.getNumber()); // Assuming you added this in your UserModel
//                            address.setText(userModel.getAddress()); // Assuming you added this in your UserModel
//                          //  address.setText(userModel.getProvince()); // Assuming you added this in your UserModel
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//// Initialize the image picker launcher
//        imagePickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//                        Uri selectedImageUri = result.getData().getData();
//                        if (selectedImageUri != null) {
//                            profileImg.setImageURI(selectedImageUri);
//                            uploadImageToFirebase(selectedImageUri);
//                        }
//                    }
//                }
//        );
//
//        profileImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickImage();
//            }
//        });
//
//        updateBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String updatedName = name.getText().toString().trim();
//                String updatedEmail = email.getText().toString().trim();
//                String updatedNumber = number.getText().toString().trim();
//                String updatedAddress = address.getText().toString().trim();
//
//                // Ensure data is valid before updating
//                if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedNumber.isEmpty() || updatedAddress.isEmpty()) {
//                    Toast.makeText(UpdateProfileActivity.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Create a new UserModel object with updated data
//                UserModel updatedUserModel = new UserModel(
//                        FirebaseAuth.getInstance().getUid(),
//                        updatedName,
//                        updatedEmail,
//                        updatedNumber,
//                        updatedAddress,
//                        profileImgUri != null ? profileImgUri.toString() : "" // If no new image, pass empty string
//                );
//
//                // Update the data in the Realtime Database
//                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                        .setValue(updatedUserModel)
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(UpdateProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//                                finish();  // Finish the activity to return to the previous screen
//                            } else {
//                                Toast.makeText(UpdateProfileActivity.this, "Profile update failed", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//            }
//        });
//
//
//    }
//
//
//    private void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        imagePickerLauncher.launch(intent);
//    }
//    private void uploadImageToFirebase(Uri uri) {
//        if (auth.getCurrentUser() == null) {
//            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        StorageReference reference = storage.getReference()
//                .child("profile_pictures")
//                .child(auth.getUid() + ".jpg");
//
//        reference.putFile(uri)
//                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(downloadUri -> {
//                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                    // Store the profile image URI
//                    profileImgUri = downloadUri;
//
//                    // Now update the database with the profile image URL
//                    database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                            .child("profileImg").setValue(downloadUri.toString());
//                    Toast.makeText(UpdateProfileActivity.this, "Profile Image Updated Successfully", Toast.LENGTH_SHORT).show();
//                }))
//                .addOnFailureListener(e -> Toast.makeText(this, "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//    }

//    private void uploadImageToFirebase(Uri uri) {
//        if (auth.getCurrentUser() == null) {
//            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        StorageReference reference = storage.getReference()
//                .child("profile_pictures")
//                .child(auth.getUid() + ".jpg");
//
//        reference.putFile(uri)
//                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(downloadUri -> {
//                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
//
//                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                                    .child("profileImg").setValue(uri.toString());
//                            Toast.makeText(UpdateProfileActivity.this, "Profile Image Updated Successfully", Toast.LENGTH_SHORT).show();
//
//
//                        }
//                    });
//
//
//                    //                    saveImageUrlToDatabase(downloadUri.toString());
//                }))
//                .addOnFailureListener(e -> Toast.makeText(this, "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//    }

//    private void updateUserProfile() {
//
//        String userNumber = number.getText().toString();
//        String userAddress = address.getText().toString();
//
//        if (userNumber.isEmpty() || userAddress.isEmpty()) {
//            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        UserModel updatedUser = new UserModel(userNumber, userAddress);
//        database.getReference().child("Users").child(auth.getUid())
//                .setValue(updatedUser)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}
//    private void saveImageUrlToDatabase(String imageUrl) {
//        DatabaseReference userRef = database.getReference().child("Users").child(auth.getUid());
//        userRef.child("profileImage").setValue(imageUrl)
//                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Profile Updated!", Toast.LENGTH_SHORT).show())
//                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show());
//    }

//    private void updateUserProfile() {
//        // Add user update logic here (e.g., saving name, email, etc.)
//    }
//}

/////////////////////////////
//public class UpdateProfileActivity extends AppCompatActivity {
//    CircleImageView profileImg;
//    EditText name, email, number, address;
//    Button updateBtn;
//
//    FirebaseStorage storage;
//    FirebaseAuth auth;
//    FirebaseDatabase database;

//    private ActivityResultLauncher<Intent> imagePickerLauncher;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_update_profile);

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        storage = FirebaseStorage.getInstance();
//
//        // Proper initialization of views
//        profileImg = findViewById(R.id.update_profile_img);
//        name = findViewById(R.id.update_profile_name);
//        email = findViewById(R.id.update_profile_email);
//        number = findViewById(R.id.update_profile_number);
//        address = findViewById(R.id.update_profile_address);
//        updateBtn = findViewById(R.id.update_btn);

//        // Load current user profile data
//        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        UserModel userModel = snapshot.getValue(UserModel.class);
//
//                        // Set profile details to UI elements
//                        if (userModel != null) {
//                            Glide.with(UpdateProfileActivity.this)
//                                    .load(userModel.getProfileImg())
//                                    .into(profileImg);
//                            name.setText(userModel.getName());
//                            email.setText(userModel.getEmail());
//                            // Optional: set other user fields, if available
//                            number.setText(userModel.getNumber());  // Assuming 'number' is added to the model
//                            address.setText(userModel.getAddress()); // Assuming 'address' is added to the model
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(UpdateProfileActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        // Initialize the image picker launcher
//        imagePickerLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//                        Uri selectedImageUri = result.getData().getData();
//                        if (selectedImageUri != null) {
//                            profileImg.setImageURI(selectedImageUri);
//                            uploadImageToFirebase(selectedImageUri);
//                        }
//                    }
//                }
//        );
//
//        profileImg.setOnClickListener(view -> pickImage());
//
//        updateBtn.setOnClickListener(view -> updateUserProfile());
//    }
//
//    private void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        imagePickerLauncher.launch(intent);
//    }
//
//    private void uploadImageToFirebase(Uri uri) {
//        if (auth.getCurrentUser() == null) {
//            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        StorageReference reference = storage.getReference()
//                .child("profile_pictures")
//                .child(auth.getUid() + ".jpg");
//
//        reference.putFile(uri)
//                .addOnSuccessListener(taskSnapshot -> {
//                    reference.getDownloadUrl().addOnSuccessListener(downloadUri -> {
//                        // Update profile image URL in the database
//                        database.getReference().child("Users")
//                                .child(auth.getUid())
//                                .child("profileImg")
//                                .setValue(downloadUri.toString())
//                                .addOnCompleteListener(task -> {
//                                    if (task.isSuccessful()) {
//                                        Toast.makeText(this, "Profile Image Updated Successfully", Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(this, "Failed to update profile image", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                    });
//                })
//                .addOnFailureListener(e -> Toast.makeText(this, "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//    }
//
//    private void updateUserProfile() {
//        String userName = name.getText().toString();
//        String userEmail = email.getText().toString();
//        String userNumber = number.getText().toString();
//        String userAddress = address.getText().toString();
//
//        if (userName.isEmpty() || userEmail.isEmpty() || userNumber.isEmpty() || userAddress.isEmpty()) {
//            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        UserModel updatedUser = new UserModel(userName, userEmail, userNumber, userAddress);
//        database.getReference().child("Users").child(auth.getUid())
//                .setValue(updatedUser)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
//}
