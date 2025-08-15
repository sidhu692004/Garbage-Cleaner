package com.example.kachraseth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class forgetpassword extends AppCompatActivity {
    private EditText inputEmail;
    private Button btnSubmit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        inputEmail = findViewById(R.id.inputEmail);
        btnSubmit = findViewById(R.id.btnSubmitEmail);

        mAuth = FirebaseAuth.getInstance();

        btnSubmit.setOnClickListener(view -> {
            String email = inputEmail.getText().toString().trim();

            if (email.isEmpty()) {
                Toast.makeText(forgetpassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
            } else {
                // Send password reset email
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Email sent successfully
                                Toast.makeText(forgetpassword.this, "Password reset email sent. Please check your inbox.", Toast.LENGTH_SHORT).show();
                                // Optionally, you could redirect the user to a different activity or screen here
                                startActivity(new Intent(forgetpassword.this, MainActivity.class));
                            } else {
                                // Error sending email
                                Toast.makeText(forgetpassword.this, "Failed to send password reset email: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
