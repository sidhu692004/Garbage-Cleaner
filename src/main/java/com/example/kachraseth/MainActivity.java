package com.example.kachraseth;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN_GOOGLE = 1001;
    private EditText inputEmail, inputPassword;
    private Button btnLogin, btnGoogleSignIn;
    private TextView txtForgetPassword;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtForgetPassword = findViewById(R.id.inputForgetPassword);
        btnGoogleSignIn = findViewById(R.id.btnGoogle);

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Handle Sign-in with Email/Password
        btnLogin.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity .this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    if (user.isEmailVerified()) {
                                        startActivity(new Intent(MainActivity.this, homeactivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Email not verified. Please verify your email.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "Sign-in failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Handle Google Sign-In
        btnGoogleSignIn.setOnClickListener(view -> signInWithGoogle());

        // Redirect to Register page
        TextView btnRegister = findViewById(R.id.textviewSignup);
        btnRegister.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        // Redirect to Forget Password page
        txtForgetPassword.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, forgetpassword.class)));
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_GOOGLE) {
            try {
                GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
                if (account != null) {
                    Log.d("Google Sign-In", "Account: " + account.getEmail());
                    firebaseAuthWithGoogle(account.getIdToken());
                }
            } catch (ApiException e) {
                Log.w("Google Sign-In", "Google sign in failed: " + e.getStatusCode(), e);
                Toast.makeText(this, "Google sign-in failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d("Google Sign-In", "Firebase sign-in successful: " + (user != null ? user.getEmail() : "No user"));
                        startActivity(new Intent(MainActivity.this, homeactivity.class));
                        finish();
                    } else {
                        Log.w("Google Sign-In", "signInWithCredential:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Google sign-in failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    protected void onStart() {
        super.onStart();

        // Check if a user is already signed in
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null && currentUser.isEmailVerified()) {
            // User is logged in and email is verified, redirect to home
            startActivity(new Intent(MainActivity.this, homeactivity.class));
            finish(); // Close Login Activity
        }
    }
}

