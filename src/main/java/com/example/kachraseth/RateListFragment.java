package com.example.kachraseth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;



public class RateListFragment extends Fragment {

    private EditText editTextMessage;
    private Button buttonSend;
    private RecyclerView recyclerViewMessages;

    // Firestore instance and collection reference
    private FirebaseFirestore firestore;
    private CollectionReference chatMessagesRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ratelist, container, false);

        // Initialize Firestore and reference to the collection
        firestore = FirebaseFirestore.getInstance();
        chatMessagesRef = firestore.collection("chatConversations");

        // Initialize views
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);
        recyclerViewMessages = view.findViewById(R.id.recyclerViewMessages);

        // Set up RecyclerView (you need an adapter for displaying messages)
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        // recyclerViewMessages.setAdapter(yourAdapter); // Set up with your adapter here

        // Send button click listener
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    sendMessageToFirestore(message);
                } else {
                    Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void sendMessageToFirestore(String message) {
        // Create a map to hold the message data
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("message", message);
        messageData.put("timestamp", System.currentTimeMillis()); // Optional: for sorting

        // Add the message to Firestore
        chatMessagesRef.add(messageData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Message sent", Toast.LENGTH_SHORT).show();
                        editTextMessage.setText(""); // Clear input field after sending
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to send message", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

