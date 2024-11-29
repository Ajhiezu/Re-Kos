package com.jedu.re_kos.Register_Login;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.jedu.re_kos.Detail.AjukanSewaActivity;
import com.jedu.re_kos.MainActivity;
import com.jedu.re_kos.R;
import com.jedu.re_kos.model.DataModel;
import com.jedu.re_kos.viewmodel.DataViewModel;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private DataViewModel viewModel;
    private Button buttonLogin, googleSignInButton;
    private EditText editEmail, editPassword;
    private TextView signup;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(DataViewModel.class);

        // Initialize Google Sign-In Client
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Pastikan ini benar
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //warna navigasi bar
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.biru_navbar));

        // Initialize views
        buttonLogin = findViewById(R.id.buttonLogin);
        googleSignInButton = findViewById(R.id.buttonGoogle);
        signup = findViewById(R.id.signup);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        int userId = sharedPreferences.getInt("user_id", 0);
        if (userId != 0) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }

        // Regular email/password login
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

        // Google Sign-In button
        googleSignInButton.setOnClickListener(v -> signInWithGoogle());

        // Sign-up redirection
        signup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterasiActivity.class);
            startActivity(intent);
        });
    }

    // Google Sign-In method
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                // Sign-In Success
                updateUI(account);
            } catch (ApiException e) {
                Log.w("Google Sign-In", "signInResult:failed code=" + e.getStatusCode());
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Handle the successful Google Sign-In
    private void updateUI(GoogleSignInAccount account) {
        // Extract user details and save in SharedPreferences or your ViewModel
        String email = account.getEmail();
        String id = account.getId();

        // Save Google User info to SharedPreferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("google_email", email);
        editor.putString("google_id", id);
        editor.apply();

        // Redirect to main activity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
