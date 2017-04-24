package com.wimex.mbns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.SearchListAdapter;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.Search;
import com.wimex.mbns.NguoiMua.GioHang;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.wimex.mbns.R.menu.menu_search;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ListView lvSearch;
    SearchListAdapter searchListAdapter;
    Toolbar mtoolbar;
    SearchView searchview;
    public static TextView txtCartMenu;
    ImageView imgCartMenu;
    ArrayList<Search> listSearch;
    ArrayList<Search> listSearchFiltered;
    public static RelativeLayout layout_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        addControls();
        addEvents();
    }

    private void addControls() {
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvSearch = (ListView) findViewById(R.id.lvSearch);
        layout_search = (RelativeLayout) findViewById(R.id.layout_search);
        listSearch = new ArrayList<>();
        listSearchFiltered = new ArrayList<>();
        searchListAdapter = new SearchListAdapter(SearchActivity.this, listSearch, listSearchFiltered);
        lvSearch.setAdapter(searchListAdapter);
        HashMap<String, String> postData = new HashMap<>();
        PostResponseAsyncTask mSearchTask = new PostResponseAsyncTask(SearchActivity.this, postData, "Đang tải", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                try {
                    JSONArray root = new JSONArray(s);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject mSearch = root.getJSONObject(i);
                        Search itemSearch = new Search();
                        itemSearch.setId(mSearch.getString("id"));
                        itemSearch.setTenSanPham(mSearch.getString("ten_san_pham"));
                        listSearch.add(itemSearch);
                        listSearchFiltered.add(itemSearch);
                        searchListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        mSearchTask.setLoadingMessage(getResources().getString(R.string.loading));
        mSearchTask.setEachExceptionsHandler(new EachExceptionsHandler() {
            @Override
            public void handleIOException(IOException e) {

            }

            @Override
            public void handleMalformedURLException(MalformedURLException e) {

            }

            @Override
            public void handleProtocolException(ProtocolException e) {
                Toast.makeText(getBaseContext(), getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {

            }
        });
        mSearchTask.execute(Auth.domain + "/search_activity_json.php");

    }

    private void addEvents() {
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Search search = (Search) parent.getItemAtPosition(position);
                //  Toast.makeText(getBaseContext(),search.getId()+"=="+search.getTenSanPham(),Toast.LENGTH_LONG).show();
                Intent mMoveToSearchResultActivity = new Intent(getApplicationContext(), SearchResultActivity.class);
                mMoveToSearchResultActivity.putExtra("id", search.getId());
                mMoveToSearchResultActivity.putExtra("key", search.getTenSanPham());
                startActivity(mMoveToSearchResultActivity);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(menu_search, menu);
        MenuItem msearchview = menu.findItem(R.id.timkiem);
        //  SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //focus in search view* notice that must set always to menu item search view
        searchview = (SearchView) msearchview.getActionView();
        searchview.setOnQueryTextListener(SearchActivity.this);
        //  searchview.setQuery("", true);
        searchview.setFocusable(true);
        searchview.setIconified(false);
        searchview.requestFocusFromTouch();
        Intent i = getIntent();
        /*if (i.hasExtra("key")){
            searchview.setQuery(i.getStringExtra("key"),false);
        }*/
        // for item cart
        MenuItem item = menu.findItem(R.id.giohang);
        MenuItemCompat.setActionView(item, R.layout.cart_number);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        txtCartMenu = (TextView) notifCount.findViewById(R.id.txtCartMenu);
        imgCartMenu = (ImageView) notifCount.findViewById(R.id.imgCartMenu);
        imgCartMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMoGioHang = new Intent(getApplicationContext(), GioHang.class);
                // Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                startActivity(iMoGioHang);
            }
        });
        //show or not show cart number
        if (Auth.gioHang.size() > 0) {
            txtCartMenu.setVisibility(View.VISIBLE);
            txtCartMenu.setText("" + Auth.gioHang.size());
        } else {
            txtCartMenu.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            super.onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // Toast.makeText(getBaseContext(),"ngon",Toast.LENGTH_LONG).show();
        searchListAdapter.getFilter().filter(newText);
        searchListAdapter.notifyDataSetChanged();
        return true;
    }
}
