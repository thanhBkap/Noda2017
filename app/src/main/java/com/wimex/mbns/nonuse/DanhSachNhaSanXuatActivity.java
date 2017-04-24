package com.wimex.mbns.nonuse;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wimex.mbns.R;
import com.wimex.mbns.NguoiMua.SapXepNSXTheoTenFragment;

public class DanhSachNhaSanXuatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nsx);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main4, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SapXepNSXTheoTenFragment sapXepNSXTheoTenFragment = new SapXepNSXTheoTenFragment();
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    //SapXepNSXTheoTenFragment sapXepNSXTheoTenFragment = new SapXepNSXTheoTenFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("tab",1);
                    sapXepNSXTheoTenFragment.setArguments(bundle);
                    return sapXepNSXTheoTenFragment;
                case 1:
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("tab",2);
                    sapXepNSXTheoTenFragment.setArguments(bundle1);
                    return sapXepNSXTheoTenFragment;
                    /*SapXepNSXTheoUyTinFragment sapXepNSXTheoUyTinFragment = new SapXepNSXTheoUyTinFragment();
                    return sapXepNSXTheoUyTinFragment;*/

                case 2:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("tab",3);
                    sapXepNSXTheoTenFragment.setArguments(bundle2);
                    return sapXepNSXTheoTenFragment;
                     /*SapXepNSXTheoSoLuongFragment sapXepNSXTheoSoLuongFragment = new SapXepNSXTheoSoLuongFragment();
                    return sapXepNSXTheoSoLuongFragment;*/
               /* case 3:
                    Bundle bundle3 = new Bundle();

                    bundle3.putInt("tab",4);
                    sapXepNSXTheoTenFragment.setArguments(bundle3);
                    return sapXepNSXTheoTenFragment;*/
                    /*SapXepNSXTheoMoiFragment sapXepNSXTheoMoiFragment = new SapXepNSXTheoMoiFragment();
                    return sapXepNSXTheoMoiFragment;*/
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Tên (a-z)";
                case 1:
                    return "Uy tín";
                case 2:
                    return "Nhiều sản phẩm nhất";
               /* case 3:
                    return "Mới";*/
                default:
                    return null;
            }

        }
    }
}
