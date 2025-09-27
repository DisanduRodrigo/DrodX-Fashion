package com.example.drodx.ui.profile;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
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
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    CircleImageView profileImg;
    EditText name, email, number, address;
    Button editBtn;

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;


    private ActivityResultLauncher<Intent> imagePickerLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        profileImg = root.findViewById(R.id.profile_img);
        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        number = root.findViewById(R.id.profile_number);
        address = root.findViewById(R.id.profile_address);
        editBtn = root.findViewById(R.id.edit_btn);


        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);

                        Glide.with(getContext()).load(userModel.getProfileImg()).into(profileImg);
                        name.setText(userModel.getName());
                        email.setText(userModel.getEmail());
                        number.setText(userModel.getPhone());
                        address.setText(userModel.getAddress());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }
}
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
//   profileImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickImage();
//            }
//        });
//

//
//
//
//
//    private void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        imagePickerLauncher.launch(intent);
//    }
//
//    private void uploadImageToFirebase(Uri uri) {
//        if (auth.getCurrentUser() == null) {
//            Toast.makeText(getContext(), "User not authenticated!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        StorageReference reference = storage.getReference()
//                .child("profile_pictures")
//                .child(auth.getUid() + ".jpg");
//
//        reference.putFile(uri)
//                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(downloadUri -> {
//                    Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
//
//                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
//                                    .child("profileImg").setValue(uri.toString());
//                            Toast.makeText(getContext(), "Profile Image Updated Successfully", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//
//                    //                    saveImageUrlToDatabase(downloadUri.toString());
//                }))
//                .addOnFailureListener(e -> Toast.makeText(getContext(), "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//    }

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


////
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(data.getData() != null){
//            Uri profileUri = data.getData();
//            profileImg.setImageURI(profileUri);
//
//
//            final StorageReference reference = storage.getReference().child("profile_picture")
//                    .child(FirebaseAuth.getInstance().getUid());
//
//            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getContext(), "Profile Image Uploaded Success", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//    }
//}



//
//public class ProfileFragment extends Fragment {
//
//    CircleImageView profileImg;
//     EditText name, email, number, address;
//    Button update;
//
//     FirebaseStorage storage;
//     FirebaseAuth auth;
//     FirebaseDatabase database;
//
//    private ActivityResultLauncher<Intent> imagePickerLauncher;
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_profile, container, false);
//
//        auth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        storage = FirebaseStorage.getInstance();
//
//        profileImg = root.findViewById(R.id.profile_img);
//        name = root.findViewById(R.id.profile_name);
//        email = root.findViewById(R.id.profile_email);
//        number = root.findViewById(R.id.profile_number);
//        address = root.findViewById(R.id.profile_address);
//        update = root.findViewById(R.id.update);
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
//        update.setOnClickListener(view -> updateUserProfile());
//
//        return root;
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
//            Toast.makeText(getContext(), "User not authenticated!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        StorageReference reference = storage.getReference()
//                .child("profile_pictures")
//                .child(auth.getUid() + ".jpg");
//
//        reference.putFile(uri)
//                .addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnSuccessListener(downloadUri -> {
//                    Toast.makeText(getContext(), "Profile Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    saveImageUrlToDatabase(downloadUri.toString());
//                }))
//                .addOnFailureListener(e -> Toast.makeText(getContext(), "Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
//    }
//
//    private void saveImageUrlToDatabase(String imageUrl) {
//        DatabaseReference userRef = database.getReference().child("Users").child(auth.getUid());
//        userRef.child("profileImage").setValue(imageUrl)
//                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Profile Updated!", Toast.LENGTH_SHORT).show())
//                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to update profile", Toast.LENGTH_SHORT).show());
//    }
//
//    private void updateUserProfile() {
//        // Add user update logic here (e.g., saving name, email, etc.)
//    }
//}
