package com.wimex.mbns;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.wimex.mbns.Adapter.SearchListAdapter;
import com.wimex.mbns.Model.Search;

import java.util.ArrayList;

public class SearchableActivity extends AppCompatActivity {
    ListView search_list;
    ArrayList<Search> arrayListSearch;
    SearchListAdapter searchListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

       /* search_list= (ListView) findViewById(R.id.search_list);
        arrayListSearch = new ArrayList<>();
        searchListAdapter = new SearchListAdapter(SearchableActivity.this,arrayListSearch);
        search_list.setAdapter(searchListAdapter);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }*/

    }

    private void doMySearch(String query) {
        Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
    }
}
