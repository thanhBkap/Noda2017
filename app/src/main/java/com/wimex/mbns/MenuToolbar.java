package com.wimex.mbns;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.NguoiMua.GioHang;

/**
 * Created by MyPC on 1/20/2017.
 */

public class MenuToolbar extends AppCompatActivity implements SearchView.OnQueryTextListener {
    Toolbar mtoolbar;
    public static TextView txtCartMenu;
    ImageView imgCartMenu;
    SearchView searchview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        //getSupportActionBar().setTitle("Noda");
        //getSupportActionBar().setWindowTitle("win");
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem msearchview = menu.findItem(R.id.timkiem);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchview = (SearchView) msearchview.getActionView();
        searchview.setQueryHint("Tìm kiếm theo tên sản phẩm");
        searchview.setOnQueryTextListener(MenuToolbar.this);
        searchview.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchview.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    searchview.clearFocus();
                    Intent mMoveToSearchActivity = new Intent(getApplicationContext(), SearchActivity.class);
                    // mMoveToSearchActivity.putExtra("key",newText);
                    startActivity(mMoveToSearchActivity);
                }
            }
        });
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
        if (Auth.gioHang.size() > 0) {
            txtCartMenu.setVisibility(View.VISIBLE);
            txtCartMenu.setText("" + Auth.gioHang.size());
        } else {
            txtCartMenu.setVisibility(View.INVISIBLE);
        }
        /*RelativeLayout badgeLayout = (RelativeLayout)    menu.findItem(R.id.giohang).getActionView();
        TextView tv = (TextView) badgeLayout.findViewById(R.id.txtCartMenu);
        tv.setText("12");*/
        /*MenuItem itemCart = menu.findItem(R.id.giohang);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, "9");*/
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.timkiem:
                break;
            case R.id.giohang:
                Intent iMoGioHang = new Intent(getApplicationContext(), GioHang.class);
                Toast.makeText(getBaseContext(), "ok", Toast.LENGTH_SHORT).show();
                startActivity(iMoGioHang);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /*public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;
        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }*/
    //khi người dùng  click vào enter (SearchView)
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //khi người dùng gõ phím (SearchView)
    @Override



    public boolean onQueryTextChange(String newText) {
        // Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_LONG).show();
        Intent mMoveToSearchActivity = new Intent(getApplicationContext(), SearchActivity.class);
       // mMoveToSearchActivity.putExtra("key",newText);
        startActivity(mMoveToSearchActivity);
        return false;
    }
}
