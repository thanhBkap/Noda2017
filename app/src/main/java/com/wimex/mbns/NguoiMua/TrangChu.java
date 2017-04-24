package com.wimex.mbns.NguoiMua;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.LoaiSanPhamAdapter;
import com.wimex.mbns.MainActivity;
import com.wimex.mbns.MenuToolbar;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.LoaiSanPham;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SignUp1Activity;
import com.wimex.mbns.SignInSignUp.SigninActivity;
import com.wimex.mbns.TroGiup;
import com.wimex.mbns.XuLyData.JsonToString;
import com.wimex.mbns.XuLyData.MyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MyPC on 2/6/2017.
 */

public class TrangChu extends MenuToolbar implements NavigationView.OnNavigationItemSelectedListener {
    GridView gridViewHome;
    ArrayList<LoaiSanPham> arrSanPhamMoi;
    LoaiSanPhamAdapter spmoiAdapter;
    TextView txtHoTen_TrangChu, txtTrangChuVoteNotice;
    Button btDangKy_trangchu, btDangNhap_trangchu;
    String app_server_url = Auth.domain + "/fcm_insert.php";

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler = new Handler();

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Auth.signInStatus.equals("yes")) {
            setContentView(R.layout.activity_trangchu_dadangnhap);

        } else {
            setContentView(R.layout.activity_trangchu_nguoimua);
            ImageView imageView1 = (ImageView) findViewById(R.id.txtTenApp);
            imageView1.setImageResource(R.drawable.noda_tc);
        }
        super.onCreate(savedInstanceState);

/////////////////////////////////////////////////////////////////////////////////////////////
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ngm);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                TrangChu.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_ngm);
        navigationView.setNavigationItemSelectedListener(this);
///////////////////////////////////////////////////////////////////////////////////////////////

        addControls();
        addEvents();

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
    protected void onResume() {
       /* if (Auth.signInStatus.equals("yes")) {
            setContentView(R.layout.trangchu_dadangnhap);

        } else {
            setContentView(R.layout.trangchu2);
        }*/
        super.onResume();
        // addControls();
        //  addEvents();
    }

    private void addEvents() {
        gridViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //chuyển sang sản phẩm tương ứng và gửi id kèm theo
                Intent mMoveToSanPhamActivity = new Intent(TrangChu.this, SanPhamActivity.class);
                LoaiSanPham mLoaiSanPham = new LoaiSanPham();
                mLoaiSanPham = arrSanPhamMoi.get(position);
                mMoveToSanPhamActivity.putExtra("id", mLoaiSanPham.getId());
                startActivity(mMoveToSanPhamActivity);
            }
        });

    }

    private void addControls() {
        // Toast.makeText(getApplicationContext(),"onCre TrangChu",Toast.LENGTH_LONG).show();
        //Nếu chưa gửi token lên server
        if (Auth.checkToken == 0) {
            final SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
            final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "nothing");
            final String inserted = sharedPreferences.getString("inserted", "no");
            //kiểm tra đã gửi chưa bằng 1 sharepref
            if (inserted.equals("no")) {
                final MyCommand myCommand = new MyCommand(getApplicationContext());
                try {
                    StringRequest stringRequest;
                    stringRequest = new StringRequest(Request.Method.POST, app_server_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            Toast.makeText(TrangChu.this, "" + response, Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("inserted", "yes");
                            editor.commit();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(TrangChu.this, "some trouble occured " + error, Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("fcm_token", token);
                            map.put("id", Auth.khachHangId);
                            return map;
                        }
                    };
                    myCommand.add(stringRequest);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                myCommand.execute();
            }
            Auth.checkToken = 1;
        }
        gridViewHome = (GridView) findViewById(R.id.gridViewLoaiSanPham);
        arrSanPhamMoi = new ArrayList<LoaiSanPham>();
        Intent i = getIntent();
        if (i.hasExtra("dathang")) {
            if (i.getStringExtra("dathang").equals("thanhcong")) {
                Toast.makeText(TrangChu.this, "Quý khách đã đặt hàng thành công ", Toast.LENGTH_LONG).show();
            }
        }

        spmoiAdapter = new LoaiSanPhamAdapter(TrangChu.this, R.layout.grid_item2, arrSanPhamMoi);
        gridViewHome.setNumColumns(2);
        gridViewHome.setAdapter(spmoiAdapter);
        if (Auth.signInStatus.equals("yes")) {
            txtTrangChuVoteNotice = (TextView) findViewById(R.id.txtTrangChuVoteNotice);
            txtTrangChuVoteNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(TrangChu.this, VoteActivity.class);
                    startActivity(i);
                }
            });
            txtHoTen_TrangChu = (TextView) findViewById(R.id.txtHoTen_TrangChu);

            HashMap<String, String> postData = new HashMap<>();
            postData.put("id", Auth.khachHangId);
            PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    //  Toast.makeText(TrangChu.this, s, Toast.LENGTH_LONG).show();
                    //txtTaoGianHangDonGia.setText(s);
                    try {
                        JSONArray root = new JSONArray(s);
                        JSONObject data = root.getJSONObject(0);
                        txtHoTen_TrangChu.setText(data.getString("tenKhachHang"));
                        if (Integer.parseInt(data.getString("voteNonVoted")) > 0) {
                            txtTrangChuVoteNotice.setText("Bạn đang có " + data.getString("voteNonVoted") + " sản phẩm chưa đánh giá !!!\n Click đây để đánh giá ngay...");
                            txtTrangChuVoteNotice.setVisibility(View.VISIBLE);
                        } else {
                            txtTrangChuVoteNotice.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
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
            postResponseAsyncTask.setLoadingMessage(getResources().getString(R.string.loading));
            postResponseAsyncTask.execute(Auth.domain + "/trangchujson1.php");
        } else {
            //txtTrangChuVoteNotice.setVisibility(View.GONE);
//            btDangKy_trangchu = (Button) findViewById(R.id.btDangKy_trangchu);
            //           btDangKy_trangchu.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent i1 = new Intent(TrangChu.this, SignUp1Activity.class);
            //                  startActivity(i1);
            //            }
            //       });
            //      btDangNhap_trangchu = (Button) findViewById(R.id.btDangNhap_trangchu);
            //     btDangNhap_trangchu.setOnClickListener(new View.OnClickListener() {
            //       @Override
            //       public void onClick(View v) {
            //          Intent i = new Intent(TrangChu.this, SigninActivity.class);
            //         startActivity(i);
            //    }
            //   });
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain + "/loaisanpham.php");
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////

   /* @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ngm);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.thongtintaikhoan:
                Intent i1 = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
                startActivity(i1);
                //Intent i1 = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
                //startActivity(i1);
                break;
            /*case R.id.thongtinmota:
                Intent i2 = new Intent(getApplicationContext(),ThongTinMoTaActivity.class);
                startActivity(i2);
                break;*/
            /*case R.id.nsx:
                Intent i3 = new Intent(getApplicationContext(),NhaSanXuatActivity.class);
                startActivity(i3);
                break;*/
            case R.id.listnsx:
                Intent i4 = new Intent(getApplicationContext(), DSNSXActivity.class);
                startActivity(i4);
                break;

            case R.id.lichsu:
                Intent iLichSu = new Intent(getApplicationContext(), LichSuMuaHangActivity.class);
                startActivity(iLichSu);
                break;
            /*case R.id.caidat:
                break;*/
            case R.id.logout:
                Intent mMoveToSignInActivity = new Intent(getApplicationContext(), SigninActivity.class);
                mMoveToSignInActivity.putExtra("action", "logout");
                startActivity(mMoveToSignInActivity);
                finish();
                break;
            case R.id.mnudanhgia:
                Intent mMoveToVoteActivity = new Intent(getApplicationContext(), VoteActivity.class);
                startActivity(mMoveToVoteActivity);
                break;
            case R.id.itemmenudangnhap:
                Intent intent1 = new Intent(TrangChu.this, SigninActivity.class);
                startActivity(intent1);
                break;
            case R.id.itemdangky:
                Intent intent2 = new Intent(TrangChu.this, SignUp1Activity.class);
                startActivity(intent2);
                break;
         /*   case  R.id.chuyensang_nsx:
                if (Auth.loaiKhachHangId.equals("1")) {
                    Intent iChuyen = new Intent(TrangChu.this, TrangChuNSXActivity.class);
                    startActivity(iChuyen);
                } else {
                    final Dialog dialog = new Dialog(TrangChu.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("THÔNG BÁO");
                    dialog.setContentView(R.layout.dialog_thongbao);

                    dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
//                        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/dividerId", null, null);
//                        View divider = dialog.findViewById(dividerId);
//                        divider.setBackgroundColor(getResources().getColor(R.color.colorDialog));
                    int textViewId = dialog.getContext().getResources().getIdentifier("android:id/title", null, null);
                    TextView tv = (TextView) dialog.findViewById(textViewId);
                    tv.setTextColor(getResources().getColor(R.color.colorPrimary));

                    dialog.show();
                    Button btbothongbao = (Button) dialog.findViewById(R.id.btboquathongbao);
                    btbothongbao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
                break;*/
            case R.id.trogiup:
                Intent iTrG = new Intent(TrangChu.this, TroGiup.class);
                startActivity(iTrG);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_ngm);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_dk_dn, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        return super.onOptionsItemSelected(item);
//    }

/////////////////////////////////////////////////////////////////////////////////////////////////////

    class Task extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // lấy dữ liệu json trên mạng về
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(TrangChu.this,s,Toast.LENGTH_SHORT).show();
            //Đọc file json lấy toàn bộ thông tin các sản phẩm đang có
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject mLoaiSanPham = root.getJSONObject(i);
                    LoaiSanPham mLoaiSanPham2 = new LoaiSanPham();
                    mLoaiSanPham2.setId(mLoaiSanPham.getInt("id") + "");
                    mLoaiSanPham2.setHinhLoaiSanPham(Auth.domain + "/images/loaisanpham/"
                            + mLoaiSanPham.getString("hinhLoaiSanPham"));
                    mLoaiSanPham2.setTenLoaiSanPham(mLoaiSanPham.getString("tenLoaiSanPham"));
                    mLoaiSanPham2.setChiTiet(mLoaiSanPham.getString("chitiet"));
                    mLoaiSanPham2.setSoLuong(mLoaiSanPham.getString("soLuong"));
                    arrSanPhamMoi.add(mLoaiSanPham2);
                    spmoiAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
