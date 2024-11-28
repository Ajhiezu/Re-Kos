package com.jedu.re_kos.Register_Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.jedu.re_kos.Detail.AjukanSewaActivity;
import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.Model.DataModel;
import com.jedu.re_kos.viewmodel.DataViewModel;
import com.jedu.re_kos.Model.LoginResponse;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {
    private DataViewModel viewModel;
    private Button buttonLogin;
    private EditText editEmail, editPassword;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.biru_navbar));

        //buttonLogin
        buttonLogin = findViewById(R.id.buttonLogin);

        int userId = sharedPreferences.getInt("user_id", 0);
        if(userId !=0 ){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        //menuju ke sign up
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(v -> {
            //menuju ke registrasi
            Intent intent = new Intent(LoginActivity.this, RegisterasiActivity.class);
            startActivity(intent);
        });

        //editText
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        //setOnclick
        buttonLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            viewModel.login(email, password).observe(this, loginResponse -> {
                if (loginResponse.getStatus().equals("success") && loginResponse.getData() != null) {
                    // Login successful
                    DataModel dataModel = loginResponse.getData();
                    int idUser = loginResponse.getData().getId();
                    int idKos = loginResponse.getData().getId_kos();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("user_id", idUser);
                    editor.putInt("kost_id", idKos);
                    editor.apply();

                    Log.d("login", String.valueOf(dataModel.getId()));
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Login failed
                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void setData(String Email, int ID, String role) {
        DataModel dataModel = new DataModel();
        dataModel.setEmail(Email);
        dataModel.setId(ID);
        dataModel.setRole(role);

        // Save the data in the ViewModel
        this.viewModel.setData(dataModel);
    }
}