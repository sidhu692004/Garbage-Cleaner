package com.example.kachraseth;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class SellScrapFragment extends Fragment {

    private CardView cardPlastic, cardPaper, cardMetal, cardEwaste, cardOthers;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sellscrap, container, false);

        // Initialize views
        searchView = view.findViewById(R.id.searchView);
        cardPlastic = view.findViewById(R.id.cardPlastic);
        cardPaper = view.findViewById(R.id.cardPaper);
        cardMetal = view.findViewById(R.id.cardMetal);
        cardEwaste = view.findViewById(R.id.cardEwaste);
        cardOthers = view.findViewById(R.id.cardOthers);

        // Click Listeners for each card
        setCardClickListeners();

        // Implement search filtering
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCards(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCards(newText);
                return false;
            }
        });

        return view;
    }

    private void setCardClickListeners() {
        cardPlastic.setOnClickListener(v -> openActivity(PlasticActivity.class));
        cardPaper.setOnClickListener(v -> openActivity(PaperActivity.class));
        cardMetal.setOnClickListener(v -> openActivity(MetalActivity.class));
        cardEwaste.setOnClickListener(v -> openActivity(EwasteActivity.class));
        cardOthers.setOnClickListener(v -> openActivity(OthersActivity.class));
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        startActivity(intent);
    }

    private void filterCards(String query) {
        query = query.toLowerCase().trim();

        // Show/hide cards based on the search query
        cardPlastic.setVisibility(query.isEmpty() || "plastic".contains(query) ? View.VISIBLE : View.GONE);
        cardPaper.setVisibility(query.isEmpty() || "paper".contains(query) ? View.VISIBLE : View.GONE);
        cardMetal.setVisibility(query.isEmpty() || "metal".contains(query) ? View.VISIBLE : View.GONE);
        cardEwaste.setVisibility(query.isEmpty() || "e-waste ewaste".contains(query) ? View.VISIBLE : View.GONE);
        cardOthers.setVisibility(query.isEmpty() || "others".contains(query) ? View.VISIBLE : View.GONE);
    }
}
