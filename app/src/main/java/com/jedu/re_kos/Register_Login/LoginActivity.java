package com.jedu.re_kos.Register_Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.viewmodel.DataViewModel;
import com.jedu.re_kos.model.LoginResponse;

public class LoginActivity extends AppCompatActivity {
    private DataViewModel viewModel;
    private Button buttonLogin;
    private EditText editEmail, editPassword;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.biru_navbar));

        //buttonLogin
        buttonLogin = findViewById(R.id.buttonLogin);

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
//                if (loginResponse != null && loginResponse.getEmail() != null) {
//                    // Login successful
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                } else {
//                    // Login failed
//                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show();
//                }
                if (loginResponse != null) {
                    // Handle successful login
                    if (loginResponse.getStatus().equals("success")) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // Show login error message
                        Log.e("LOGIN_ERROR", loginResponse.getMessage());
                    }
                } else {
                    // Handle failure to get a response
                    Log.e("LOGIN_ERROR", "Failed to get response from API");
                }
            });
        });
    }
}