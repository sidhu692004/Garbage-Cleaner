package com.example.kachraseth;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class EwasteActivity extends AppCompatActivity {

    private SearchView searchViewEwaste;
    private CardView cardAirConditioner, cardFridge, cardCPU, cardBulbLED, cardMobileBattery, cardPrinter, cardEwaste, cardKeywords;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ewaste);

        // Initialize views
        searchViewEwaste = findViewById(R.id.searchViewEwaste);
        cardAirConditioner = findViewById(R.id.cardAirConditioner);
        cardFridge = findViewById(R.id.cardFridge);
        cardCPU = findViewById(R.id.cardCPU);
        cardBulbLED = findViewById(R.id.cardBulbLED);
        cardMobileBattery = findViewById(R.id.cardMobileBattery);
        cardPrinter = findViewById(R.id.cardPrinter);
        cardEwaste = findViewById(R.id.cardEwaste);
        cardKeywords = findViewById(R.id.cardKeywords);

        // Set OnClickListeners for each card
        setCardListeners();

        // Set up search functionality
        setupSearch();
    }

    private void setCardListeners() {
        View.OnClickListener listener = v -> {
            String itemName = "";
            if (v == cardAirConditioner) itemName = "Air Conditioner";
            else if (v == cardFridge) itemName = "Fridge";
            else if (v == cardCPU) itemName = "CPU";
            else if (v == cardBulbLED) itemName = "Bulb/LED";
            else if (v == cardMobileBattery) itemName = "Mobile Battery";
            else if (v == cardPrinter) itemName = "Printer";
            else if (v == cardEwaste) itemName = "E-Waste";
            else if (v == cardKeywords) itemName = "Keywords";

            Toast.makeText(EwasteActivity.this, itemName + " selected", Toast.LENGTH_SHORT).show();
        };

        cardAirConditioner.setOnClickListener(listener);
        cardFridge.setOnClickListener(listener);
        cardCPU.setOnClickListener(listener);
        cardBulbLED.setOnClickListener(listener);
        cardMobileBattery.setOnClickListener(listener);
        cardPrinter.setOnClickListener(listener);
        cardEwaste.setOnClickListener(listener);
        cardKeywords.setOnClickListener(listener);
    }

    private void setupSearch() {
        searchViewEwaste.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCards(newText);
                return false;
            }
        });
    }

    private void filterCards(String query) {
        query = query.toLowerCase().trim();

        // Show/hide cards based on the search query
        cardAirConditioner.setVisibility(query.isEmpty() || "air conditioner ac".contains(query) ? View.VISIBLE : View.GONE);
        cardFridge.setVisibility(query.isEmpty() || "fridge refrigerator".contains(query) ? View.VISIBLE : View.GONE);
        cardCPU.setVisibility(query.isEmpty() || "cpu processor".contains(query) ? View.VISIBLE : View.GONE);
        cardBulbLED.setVisibility(query.isEmpty() || "bulb led".contains(query) ? View.VISIBLE : View.GONE);
        cardMobileBattery.setVisibility(query.isEmpty() || "mobile battery".contains(query) ? View.VISIBLE : View.GONE);
        cardPrinter.setVisibility(query.isEmpty() || "printer".contains(query) ? View.VISIBLE : View.GONE);
        cardEwaste.setVisibility(query.isEmpty() || "ewaste e-waste".contains(query) ? View.VISIBLE : View.GONE);
        cardKeywords.setVisibility(query.isEmpty() || "keywords".contains(query) ? View.VISIBLE : View.GONE);
    }
}
