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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.jedu.re_kos.R;
import com.jedu.re_kos.Model.UserModel;
import com.jedu.re_kos.Model.request.UpdateRequest;
import com.jedu.re_kos.viewmodel.ImageUploadViewModel;
import com.jedu.re_kos.viewmodel.UserViewModel;

import java.io.File;
import java.util.Calendar;

public class profil2 extends AppCompatActivity {

    String[] item = {"Laki-Laki","Perempuan"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    private Button Back,Simpan;
    private UserModel userModel;
    private UserViewModel userViewModel;
    private EditText nama,email,pekerjaan,instansi,alamat,notelephone,tanggallahir;
    private ImageUploadViewModel imageViewModel;
    private ImageView imgprofil;
    private static final String PREFS_NAME = "UserProfile";
    private static final String PROFILE_IMAGE_URI_KEY = "profileImageUri";
    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil2);

        // findById
        imgprofil = findViewById(R.id.image_profil);
        nama = findViewById(R.id.nama);
        email = findViewById(R.id.email);
        pekerjaan = findViewById(R.id.pekerjaan);
        instansi = findViewById(R.id.instansi);
        alamat = findViewById(R.id.alamat);
        notelephone = findViewById(R.id.notelephone);
        autoCompleteTextView = findViewById(R.id.gender);
        adapterItems = new ArrayAdapter<>(this,R.layout.list_items, item);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        imageViewModel = new ViewModelProvider(this).get(ImageUploadViewModel.class);
        tanggallahir = findViewById(R.id.tanggalLahir);
        Back = findViewById(R.id.button_batal);
        Simpan = findViewById(R.id.button_simpan);

        //aksi tombol batal
        Back.setOnClickListener(v -> {
            onBackPressed();
        });

        imageViewModel.getImageData().observe(this, bitmap -> {
            if (bitmap != null) {
                imgprofil.setImageBitmap(bitmap);
            }
        });


        imageViewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        int userId = getUserId();
        imageViewModel.fetchImage(String.valueOf(userId));
        imgprofil.setOnClickListener(v -> showImageDialog());

        // Nonaktifkan fokus dan tetap dapat diklik
        tanggallahir.setFocusable(false);
        tanggallahir.setClickable(true);
        tanggallahir.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        // Set adapter ke AutoCompleteTextView
        autoCompleteTextView.setAdapter(adapterItems);

        // Set listener untuk AutoCompleteTextView
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(profil2.this, selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        Simpan.setOnClickListener(v -> {
            // Ambil data dari input pengguna
            int id_user = getUserId();
            String updatenama = nama.getText().toString().trim();
            String updatejenisKelamin = autoCompleteTextView.getText().toString().trim();
            String updatetanggalLahir = tanggallahir.getText().toString().trim();
            String updatepekerjaan = pekerjaan.getText().toString().trim();
            String updateinstansi = instansi.getText().toString().trim();
            String updatealamat = alamat.getText().toString().trim();
            String updatenumberPhone = notelephone.getText().toString().trim();

            // Validasi input
            if (updatenama.isEmpty() || updatetanggalLahir.isEmpty()) {
                Toast.makeText(this, "Nama dan Tanggal Lahir harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }
            UpdateRequest updateRequest = new UpdateRequest(id_user,updatenama,updatejenisKelamin,updatetanggalLahir,updatepekerjaan,updateinstansi,updatealamat,updatenumberPhone);

            userViewModel.updateUser(updateRequest).observe(this, updateRespon->{
                if (updateRespon.getStatus().equals("success")) {
                    // Berhasil diperbarui
                    Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                } else {
                    // Jika respon null, berarti ada kesalahan
                    Toast.makeText(this, updateRespon.getMessage() + updatetanggalLahir, Toast.LENGTH_SHORT).show();
                }
            });

        });



        userViewModel.getData(userId).observe(this, userResponse -> {
            if(userResponse.getStatus().equals("success")){
                userModel = userResponse.getUserModel();
                nama.setText(userModel.getNama() != null ? userModel.getNama() : "");
                email.setText(userModel.getEmail() != null ? userModel.getEmail() : "");
                autoCompleteTextView.setText(userModel.getJenis_kelamin() != null ? userModel.getJenis_kelamin() : "");
                tanggallahir.setText(userModel.getTanggal_lahir() != null ? userModel.getTanggal_lahir() : "");
                pekerjaan.setText(userModel.getPekerjaan() != null ? userModel.getPekerjaan() : "");
                instansi.setText(userModel.getInstansi() != null ? userModel.getInstansi() : "");
                alamat.setText(userModel.getAlamat() != null ? userModel.getAlamat() : "");
                notelephone.setText(userModel.getNumber_phone() != null ? userModel.getNumber_phone() : "");
            }
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                imgprofil.setImageURI(imageUri);  // Display the selected image in ImageView
                imgprofil.setScaleType(ImageView.ScaleType.CENTER_CROP);

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
        // Mendapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Membuat DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Tambahkan 1 ke bulan karena dimulai dari 0
                selectedMonth += 1;

                // Format tanggal dengan zero-padding untuk bulan dan hari
                String formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth, selectedDay);

                tanggallahir.setText(formattedDate);
            }
        }, year, month, day);

        // Menampilkan dialog
        datePickerDialog.show();
    }

    private int getUserId() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", 0);
        if(userId != 0){
            return userId;
        } else {
            return 0;
        }
    }
    private void showImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        Dialog fullScreenDialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        ImageView fullScreenImageView = new ImageView(this);
        fullScreenImageView.setImageDrawable(imgprofil.getDrawable()); // Set the same image
        fullScreenDialog.setContentView(fullScreenImageView);
        fullScreenDialog.show();
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }


    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

}