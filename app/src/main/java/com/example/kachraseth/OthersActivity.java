package com.example.kachraseth;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.util.ArrayList;
import java.util.List;

public class OthersActivity extends AppCompatActivity {

    private SearchView searchViewOthers;
    private TextView noResultsText;
    private List<CardView> cardViews = new ArrayList<>();
    private List<String> itemNames = new ArrayList<>(); // To store corresponding names for search

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others); // Ensure this layout exists

        // Initialize SearchView
        searchViewOthers = findViewById(R.id.searchViewOthers);
        searchViewOthers.setIconifiedByDefault(false); // Keep it open for user convenience

        // Initialize "No Results Found" TextView
        noResultsText = findViewById(R.id.noResultsText);
        noResultsText.setVisibility(View.GONE); // Initially hidden

        // Add CardViews and their corresponding text
        addCardViews();

        // Setup Search Functionality
        setupSearchView();
    }

    private void addCardViews() {
//        // Initialize CardViews and corresponding names
//        cardViews.add(findViewById(R.id.cardWomenHair));
//        itemNames.add("Women Hair");

        cardViews.add(findViewById(R.id.cardBattery));
        itemNames.add("Battery");

        cardViews.add(findViewById(R.id.cardTyre));
        itemNames.add("Tyre");

        cardViews.add(findViewById(R.id.cardMixWaste));
        itemNames.add("Mix-Waste");

        cardViews.add(findViewById(R.id.cardBeerBottles));
        itemNames.add("Beer Bottles");

        cardViews.add(findViewById(R.id.cardOldClothes));
        itemNames.add("Old Clothes");

        cardViews.add(findViewById(R.id.cardMachines));
        itemNames.add("Machines");
    }

    private void setupSearchView() {
        searchViewOthers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterItems(newText);
                return true;
            }
        });
    }

    private void filterItems(String query) {
        query = query.toLowerCase().trim();
        boolean hasResults = false;

        for (int i = 0; i < cardViews.size(); i++) {
            String itemName = itemNames.get(i).toLowerCase();
            CardView cardView = cardViews.get(i);

            if (itemName.contains(query) || query.isEmpty()) {
                cardView.setVisibility(View.VISIBLE);
                hasResults = true;
            } else {
                cardView.setVisibility(View.GONE);
            }
        }

        // Show "No Results Found" message if nothing matches
        noResultsText.setVisibility(hasResults ? View.GONE : View.VISIBLE);
    }
}
