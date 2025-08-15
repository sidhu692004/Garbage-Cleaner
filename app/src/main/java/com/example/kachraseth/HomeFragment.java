package com.example.kachraseth;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Find views inside onCreateView
        EditText donate = view.findViewById(R.id.donate);
        Button btnDonate = view.findViewById(R.id.btnDonate);

        btnDonate.setOnClickListener(v -> {
            String upiId = "8228958397@ibl";  // Receiver UPI ID
            String name = "Sudhanshu";  // Receiver Name
            String amount = donate.getText().toString().trim();  // Get Amount
            String transactionNote = "Donation for Society cleaning";

            // Validation: Amount empty na ho
            if (amount.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a valid amount", Toast.LENGTH_SHORT).show();
                return; // Stop execution
            }

            Uri uri = Uri.parse("upi://pay")
                    .buildUpon()
                    .appendQueryParameter("pa", upiId)   // Payee UPI ID
                    .appendQueryParameter("pn", name)    // Payee Name
                    .appendQueryParameter("tn", transactionNote) // Transaction Note
                    .appendQueryParameter("am", amount)   // Amount
                    .appendQueryParameter("cu", "INR")    // Currency (INR)
                    .build();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);

            // Open any available UPI app
            Intent chooser = Intent.createChooser(intent, "Pay with UPI");
            try {
                startActivity(chooser);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getContext(), "No UPI app found. Please install one.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
