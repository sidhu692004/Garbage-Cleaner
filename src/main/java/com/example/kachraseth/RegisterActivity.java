package com.example.kachraseth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputUsername, inputEmail, inputPassword, inputConfirmPassword;
    private Button btnRegister;
    private TextView alreadyHaveAccount;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        alreadyHaveAccount = findViewById(R.id.alreadyhaveaccount);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Set up the click listener for the Register button
        btnRegister.setOnClickListener(view -> {
            String username = inputUsername.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            String confirmPassword = inputConfirmPassword.getText().toString().trim();

            // Validate input fields
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || !password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
            } else {
                // Create user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, task -> {
                            if (task.isSuccessful()) {
                                // Send email verification
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    user.sendEmailVerification()
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    Toast.makeText(RegisterActivity.this, "Registration successful. Please verify your email.", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                                } else {
                                                    String errorMessage = task1.getException() != null ? task1.getException().getMessage() : "Failed to send verification email.";
                                                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration failed.";
                                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        alreadyHaveAccount.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, MainActivity.class)));
    }
}
