package com.wimex.mbns.NhaSanXuat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.DanhMucSanPhamNSXAdapter;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.DanhMuc;
import com.wimex.mbns.NguoiMua.ThongTinTaiKhoanActivity;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;
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

public class DanhMucSanPhamNSXActivity extends AppCompatActivity {
    ListView lvDanhMucSanPhamNSX;
    ArrayList<DanhMuc> listDanhMuc;
    DanhMucSanPhamNSXAdapter danhMucSanPhamNSXAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc_san_pham_nsx);
        addControls();
        addEvents();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nsx, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_thong_tin_tai_khoan) {
            Intent mMoveToTTTK = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
            startActivity(mMoveToTTTK);
        } else if (id == R.id.nav_thong_tin_mo_ta) {
            Intent mMoveToTTMT = new Intent(getApplicationContext(), ThongTinMoTaActivity.class);
            startActivity(mMoveToTTMT);
        } else if (id == R.id.nav_tao_hang_hoa) {
            Intent mMoveToTaoHangHoa = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
            startActivity(mMoveToTaoHangHoa);
        } else if (id == R.id.nav_lich_su) {
            Intent mMoveToLichSu = new Intent(getApplicationContext(), LichSuBanHangActivity.class);
            startActivity(mMoveToLichSu);
        } else if (id == R.id.nav_logout) {
            Intent mMoveToSignInActivity = new Intent(getApplicationContext(), SigninActivity.class);
            mMoveToSignInActivity.putExtra("action", "logout");
            startActivity(mMoveToSignInActivity);
        } else if (id == R.id.nav_home) {
            Intent mMoveToTrangChuNSXActivity = new Intent(getApplicationContext(), TrangChuNSXActivity.class);
            mMoveToTrangChuNSXActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mMoveToTrangChuNSXActivity);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mMoveToTrangChuNSX = new Intent(DanhMucSanPhamNSXActivity.this, TrangChuNSXActivity.class);
        mMoveToTrangChuNSX.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mMoveToTrangChuNSX);
    }

    private void addEvents() {
        lvDanhMucSanPhamNSX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mMoveToSanPhamNSX = new Intent(DanhMucSanPhamNSXActivity.this, NhomSanPhamNSXActivity.class);
                mMoveToSanPhamNSX.putExtra("loaisanphamid", listDanhMuc.get(position).getId());
                startActivity(mMoveToSanPhamNSX);
            }
        });
    }

    private void addControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvDanhMucSanPhamNSX = (ListView) findViewById(R.id.lvDanhMucSanPhamNSX);
        listDanhMuc = new ArrayList<>();
        danhMucSanPhamNSXAdapter = new DanhMucSanPhamNSXAdapter(DanhMucSanPhamNSXActivity.this, R.layout.list_item_danh_muc_san_pham_nsx, listDanhMuc);
        lvDanhMucSanPhamNSX.setAdapter(danhMucSanPhamNSXAdapter);
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain + "/danhmucsanphamnsx1json.php");
            }
        });*/
        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", Auth.khachHangId);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(DanhMucSanPhamNSXActivity.this, postData, getResources().getString(R.string.loading), new AsyncResponse() {
            @Override
            public void processFinish(String s) {
               // Toast.makeText(DanhMucSanPhamNSXActivity.this,s,Toast.LENGTH_LONG).show();
                try {
                    JSONArray root = new JSONArray(s);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        DanhMuc mDanhMuc = new DanhMuc();
                        mDanhMuc.setAnh(Auth.domain + "/images/loaisanpham/" + data.getString("hinhLoaiSanPham"));
                        mDanhMuc.setId(data.getString("LoaiSanPhamId"));
                        mDanhMuc.setTen(data.getString("tenLoaiSanPham"));
                        mDanhMuc.setVidu(data.getString("chitiet"));
                        listDanhMuc.add(mDanhMuc);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                danhMucSanPhamNSXAdapter.notifyDataSetChanged();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Task2().execute(Auth.domain + "/danhmucsanphamnsx2json.php");
                    }
                });
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
               // Toast.makeText(DanhMucSanPhamNSXActivity.this, getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {

            }
        });
        postResponseAsyncTask.execute(Auth.domain + "/danhmucsanphamnsx1json.php");
    }

    //lấy các danh mục tương ứng
/*    class Task extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //  Toast.makeText(ChiTietSanPham.this,s,Toast.LENGTH_LONG).show();
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject data = root.getJSONObject(i);
                    if (data.getString("KhachHangId").equals(Auth.khachHangId)) {
                        DanhMuc mDanhMuc = new DanhMuc();
                        mDanhMuc.setAnh(Auth.domain + "/images/loaisanpham/" + data.getString("hinhLoaiSanPham"));
                        mDanhMuc.setId(data.getString("LoaiSanPhamId"));
                        mDanhMuc.setTen(data.getString("tenLoaiSanPham"));
                        mDanhMuc.setVidu(data.getString("chitiet"));
                        listDanhMuc.add(mDanhMuc);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            danhMucSanPhamNSXAdapter.notifyDataSetChanged();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Task2().execute(Auth.domain + "/danhmucsanphamnsx2json.php");
                }
            });
        }
    }*/

    //đếm số sản phẩm mỗi danh mục
    class Task2 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            //  Toast.makeText(ChiTietSanPham.this,s,Toast.LENGTH_LONG).show();
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject data = root.getJSONObject(i);
                    if (data.getString("KhachHangId").equals(Auth.khachHangId)) {
                        for (int j = 0; j < listDanhMuc.size(); j++) {
                            if (listDanhMuc.get(j).getId().equals(data.getString("LoaiSanPhamId"))) {
                                listDanhMuc.get(j).setSoluong(data.getString("count"));
                            }
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            danhMucSanPhamNSXAdapter.notifyDataSetChanged();
        }
    }
}
