package com.wimex.mbns;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.wimex.mbns.NguoiMua.TrangChu;

/**
 * Created by MyPC on 1/20/2017.
 */

public class Menu_CT extends AppCompatActivity {
    Toolbar mtoolbar1;
    public static TextView txtCartMenu;
    ImageView imgCartMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mtoolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar1);
        //getSupportActionBar().setTitle("Noda");
        //getSupportActionBar().setWindowTitle("win");
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chitiet, menu);
        MenuItem item = menu.findItem(R.id.giohang);
        MenuItemCompat.setActionView(item, R.layout.cart_number);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        txtCartMenu = (TextView) notifCount.findViewById(R.id.txtCartMenu);
        imgCartMenu= (ImageView) notifCount.findViewById(R.id.imgCartMenu);
        imgCartMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iMoGioHang = new Intent(getApplicationContext(), GioHang.class);
                // Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                startActivity(iMoGioHang);
            }
        });

        if (Auth.gioHang.size()>0){
            txtCartMenu.setVisibility(View.VISIBLE);
            txtCartMenu.setText(""+Auth.gioHang.size());
        }else{
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
            case R.id.quayvetrangchu:
                Intent iveTC = new Intent(getApplicationContext(), TrangChu.class);
                startActivity(iveTC);
                break;
            case R.id.giohang:
                Intent iMoGioHang = new Intent(getApplicationContext(), GioHang.class);
                Toast.makeText(getBaseContext(),"ok",Toast.LENGTH_SHORT).show();
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

}
