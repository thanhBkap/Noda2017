package com.wimex.mbns.NguoiMua;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.wimex.mbns.Adapter.LichSuAdapter;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.GiaoDich;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.XuLyData.JsonToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LichSuMuaHangActivity extends Menu_CT {
    ListView lvLichSuBanHang;
    TextView txtLichSuBanHangTongTien;
    ArrayList<GiaoDich> listGiaoDich;
    LichSuAdapter lichSuAdapter;
    int tongTien = 0;
    String link = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lich_su_mua_hang);
        super.onCreate(savedInstanceState);
        adđControls();
        adđEvents();
    }

    private void adđControls() {
        lvLichSuBanHang = (ListView) findViewById(R.id.lvLichSuBanHang);
        txtLichSuBanHangTongTien = (TextView) findViewById(R.id.txtLichSuBanHangTongTien);
        listGiaoDich = new ArrayList<>();
        lichSuAdapter = new LichSuAdapter(LichSuMuaHangActivity.this, R.layout.list_item_lich_su_ban_hang2, listGiaoDich);
        lvLichSuBanHang.setAdapter(lichSuAdapter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain+"/lichsujson.php");
            }
        });
    }

    private void adđEvents() {

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
                    JSONObject giaodich = root.getJSONObject(i);
                    if (giaodich.getString("KhachHangId").equals(Auth.khachHangId)) {
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
            txtLichSuBanHangTongTien.setText(DinhDangSo.fixNumnerToString(tongTien) + " đ");
        }
    }
}
