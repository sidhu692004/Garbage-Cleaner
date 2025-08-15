package com.example.kachraseth;

import android.os.Bundle;
import android.text.TextUtils;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PaperActivity extends AppCompatActivity {

    private SearchView searchViewPaper;
    private final Handler searchHandler = new Handler();
    private Runnable searchRunnable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);

        // Initialize SearchView
        searchViewPaper = findViewById(R.id.searchViewPaper);

        // Implement search with a debounce mechanism
        searchViewPaper.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    searchHandler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> filterItems(newText);
                searchHandler.postDelayed(searchRunnable, 300); // 300ms debounce delay

                return true;
            }
        });
    }

    // Function to filter items based on search query
    private void filterItems(String query) {
        ScrollView scrollView = findViewById(R.id.scrollView);
        LinearLayout layout = (LinearLayout) scrollView.getChildAt(0);

        // Convert query to lowercase and trim spaces
        query = query.toLowerCase().trim();

        for (int i = 0; i < layout.getChildCount(); i++) {
            View view = layout.getChildAt(i);

            if (view instanceof CardView) {
                CardView cardView = (CardView) view;

                // Get the RelativeLayout inside the CardView
                View cardChild = cardView.getChildAt(0);
                if (cardChild instanceof LinearLayout || cardChild instanceof RelativeLayout) {
                    View firstChild = ((ViewGroup) cardChild).getChildAt(0);

                    // Ensure the first child is a TextView (Item Name)
                    if (firstChild instanceof TextView) {
                        TextView textView = (TextView) firstChild;
                        String itemName = textView.getText().toString().toLowerCase();

                        // Show or hide the card based on search query
                        if (TextUtils.isEmpty(query) || itemName.contains(query)) {
                            cardView.setVisibility(View.VISIBLE);
                        } else {
                            cardView.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }
    }
}
