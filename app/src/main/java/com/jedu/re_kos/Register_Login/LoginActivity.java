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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.model.DataModel;
import com.jedu.re_kos.viewmodel.DataViewModel;
import com.jedu.re_kos.model.LoginResponse;

public class LoginActivity extends AppCompatActivity {
    private DataViewModel viewModel;
    private Button buttonLogin, googleSignInButton;
    private EditText editEmail, editPassword;
    private TextView signup;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        // Warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.biru_navbar));

        // Button login dan Google Sign-In
        buttonLogin = findViewById(R.id.buttonLogin);
        googleSignInButton = findViewById(R.id.buttonGoogle);

        // Cek apakah user sudah login
        int userId = sharedPreferences.getInt("user_id", 0);
        if (userId != 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        // Menuju ke sign up
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterasiActivity.class);
            startActivity(intent);
        });

        // EditText
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        // Login dengan email dan password
        buttonLogin.setOnClickListener(v -> {
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            viewModel.login(email, password).observe(this, loginResponse -> {
                if (loginResponse.getStatus().equals("success") && loginResponse.getData() != null) {
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
                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Konfigurasi Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id)) // Ganti dengan Client ID Anda
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Login dengan Google
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleGoogleSignInResult(task);
        }
    }

    private void handleGoogleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                String email = account.getEmail();
                String idToken = account.getIdToken();

                // Simpan data ke SharedPreferences
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_email", email);
                editor.apply();

                Toast.makeText(this, "Login sukses dengan Google: " + email, Toast.LENGTH_SHORT).show();

                // Arahkan ke MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } catch (ApiException e) {
            Toast.makeText(this, "Login gagal: " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(String Email, int ID, String role) {
        DataModel dataModel = new DataModel();
        dataModel.setEmail(Email);
        dataModel.setId(ID);
        dataModel.setRole(role);

        this.viewModel.setData(dataModel);
    }
}
