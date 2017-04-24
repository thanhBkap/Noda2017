package com.wimex.mbns.NhaSanXuat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.NhomSanPhamNSXAdapter;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
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

public class SanPhamNSXActivity extends AppCompatActivity {
    ListView lvNhomSanPhamNSX;
    ArrayList<SanPham> listSanPham;
    NhomSanPhamNSXAdapter nhomSanPhamNSXAdapter;
    String loaisanphamid = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham_nsx);
        addControls();
        addEvents();
    }

    private void addControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvNhomSanPhamNSX = (ListView) findViewById(R.id.lvNhomSanPhamNSX);
        listSanPham = new ArrayList<>();
        nhomSanPhamNSXAdapter = new NhomSanPhamNSXAdapter(SanPhamNSXActivity.this, R.layout.list_item_nhom_san_pham_nsx, listSanPham);
        lvNhomSanPhamNSX.setAdapter(nhomSanPhamNSXAdapter);
        Intent i = getIntent();
        loaisanphamid = i.getStringExtra("loaisanphamid");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // new Task().execute("http://mbns.esy.es/nhomsanphamnsxjson.php");
            }
        });

        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", Auth.khachHangId);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
               //  Toast.makeText(SanPhamNSXActivity.this, s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                try {
                    JSONArray root = new JSONArray(s);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                            SanPham sp = new SanPham();
                            sp.setAnh(Auth.domain+"/images/" + data.getString("logoId"));
                            sp.setTen(data.getString("tenSanPham"));
                            sp.setDate(data.getString("date"));
                            sp.setPrice(Float.parseFloat(data.getString("giaSanPham")));
                            listSanPham.add(sp);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                nhomSanPhamNSXAdapter.notifyDataSetChanged();
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
        postResponseAsyncTask.execute(Auth.domain+"/nhomsanphamnsxjson.php");
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
        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_thong_tin_tai_khoan) {
            Intent mMoveToTTTK = new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
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
        }else if (id == R.id.nav_home) {
            Intent mMoveToTrangChuNSXActivity = new Intent(getApplicationContext(), TrangChuNSXActivity.class);
            mMoveToTrangChuNSXActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mMoveToTrangChuNSXActivity);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent mMoveToTrangChuNSX = new Intent(SanPhamNSXActivity.this, TrangChuNSXActivity.class);
        startActivity(mMoveToTrangChuNSX);
    }

    private void addEvents() {

    }

    class Task extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject data = root.getJSONObject(i);
                    if (data.getString("KhachHangId").equals(Auth.khachHangId)) {
                        SanPham sp = new SanPham();
                        sp.setAnh(Auth.domain+"/images/" + data.getString("logoId"));
                        sp.setTen(data.getString("tenSanPham"));
                        sp.setDate(data.getString("date"));
                        sp.setPrice(Float.parseFloat(data.getString("giaSanPham")));
                        listSanPham.add(sp);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            nhomSanPhamNSXAdapter.notifyDataSetChanged();

        }
    }
}
