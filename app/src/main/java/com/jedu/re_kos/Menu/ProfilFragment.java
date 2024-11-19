package com.jedu.re_kos.Menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.util.Calendar;

import com.bumptech.glide.Glide;
import com.jedu.re_kos.R;
import com.jedu.re_kos.model.DataModel;
import com.jedu.re_kos.viewmodel.ImageUploadViewModel;

public class ProfilFragment extends Fragment {
    private DataModel dataModel;
    private ImageUploadViewModel imageViewModel;
    private ImageView imageView;
    private static final String PREFS_NAME = "UserProfile";
    private static final String PROFILE_IMAGE_URI_KEY = "profileImageUri";
    private EditText birthdateEditText;
    private Button lanjut;
    private static final int REQUEST_CODE_PICK_IMAGE = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        imageView = root.findViewById(R.id.imageView);
        imageViewModel = new ViewModelProvider(this).get(ImageUploadViewModel.class);

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


        imageView.setOnClickListener(v -> showImageDialog());

        lanjut = root.findViewById(R.id.lanjut);
        lanjut.setOnClickListener(view1 -> {
            Intent intent = new Intent(requireActivity(), profil2.class);
            startActivity(intent);
        });

        // Setup Spinner for gender selection
        Spinner genderSpinner = root.findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.gender_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Setup DatePickerDialog for birthdate selection
        birthdateEditText = root.findViewById(R.id.editText_birthdate);
        birthdateEditText.setOnClickListener(v -> showDatePickerDialog());

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imageView.setImageURI(imageUri);  // Display the selected image in ImageView

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

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    birthdateEditText.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Image Options");

        // Set options in the dialog
        String[] options = {"View Image", "Upload New Image"};
        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0: // View Image
                    showFullScreenImage();
                    break;
                case 1: // Upload New Image
                    pickImage();
                    break;
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

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }


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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        if(userId != 0){
            return userId;
        } else {
            return 0;
        }
    }
}
