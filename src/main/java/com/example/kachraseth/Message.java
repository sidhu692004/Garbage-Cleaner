package com.example.kachraseth;

public class Message {
    private String message;
    private long timestamp; // Use long if storing timestamp in milliseconds
    // Required empty constructor for Firestore deserialization
    public Message() {}
    public Message(String message, long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Getter for timestamp
    public long getTimestamp() {
        return timestamp;
    }

    // Additional setters if needed
    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}


