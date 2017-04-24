package com.wimex.mbns.NguoiMua;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.wimex.mbns.MenuToolbar;
import com.wimex.mbns.R;

public class NhaSanXuatActivity extends MenuToolbar {
    String nsxId="none";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nha_san_xuat);
        super.onCreate(savedInstanceState);

       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        Intent i = getIntent();
        if (i.hasExtra("NSXId")){
            nsxId=i.getStringExtra("NSXId");
        }

    }

    @Override
    public void onBackPressed() {
      /*  Intent mMoveToDanhSachNSX = new Intent(getApplicationContext(),DanhSachNhaSanXuatActivity.class);
       // mMoveToDanhSachNSX.
      //  Intent mMoveToDanhSachNSX = new Intent(getApplicationContext(),TrangChu.class);
        SapXepNSX.sosanh(Auth.sapXepNSXTheoTenList,1);
        startActivity(mMoveToDanhSachNSX);*/
        super.onBackPressed();
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nha_san_xuat, menu);
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
    }*/

    /**
     * A placeholder fragment containing a simple view.
     */
   /* public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.gioi_thieu_nha_san_xuat_fragment, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }*/

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle= new Bundle();
            bundle.putString("nsxId",nsxId);
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    GianHangNhaSanXuatFragment gianHangNhaSanXuatFragment = new GianHangNhaSanXuatFragment();
                    gianHangNhaSanXuatFragment.setArguments(bundle);
                    return gianHangNhaSanXuatFragment;
                case 1:
                    GioiThieuNhaSanXuatFragment gioiThieuNhaSanXuatFragment = new GioiThieuNhaSanXuatFragment();
                    gioiThieuNhaSanXuatFragment.setArguments(bundle);
                    return gioiThieuNhaSanXuatFragment;

                case 2:
                    ThongTinNhaSanXuatFragment thongTinNhaSanXuatFragment = new ThongTinNhaSanXuatFragment();
                    thongTinNhaSanXuatFragment.setArguments(bundle);
                    return thongTinNhaSanXuatFragment;
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
                    return "Gian hàng";
                case 1:
                    return "Giới thiệu";
                case 2:
                    return "Thông tin";
            }
            return null;
        }
    }
}
