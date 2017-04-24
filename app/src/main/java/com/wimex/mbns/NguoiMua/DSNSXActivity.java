package com.wimex.mbns.NguoiMua;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.NhaSanXuat;
import com.wimex.mbns.R;

import java.util.ArrayList;

public class DSNSXActivity extends Menu_CT {
    private SapXepNSXTheoTenFragment m1Fragment;
    private UyTinNSXFragment m2Fragment;
    private SoLuongNSXFrament m3Fragment;
    public static ArrayList<NhaSanXuat> listNhaSanXuat;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dsnsx);
        super.onCreate(savedInstanceState);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //  Toast.makeText(getApplicationContext(),"on cre",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Toast.makeText(getApplicationContext(),"on ressume",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //  Toast.makeText(getApplicationContext(),"on pause dsnsx",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        Auth.checked = "0";

        //  Toast.makeText(getApplicationContext(),"on stop dsnsx",Toast.LENGTH_LONG).show();
        super.onStop();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new SapXepNSXTheoTenFragment();

                case 1:
                    return new UyTinNSXFragment();

                case 2:
                    return new SoLuongNSXFrament();

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
                default:
                    return null;
            }

        }
    }
}
