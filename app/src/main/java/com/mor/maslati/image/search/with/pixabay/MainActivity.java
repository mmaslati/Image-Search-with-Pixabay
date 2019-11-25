package com.mor.maslati.image.search.with.pixabay;

import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Network network;

    private Button button;
    private FloatingSearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Network Module for network calls.
        network = new Network(this);

        // Visual items creation.
        mSearchView    = findViewById(R.id.floating_search_view);
        button         = findViewById(R.id.searchButton);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // On Click Listener for Search-Button.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Dismiss keyboard.
                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

                // Make the network call.
                network.getImagesByQueryString(mSearchView.getQuery(), 1);
            }
        });


        // On Click Listener for Search
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {

            @Override
            public void onSearchAction(String currentQuery) {
                //Log.d("Mor","onSearchAction( currentQuery ="+currentQuery+"  )");

                // Make the network call.
                network.getImagesByQueryString(currentQuery, 1);
            }

            @Override
            public void onSuggestionClicked(SearchSuggestion currentText) {
                //Log.d("Mor","onSuggestionClicked( currentText ="+currentText+"  )");
            }
        });

    }
}
