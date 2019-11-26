package com.mor.maslati.image.search.with.pixabay;

import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ImagesHandler imagesHandler;

    private Button button;
    private FloatingSearchView mSearchView;
    private RecyclerView gridRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Visual items creation.
        mSearchView      = findViewById(R.id.floating_search_view);
        button           = findViewById(R.id.searchButton);
        gridRecyclerView = findViewById(R.id.gridLinearLayout);
    }

    @Override
    protected void onStart() {
        super.onStart();

        imagesHandler = ImagesHandler.getInstance(this,gridRecyclerView);

        // On Click Listener for Search-Button.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Dismiss keyboard.
                InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

                // Make the network call.
                imagesHandler.queryImages( mSearchView.getQuery() );
            }
        });


        // On Click Listener for Search field and keyboard Submit (Enter) Button.
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {

            @Override
            public void onSearchAction(String currentQuery) {
                //Log.d("Mor","onSearchAction( currentQuery ="+currentQuery+"  )");

                // Make the network call.
                imagesHandler.queryImages( currentQuery );
            }

            @Override
            public void onSuggestionClicked(SearchSuggestion currentText) {
                //Log.d("Mor","onSuggestionClicked( currentText ="+currentText+"  )");
            }
        });

    }
}
