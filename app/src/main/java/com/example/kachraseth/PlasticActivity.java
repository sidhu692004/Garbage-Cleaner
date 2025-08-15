package com.example.kachraseth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PlasticActivity extends AppCompatActivity {

    private SearchView searchViewPlastic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plastic);

        // Initialize the search view
        searchViewPlastic = findViewById(R.id.searchViewPlastic);

        // Implement search functionality
        searchViewPlastic.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    // Function to filter items based on search query
    private void filterItems(String query) {
        ScrollView scrollView = findViewById(R.id.scrollViewPlastic);
        LinearLayout layout = (LinearLayout) scrollView.getChildAt(0);

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);

            if (view instanceof CardView) {
                CardView cardView = (CardView) view;

                // Try to find a TextView dynamically within each CardView
                TextView titleTextView = cardView.findViewById(R.id.textPlasticSoft);
                if (titleTextView == null) titleTextView = cardView.findViewById(R.id.textPlasticHard);
                if (titleTextView == null) titleTextView = cardView.findViewById(R.id.textOilContainer);
                if (titleTextView == null) titleTextView = cardView.findViewById(R.id.textPlasticJar);
                if (titleTextView == null) titleTextView = cardView.findViewById(R.id.textPlasticBori);
                if (titleTextView == null) titleTextView = cardView.findViewById(R.id.textPolythene);

                if (titleTextView != null) {
                    String itemName = titleTextView.getText().toString().toLowerCase();
                    if (TextUtils.isEmpty(query) || itemName.contains(query.toLowerCase())) {
                        cardView.setVisibility(View.VISIBLE);
                    } else {
                        cardView.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

}
