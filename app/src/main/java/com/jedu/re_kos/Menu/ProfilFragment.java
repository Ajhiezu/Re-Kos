package com.jedu.re_kos.Menu;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.Calendar;

import com.bumptech.glide.Glide;
import com.jedu.re_kos.R;
import com.jedu.re_kos.viewmodel.ImageUploadViewModel;

public class ProfilFragment extends Fragment {
    private ImageUploadViewModel ImageViewModel;
    private ImageView imageView;
    private static final String PREFS_NAME = "UserProfile";
    private static final String PROFILE_IMAGE_URI_KEY = "profileImageUri";
    private EditText birthdateEditText;
    private Button lanjut;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profil, container, false);

        imageView = root.findViewById(R.id.imageView);
        ImageViewModel = new ViewModelProvider(this).get(ImageUploadViewModel.class);

        ImageViewModel.getImageData().observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);  // Set the image to the ImageView
            }
        });

        // Observe error messages and display them as a Toast
        ImageViewModel.getError().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch the image for the current user (replace with the actual user ID)
        String userId = "2024113378"; // Replace with dynamic user ID
        ImageViewModel.fetchImage(userId);


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

//    private void loadImage() {
//        // Check if there's a locally saved URI
//        String savedUriString = loadImageUri();
//        if (savedUriString != null) {
//            Uri savedUri = Uri.parse(savedUriString);
//            Glide.with(this).load(savedUri).into(imageView);
//        } else {
//            // No saved URI, fetch from server
//            String userId = getUserId(); // Retrieve the user ID
//            ImageViewModel.fetchImage(userId); // Fetch image from server
//
//            // Observe the image data from ViewModel
//            ImageViewModel.getImageData().observe(getViewLifecycleOwner(), responseBody -> {
//                if (responseBody != null) {
//                    // Load the image from the response
//                    Uri serverImageUri = Uri.parse(responseBody.getImageUrl()); // Assume `getImageUrl()` returns a valid URL
//                    Glide.with(this).load(serverImageUri).into(imageView);
//
//                    // Save URI to SharedPreferences for future sessions
//                    saveImageUri(serverImageUri);
//                }
//            });
//
//            // Observe errors if any
//            ImageViewModel.getError().observe(getViewLifecycleOwner(), error -> {
//                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
//            });
//        }
//    }

//    private void showImageOptionsDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Change Profile Picture");
//        builder.setItems(new CharSequence[]{"Choose New Image", "Cancel"}, (dialog, which) -> {
//            if (which == 0) {
//                // Open the image picker
//                imagePickerLauncher.launch("image/*");
//            } else if (which == 1) {
//                dialog.dismiss();
//            }
//        });
//        builder.show();
//    }

    // Function to upload the image (simplified example)
//    private void uploadImage(Uri imageUri) {
//        ImageViewModel.uploadProfileImage(imageUri, getUserId());
//
//        ImageViewModel.getUploadStatus().observe(getViewLifecycleOwner(), isSuccess -> {
//            if (isSuccess) {
//                Toast.makeText(requireContext(), "Image uploaded successfully!", Toast.LENGTH_SHORT).show();
//                saveImageUri(imageUri);
//            } else {
//                Toast.makeText(requireContext(), "Failed to upload image.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private String loadImageUri() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PROFILE_IMAGE_URI_KEY, null);
    }

    private void saveImageUri(Uri imageUri) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PROFILE_IMAGE_URI_KEY, imageUri.toString());
        editor.apply();
    }


    private String getUserId() {
        String id = "2024113378";
        return id;
    }
}
