package com.wimex.mbns.NguoiMua;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wimex.mbns.Adapter.DatHangAdapter;
import com.wimex.mbns.DiaChi.DiaChiActivity;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.DiaChi;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.Model.ThongTinKhachHang;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.XuLyData.JsonToString;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DatHangActivity extends Menu_CT {
    ListView lvDatHang;
    Button btnDatHang, btnChinhSuaDiaChiDatHang;
    TextView txtDiaChiDatHang, txtPhiVanChuyenDatHang, txtTenDatHang, txtEmailDatHang, txtSoDienThoaiDatHang, txtDatHangSoSanPham, txtTongTienDatHang;
    DatHangAdapter datHangAdapter;
    ArrayList<SanPham> listSanPham;
    ProgressDialog dialog;
//    ProgressBar progressBarDatHang;
    double tienShip = 0.0f;
    double mTongTienDatHang = 0.0f;
    DiaChi diaChi;
    String provinceId="1";
    String mIdGiaoDichMax;
    //số thứ tự đơn hàng
    int stt = 0;
    SanPham sanPhamTask5 = Auth.gioHang.get(0);
//    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_dat_hang2);
        super.onCreate(savedInstanceState);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDatHang();
            }
        });
        btnChinhSuaDiaChiDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChinhSua();
            }
        });
    }

    private void xuLyChinhSua() {
        Intent mMoveToDiaChiActivity = new Intent(DatHangActivity.this, DiaChiActivity.class);
        startActivity(mMoveToDiaChiActivity);
    }

    private void xuLyDatHang() {
//        progressBarDatHang.setVisibility(View.VISIBLE);
        btnDatHang.setEnabled(false);
        dialog.show();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task3().execute(Auth.domain+"/giaodichjson.php");
                new Task4().execute(Auth.domain+"/xulygiaodichjson.php");
            }
        });

    }

    private void addControls() {
//        progressBarDatHang = (ProgressBar) findViewById(R.id.progressBarDatHang);
//        progressBarDatHang.setVisibility(View.VISIBLE);
        //////ánh xạ
        lvDatHang = (ListView) findViewById(R.id.lvDatHang);
        btnDatHang = (Button) findViewById(R.id.btnDatHang);
        btnChinhSuaDiaChiDatHang = (Button) findViewById(R.id.btnChinhSuaDiaChiDatHang);
        txtDiaChiDatHang = (TextView) findViewById(R.id.txtDiaChiDatHang);
        txtPhiVanChuyenDatHang = (TextView) findViewById(R.id.txtPhiVanChuyenDatHang);
        txtTenDatHang = (TextView) findViewById(R.id.txtTenDatHang);
        txtSoDienThoaiDatHang = (TextView) findViewById(R.id.txtSoDienThoaiDatHang);
        txtEmailDatHang = (TextView) findViewById(R.id.txtEmailDatHang);
        txtDatHangSoSanPham = (TextView) findViewById(R.id.txtDatHangSoSanPham);
        txtTongTienDatHang = (TextView) findViewById(R.id.txtTongTienDatHang);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Đang tải...");
        //set value
        btnDatHang.setEnabled(true);
        txtDatHangSoSanPham.setText("Sản phẩm (" + Auth.gioHang.size() + ") ");
        listSanPham = new ArrayList<>();
        listSanPham = Auth.gioHang;
        datHangAdapter = new DatHangAdapter(DatHangActivity.this, R.layout.list_item_dat_hang, listSanPham);
        lvDatHang.setAdapter(datHangAdapter);
        /*for (SanPham sp : listSanPham) {
            Toast.makeText(DatHangActivity.this, sp.getTen() + "", Toast.LENGTH_SHORT).show();
        }*/
        Intent i = getIntent();
        //  txtTongTienDatHang.setText(Auth.giaTriGioHang+" vnd");
        txtDiaChiDatHang.setText(i.getStringExtra("diachi"));
        diaChi = (DiaChi) i.getSerializableExtra("diachi2");
        provinceId=i.getStringExtra("diachi3");
        txtDiaChiDatHang.setText(diaChi.getDiachi());
        // txtPhiVanChuyenDatHang.setText(diaChi);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain+"/thongtinkhachhangjson.php");
             //   new Task2().execute("http://mbns.esy.es/tienshipjson.php");
            }
        });
        float ship2 = 0.0f;
        txtPhiVanChuyenDatHang.setText(DinhDangSo.fixNumnerToString((int) (tienShip + 0.0)) + " đ");
        mTongTienDatHang = Math.round(Auth.giaTriGioHang + tienShip);
        txtTongTienDatHang.setText(DinhDangSo.fixNumnerToString((int) (mTongTienDatHang)) + " đ");
    }

    //chen giao dich
    private String makePostRequest(String url) {
        String kq = "";
        HttpClient httpClient = new DefaultHttpClient();
        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);
        // Các tham số truyền
        List nameValuePair = new ArrayList(5);
        nameValuePair.add(new BasicNameValuePair("xuLy", "insertGiaoDich"));
        nameValuePair.add(new BasicNameValuePair("KhachHangId", Auth.khachHangId));
        nameValuePair.add(new BasicNameValuePair("thanhtien", mTongTienDatHang + ""));
        nameValuePair.add(new BasicNameValuePair("status", "0"));
        //  nameValuePair.add(new BasicNameValuePair("giaoDichId", (Integer.parseInt(mIdGiaoDichMax)+1)+""));
        nameValuePair.add(new BasicNameValuePair("diaChiId", diaChi.getId()));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            kq = "error";
        } catch (IOException e) {
            e.printStackTrace();
            kq = "error";
        }
        return kq;
    }

    //chen don hang
    private String makePostRequest2(String url, SanPham sanPham) {
        String kq = "";
        HttpClient httpClient = new DefaultHttpClient();
        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);
        // Các tham số truyền
        List nameValuePair = new ArrayList(5);
        nameValuePair.add(new BasicNameValuePair("xuLy", "insertDonHang"));
        nameValuePair.add(new BasicNameValuePair("soluong", sanPham.getDonViTieuChuan() + ""));
        nameValuePair.add(new BasicNameValuePair("thanhtien", Math.round(Float.parseFloat(sanPham.getDonViTieuChuan()) * sanPham.getPrice()) + ""));
        nameValuePair.add(new BasicNameValuePair("sanPhamId", sanPham.getId() + ""));
        nameValuePair.add(new BasicNameValuePair("giaoDichId", (Integer.parseInt(mIdGiaoDichMax) + 1) + ""));
        //  nameValuePair.add(new BasicNameValuePair("diaChiId", diaChi.getId()));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            kq = "error";
        } catch (IOException e) {
            e.printStackTrace();
            kq = "error";
        }
        return kq;
    }

    // lay thong tin khach hang
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
                    JSONObject mKhachHang = root.getJSONObject(i);
                    if (mKhachHang.getString("khachHangId").equals(Auth.khachHangId)) {
                        ThongTinKhachHang mThongTinKhachHang = new ThongTinKhachHang();
                        mThongTinKhachHang.setTenKhachHang(mKhachHang.getString("tenKhachHang"));
                        mThongTinKhachHang.setEmail(mKhachHang.getString("email"));
                        mThongTinKhachHang.setSoDienThoai(mKhachHang.getString("soDienThoai"));
                        Auth.thongTinKhachHang = mThongTinKhachHang;
                        txtTenDatHang.setText("Tên : " + mThongTinKhachHang.getTenKhachHang());
                        txtSoDienThoaiDatHang.setText("Số điện thoại : " + mThongTinKhachHang.getSoDienThoai());
                        txtEmailDatHang.setText("Email : " + mThongTinKhachHang.getEmail());
                        break;
                    }
                }
//                progressBarDatHang.setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    // xac dinh tien ship, tien dat hang
/*    class Task2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                float ship2 = 0.0f;
                for (SanPham sanPham : listSanPham) {
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject mTienShip = root.getJSONObject(i);
                      //  if (Integer.parseInt(diaChi.getProvinceId()) == Integer.parseInt(mTienShip.getString("provinceIdDiemDen"))) {
                            if (Integer.parseInt(provinceId) == Integer.parseInt(mTienShip.getString("provinceIdDiemDen"))) {
                            // Toast.makeText(DatHangActivity.this,"diem den trung nhau",Toast.LENGTH_SHORT).show();
                                //if (Integer.parseInt(sanPham.getDiaChi().getProvinceId()) == Integer.parseInt(mTienShip.getString("provinceIdDiemBatDau"))) {
                                if (Integer.parseInt(sanPham.getProvinceid()) == Integer.parseInt(mTienShip.getString("provinceIdDiemBatDau"))) {
                                tienShip = tienShip + Float.parseFloat(mTienShip.getString("ship1")) * sanPham.getSoLuong();
                                ship2 = Float.parseFloat(mTienShip.getString("ship2"));
                                //  Toast.makeText(DatHangActivity.this,"ship2 :"+ship2+"tien ship : "+tienShip,Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                }
                tienShip += ship2;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }*/

    //lay id va chen don hang
    class Task3 extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                JSONObject giaodich = root.getJSONObject(root.length() - 1);
                mIdGiaoDichMax = giaodich.getString("id");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Task5().execute(Auth.domain+"/xulygiaodichjson.php");
                        /*for (SanPham sanPham:listSanPham){
                            sanPhamTask5=sanPham;
                            //Toast.makeText(DatHangActivity.this,sanPham.getTen()+"size list: "+listSanPham.size(),Toast.LENGTH_SHORT).show();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new Task5().execute("http://mbns.esy.es/xulygiaodichjson.php");

                                }
                            });
                        }*/
                    }
                });

                // String status = makePostRequest("http://mbns.esy.es/xulygiaodichjson.php");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //chen du lieu len bang giao dich
    class Task4 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equals("error")) {
                Toast.makeText(DatHangActivity.this, "Mạng gặp sự cố", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //chen du lieu len bang don hang
    class Task5 extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            makePostRequest2(params[0], sanPhamTask5);

            return "";
        }

        @Override
        protected void onPostExecute(String s) {
         //   Toast.makeText(DatHangActivity.this, sanPhamTask5.getId() + "", Toast.LENGTH_LONG).show();
            if ((stt + 1) == Auth.gioHang.size()) {
                /*for (int i = 0; i < Auth.gioHang.size(); i++) {
                    Auth.gioHang.remove(i);
                }*/
                Auth.gioHang.clear();
                //datHangAdapter.notifyDataSetChanged();
                Intent mMoveToTrangChu = new Intent(DatHangActivity.this, TrangChu.class);
                mMoveToTrangChu.putExtra("dathang", "thanhcong");
                Auth.giaTriGioHang = 0.0f;
//                progressBarDatHang.setVisibility(View.GONE);
                dialog.dismiss();
                startActivity(mMoveToTrangChu);
                finish();
            } else {
                stt++;
                sanPhamTask5 = Auth.gioHang.get(stt);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Task5().execute(Auth.domain+"/xulygiaodichjson.php");
                    }
                });
            }
        }
    }
}

