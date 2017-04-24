package com.wimex.mbns.NhaSanXuat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.wimex.mbns.Adapter.LichSuAdapter;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.GiaoDich;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;
import com.wimex.mbns.NguoiMua.ThongTinTaiKhoanActivity;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.XuLyData.JsonToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LichSuBanHangActivity extends AppCompatActivity {
    ListView lvLichSuBanHang;
    TextView txtLichSuBanHangTongTien;
    ArrayList<GiaoDich> listGiaoDich;
    LichSuAdapter lichSuAdapter;
    int tongTien = 0;
    String link="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lich_su_ban_hang2);
        super.onCreate(savedInstanceState);
        adđControls();
        adđEvents();
    }

    private void adđControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lvLichSuBanHang = (ListView) findViewById(R.id.lvLichSuBanHang);
        txtLichSuBanHangTongTien = (TextView) findViewById(R.id.txtLichSuBanHangTongTien);
        listGiaoDich = new ArrayList<>();
        lichSuAdapter = new LichSuAdapter(LichSuBanHangActivity.this, R.layout.list_item_lich_su_ban_hang, listGiaoDich);
        lvLichSuBanHang.setAdapter(lichSuAdapter);
        if (Auth.loaiKhachHangId.equals("1")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Task().execute(Auth.domain+"/lichsunsxjson.php");
                }
            });
        }
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
    private void adđEvents() {

    }

    class Task extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                for (int i=0; i<root.length();i++){
                    JSONObject giaodich = root.getJSONObject(i);
                    if (giaodich.getString("KhachHangId").equals(Auth.khachHangId)){
                        GiaoDich giaoDichItem = new GiaoDich();
                        giaoDichItem.setDate(giaodich.getString("ngayThuHoach"));
                        giaoDichItem.setDonGia(giaodich.getString("giaSanPham"));
                        giaoDichItem.setSoLuong(giaodich.getString("soLuong"));
                        giaoDichItem.setTen(giaodich.getString("tenSanPham"));
                        listGiaoDich.add(giaoDichItem);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            lichSuAdapter.notifyDataSetChanged();
            for (GiaoDich giaoDich : listGiaoDich) {
                tongTien += DinhDangSo.fixStringToNumber(giaoDich.thanhTien());
            }
            txtLichSuBanHangTongTien.setText(DinhDangSo.fixNumnerToString(tongTien)+" đ");
        }
    }
}
