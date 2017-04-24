package com.wimex.mbns.NhaSanXuat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.MainActivity;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.XuLyData.JsonToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;

public class TrangChuNSXActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout layoutTrangChuNSXThongTinGioiThieu,
            layoutTrangChuNSXThongTinLienHe,
            layoutTrangChuNSXDanhMucSanPham,
            layoutTrangChuNSXSanPham,
            layoutTrangChuNSXThongKeGiaoDich,
            layoutTrangChuNSXDiemUyTin;
    TextView txtTrangChuNSXSoLuongSanPham,
            txtTrangChuNSXSoLuongDanhMuc,
            txtTrangChuNSXSoKhachGiaoDich,
            txtTrangChuNSXDoanhSo;
    float tongTien = 0.0f;
    int soKhach = 0;
    float soSanPham = 0.0f;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_trang_chu_nsx);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addControls();
        addEvents();
    }
    private void addControls() {
        layoutTrangChuNSXDanhMucSanPham = (LinearLayout) findViewById(R.id.layoutTrangChuNSXDanhMucSanPham);
        layoutTrangChuNSXThongTinGioiThieu = (LinearLayout) findViewById(R.id.layoutTrangChuNSXThongTinGioiThieu);
        layoutTrangChuNSXThongTinLienHe = (LinearLayout) findViewById(R.id.layoutTrangChuNSXThongTinLienHe);
        layoutTrangChuNSXSanPham = (LinearLayout) findViewById(R.id.layoutTrangChuNSXSanPham);
        layoutTrangChuNSXThongKeGiaoDich = (LinearLayout) findViewById(R.id.layoutTrangChuNSXThongKeGiaoDich);
        layoutTrangChuNSXDiemUyTin = (LinearLayout) findViewById(R.id.layoutTrangChuNSXDiemUyTin);
        txtTrangChuNSXDoanhSo = (TextView) findViewById(R.id.txtTrangChuNSXDoanhSo);
        txtTrangChuNSXSoLuongSanPham = (TextView) findViewById(R.id.txtTrangChuNSXSoLuongSanPham);
        txtTrangChuNSXSoLuongDanhMuc = (TextView) findViewById(R.id.txtTrangChuNSXSoLuongDanhMuc);
        txtTrangChuNSXSoKhachGiaoDich = (TextView) findViewById(R.id.txtTrangChuNSXSoKhachGiaoDich);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
        //        new Task().execute("http://mbns.esy.es/trangchunsxv1.php");
                //new Task2().execute("http://mbns.esy.es/trangchunsxv2.php");
            }
        });
        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", Auth.khachHangId);
        // lấy số sản phẩm, số khách, số tiền
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //  Toast.makeText(TaoGianHangActivity.this, s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                try {
                    JSONArray root = new JSONArray(s);
                    ArrayList<String> listKhach = new ArrayList<>();
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                            if (listKhach.size() == 0) {
                                listKhach.add(data.getString("nguoimua"));
                            } else {
                                int trung = 0;
                                for (int j = 0; j < listKhach.size(); j++) {
                                    if (listKhach.get(j).equals(data.getString("nguoimua"))) {
                                        trung++;
                                        break;
                                    }
                                }
                                if (trung == 0) {
                                    listKhach.add(data.getString("nguoimua"));
                                }
                            }
                            soSanPham += Float.parseFloat(data.getString("soLuong"));
                            tongTien += Float.parseFloat(data.getString("thanhTien"));
                    }
                    soKhach = listKhach.size();
                    txtTrangChuNSXDoanhSo.setText(DinhDangSo.fixNumnerToString(Math.round(tongTien)) + " đ");
                    txtTrangChuNSXSoKhachGiaoDich.setText(soKhach + "");
                    txtTrangChuNSXSoLuongSanPham.setText(soSanPham + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        postResponseAsyncTask.setLoadingMessage(getResources().getString(R.string.loading));
        postResponseAsyncTask.setEachExceptionsHandler(new EachExceptionsHandler() {
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
        postResponseAsyncTask.execute(Auth.domain+"/trangchunsxv1.php");

        // lấy số mục sản phẩm
        HashMap<String, String> postData2 = new HashMap<>();
        postData2.put("id", Auth.khachHangId);
        // lấy số sản phẩm, số khách, số tiền
        PostResponseAsyncTask postResponseAsyncTask2 = new PostResponseAsyncTask(this, postData2, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //  Toast.makeText(TaoGianHangActivity.this, s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                String num = "0";
                try {
                    JSONArray root = new JSONArray(s);
                    ArrayList<String> listKhach = new ArrayList<>();
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                            num = data.getString("soloaisanpham");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                txtTrangChuNSXSoLuongDanhMuc.setText(num);
            }
        });
        postResponseAsyncTask2.setLoadingMessage(getResources().getString(R.string.loading));
        postResponseAsyncTask2.setEachExceptionsHandler(new EachExceptionsHandler() {
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
        postResponseAsyncTask2.execute(Auth.domain+"/trangchunsxv2.php");

    }

    private void addEvents() {
        layoutTrangChuNSXThongTinGioiThieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMoveToTTMT = new Intent(TrangChuNSXActivity.this, ThongTinMoTaActivity.class);
                startActivity(mMoveToTTMT);
            }
        });
        layoutTrangChuNSXThongTinLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMoveToTTTK = new Intent(TrangChuNSXActivity.this, ThongTinTaiKhoanNSXActivity.class);
                startActivity(mMoveToTTTK);
            }
        });

        layoutTrangChuNSXThongKeGiaoDich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMoveToLichSu = new Intent(TrangChuNSXActivity.this, LichSuBanHangActivity.class);
                startActivity(mMoveToLichSu);
            }
        });
        layoutTrangChuNSXDanhMucSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMoveToDanhMucNSX = new Intent(TrangChuNSXActivity.this, DanhMucSanPhamNSXActivity.class);
                startActivity(mMoveToDanhMucNSX);
            }
        });
        layoutTrangChuNSXSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMoveToSanPhamNSX = new Intent(TrangChuNSXActivity.this, SanPhamNSXActivity.class);
                startActivity(mMoveToSanPhamNSX);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void onBackPressed() {
       /* if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show();
        }

        mBackPressed = System.currentTimeMillis();*/

        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
            startActivity(intent);
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn nút quay lại thêm lần nữa để thoát", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trang_chu_nsx, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_tao_gian_hang) {
            Intent mMoveToTaoHangHoa = new Intent(TrangChuNSXActivity.this, TaoGianHangActivity.class);
            startActivity(mMoveToTaoHangHoa);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_thong_tin_tai_khoan) {
            Intent mMoveToTTTK = new Intent(getApplicationContext(), ThongTinTaiKhoanNSXActivity.class);
            startActivity(mMoveToTTTK);
        } else if (id == R.id.nav_thong_tin_mo_ta) {
            Intent mMoveToTTMT = new Intent(getApplicationContext(), ThongTinMoTaActivity.class);
            startActivity(mMoveToTTMT);
        } else if (id == R.id.nav_tao_hang_hoa) {
            Intent mMoveToTaoHangHoa = new Intent(getApplicationContext(), TaoGianHangActivity.class);
            startActivity(mMoveToTaoHangHoa);
        } else if (id == R.id.nav_lich_su) {
            Intent mMoveToLichSu = new Intent(getApplicationContext(), LichSuBanHangActivity.class);
            startActivity(mMoveToLichSu);
        } else if (id == R.id.nav_logout) {
            Intent mMoveToSignInActivity = new Intent(getApplicationContext(), SigninActivity.class);
            mMoveToSignInActivity.putExtra("action", "logout");
            startActivity(mMoveToSignInActivity);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // lấy số sản phẩm, số khách, số tiền
   /* class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                ArrayList<String> listKhach = new ArrayList<>();
                for (int i = 0; i < root.length(); i++) {
                    JSONObject data = root.getJSONObject(i);
                    if (data.getString("nguoiban").equals(Auth.khachHangId)) {
                        if (listKhach.size() == 0) {
                            listKhach.add(data.getString("nguoimua"));
                        } else {
                            int trung = 0;
                            for (int j = 0; j < listKhach.size(); j++) {
                                if (listKhach.get(j).equals(data.getString("nguoimua"))) {
                                    trung++;
                                    break;
                                }
                            }
                            if (trung == 0) {
                                listKhach.add(data.getString("nguoimua"));
                            }
                        }
                        soSanPham += Float.parseFloat(data.getString("soLuong"));
                        tongTien += Float.parseFloat(data.getString("thanhTien"));

                    }

                }
                soKhach = listKhach.size();
                txtTrangChuNSXDoanhSo.setText(DinhDangSo.fixNumnerToString(Math.round(tongTien)) + " đ");
                txtTrangChuNSXSoKhachGiaoDich.setText(soKhach + "");
                txtTrangChuNSXSoLuongSanPham.setText(soSanPham + "");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/

    // lấy số mục sản phẩm
    class Task2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            String num = "0";
            try {
                JSONArray root = new JSONArray(s);
                ArrayList<String> listKhach = new ArrayList<>();
                for (int i = 0; i < root.length(); i++) {
                    JSONObject data = root.getJSONObject(i);
                    if (data.getString("KhachHangId").equals(Auth.khachHangId)) {
                        num = data.getString("soloaisanpham");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            txtTrangChuNSXSoLuongDanhMuc.setText(num);
        }
    }

}
