package com.jedu.re_kos.Register_Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.jedu.re_kos.Detail.ButtonSewaActivity;
import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.Model.request.RegisRequest;
import com.jedu.re_kos.R;
import com.jedu.re_kos.viewmodel.DataViewModel;

public class RegisterasiActivity extends AppCompatActivity {

    private Button buttonRegister;
    private TextView SignIn;
    private Button buttonGoogleSignUp;
    private EditText username, password, number, email;
    private DataViewModel viewModel;
    private RegisRequest regisRequest;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001; // Request code untuk Google Sign-In

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerasi);
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        // Warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(RegisterasiActivity.this, R.color.biru_navbar));

        // Menemukan View berdasarkan ID
        buttonRegister = findViewById(R.id.buttonCreateAccount);
        SignIn = findViewById(R.id.textViewSignIn);
        buttonGoogleSignUp = findViewById(R.id.buttonGoogle);

        username = findViewById(R.id.editTextFullname);
        password = findViewById(R.id.editTextpassword);
        number = findViewById(R.id.editTextphone);
        email = findViewById(R.id.editEmail);

        buttonRegister.setOnClickListener(v -> {
        int nomor = Integer.parseInt(number.getText().toString());
            regisRequest = new RegisRequest(
                    username.getText().toString(),
                    password.getText().toString(),
                    email.getText().toString(),
                    nomor
            );

           viewModel.register(regisRequest).observe(this, responseBody -> {
               if (responseBody != null) {
                   // Registrasi sukses
                   Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(RegisterasiActivity.this, LoginActivity.class);
                   startActivity(intent);
               } else {
                   // Registrasi gagal
                   Toast.makeText(this, "Registrasi gagal!", Toast.LENGTH_SHORT).show();
               }
           });
        });

//        // Mengatur Google Sign-In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id)) // Gunakan Client ID untuk Google Sign-In
//                .requestEmail()
//                .build();

//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//
//        // Set OnClick Listener untuk tombol Sign Up biasa
//        buttonRegister.setOnClickListener(v -> {
//            Intent intent = new Intent(RegisterasiActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        });

        // Set OnClick Listener untuk tombol Sign In
        SignIn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterasiActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Set OnClick Listener untuk tombol Google Sign-Up
        buttonGoogleSignUp.setOnClickListener(v -> {
            signUpWithGoogle();
        });
    }

    private void signUpWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("GoogleSignIn", "Sign-In berhasil! Nama: " + account.getDisplayName());
                updateUI(account);
            } catch (ApiException e) {
                Log.e("GoogleSignIn", "Error code: " + e.getStatusCode(), e);
                Toast.makeText(this, "Google Failed. Error code: " + e.getStatusCode(), Toast.LENGTH_LONG).show();
            }


        }
    }

    private void updateUI(GoogleSignInAccount account) {
        // Jika sign-in berhasil, lanjutkan ke MainActivity
        if (account != null) {
            Intent intent = new Intent(RegisterasiActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
