package com.jedu.re_kos.Menu;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;

import com.jedu.re_kos.R;
import com.jedu.re_kos.Register_Login.LoginActivity;
import com.jedu.re_kos.model.DataModel;
import com.jedu.re_kos.model.UserModel;
import com.jedu.re_kos.viewmodel.ImageUploadViewModel;
import com.jedu.re_kos.viewmodel.UserViewModel;

public class ProfilFragment extends Fragment {
    private DataModel dataModel;
    private UserModel userModel;
    private ImageUploadViewModel imageViewModel;
    private UserViewModel userViewModel;
    private ImageView imageView;
    private static final String PREFS_NAME = "UserProfile";
    private static final String PROFILE_IMAGE_URI_KEY = "profileImageUri";
    private Button lanjut,logout;
    private TextView namaLengkap,email,jenisKelamin,tanggalLahir,pekerjaan,instansi,alamat,notelephone;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil, container, false);


        //findById
        imageView = root.findViewById(R.id.imageView);
        imageViewModel = new ViewModelProvider(this).get(ImageUploadViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        namaLengkap = root.findViewById(R.id.namalengkap);
        email = root.findViewById(R.id.email);
        jenisKelamin = root.findViewById(R.id.spinner_gender);
        tanggalLahir = root.findViewById(R.id.editText_birthdate);
        pekerjaan = root.findViewById(R.id.pekerjaan);
        instansi = root.findViewById(R.id.instansi);
        alamat = root.findViewById(R.id.alamat);
        notelephone = root.findViewById(R.id.nomortelephon);
        logout = root.findViewById(R.id.button_logout);
        progressBar = root.findViewById(R.id.progress_bar);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);

        logout.setOnClickListener(v -> {
            // Clear SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // This clears all stored data
            editor.apply();

            // Redirect to LoginActivity
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);

            // Finish the current activity (if applicable)
            requireActivity().finish(); // Correct method for closing the activity
        });

        imageViewModel.getImageData().observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
        });


        imageViewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        int userId = getUserId();
        Log.d("id", String.valueOf(userId));
        imageViewModel.fetchImage(String.valueOf(userId));


        userViewModel.getData(userId).observe(getViewLifecycleOwner(), userResponse -> {
            if(userResponse.getStatus().equals("success")){
                userModel = userResponse.getUserModel();
                namaLengkap.setText(userModel.getNama() != null ? userModel.getNama() : "");
                email.setText(userModel.getEmail() != null ? userModel.getEmail() : "");
                jenisKelamin.setText(userModel.getJenis_kelamin() != null ? userModel.getJenis_kelamin() : "");
                tanggalLahir.setText(userModel.getTanggal_lahir() != null ? userModel.getTanggal_lahir() : "");
                pekerjaan.setText(userModel.getPekerjaan() != null ? userModel.getPekerjaan() : "");
                instansi.setText(userModel.getInstansi() != null ? userModel.getInstansi() : "");
                alamat.setText(userModel.getAlamat() != null ? userModel.getAlamat() : "");
                notelephone.setText(userModel.getNumber_phone() != null ? userModel.getNumber_phone() : "");

            }
        });



        imageView.setOnClickListener(v -> showImageDialog());

        lanjut = root.findViewById(R.id.lanjut);
        lanjut.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), profil2.class);
            startActivity(intent);
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imageView.setImageURI(imageUri);  // Display the selected image in ImageView
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                // Convert URI to file and upload it
                String imagePath = getRealPathFromURI(imageUri);
                if (imagePath != null) {
                    File imageFile = new File(imagePath);
                    int userId = getUserId();
                    // Call the uploadImage method using the ViewModel instance
                    imageViewModel.uploadImage(imageFile, userId);
                }
            }
        }
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Image Options");

        // Set options in the dialog
        String[] options = {"View Image"};
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0: // View Image
                    showFullScreenImage();
                    break;
//                case 1: // Upload New Image
//                    pickImage();
//                    break;
            }
        });

        // Show the dialog
        builder.create().show();
    }


    private void showFullScreenImage() {
        Dialog fullScreenDialog = new Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ImageView fullScreenImageView = new ImageView(requireContext());
        fullScreenImageView.setImageDrawable(imageView.getDrawable()); // Set the same image
        fullScreenDialog.setContentView(fullScreenImageView);
        fullScreenDialog.show();
    }

//    private void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
//    }


    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = requireContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

    private int getUserId() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        if(userId != 0){
            return userId;
        } else {
            return 0;
        }
    }
}
