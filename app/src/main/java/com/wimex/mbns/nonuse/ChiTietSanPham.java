package com.wimex.mbns.nonuse;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.MenuToolbar;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.DiaChi;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.NguoiMua.GioHang;
import com.wimex.mbns.NguoiMua.SanPhamActivity;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.XuLyData.JsonToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MyPC on 2/7/2017.
 */

public class ChiTietSanPham extends MenuToolbar {
    Button mMua_ChiTietHang;
    Button mThemVaoGioHang;
    ImageView imgAnhChiTietSanPham;
    TextView txtTenSanPham, txtGiaBan, txtDonVi;
    int id;
    String id2;
    com.wimex.mbns.Model.SanPham mSanPham2 = new SanPham();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.chi_tiet_san_pham);
        super.onCreate(savedInstanceState);
        // nhận id từ trang chủ qua intent
        addControls();
        addEvents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ChiTietSanPham.this,SanPhamActivity.class);
        i.putExtra("id",id2);
        startActivity(i);
        //super.onBackPressed();
        MenuToolbar.txtCartMenu.setText(Auth.gioHang.size()+"");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void addEvents() {
        mMua_ChiTietHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMua = new Intent(ChiTietSanPham.this, GioHang.class);
                xuLyThemGioHang();
               // iMua.putExtra("SanPham",mSanPham2);
                startActivity(iMua);
            }
        });

        mThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemGioHang();
            }
        });
    }

    private void xuLyThemGioHang() {
        int trung = 0;
        for (SanPham sanpham: Auth.gioHang){
            if(mSanPham2.getId()==sanpham.getId()){
                trung=1;
                break;
            }
        }
        if (trung==0){
            Auth.gioHang.add(mSanPham2);
            MenuToolbar.txtCartMenu.setVisibility(View.VISIBLE);
            MenuToolbar.txtCartMenu.setText(Auth.gioHang.size()+"");
            Toast.makeText(ChiTietSanPham.this,"thêm thành công",Toast.LENGTH_SHORT).show();
            mThemVaoGioHang.setClickable(false);
        }

    }



    private void addControls() {
        //Toast.makeText(ChiTietSanPham.this,""+ Auth.khachHangId,Toast.LENGTH_SHORT).show();
        Intent i = getIntent();
        id = i.getIntExtra("id", 0);
        id2 = i.getStringExtra("id2");
        mMua_ChiTietHang = (Button) findViewById(R.id.btMuaNgay);
        mThemVaoGioHang = (Button) findViewById(R.id.btThemVaoGio_ChiTiet);
        imgAnhChiTietSanPham = (ImageView) findViewById(R.id.imgAnhChiTietSanPham);
        txtTenSanPham = (TextView) findViewById(R.id.txtTenChiTietSanPham);
        txtGiaBan = (TextView) findViewById(R.id.txtGiaBanChiTietSanPham);
        txtDonVi = (TextView) findViewById(R.id.txtDonViChiTietSanPham);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute("http://mbns.esy.es/chitietsanphamjson.php");
            }
        });

    }

    class Task extends AsyncTask<String, Void, String> {
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
                    JSONObject mSanPham = root.getJSONObject(i);
                    if (mSanPham.getInt("sanPhamId") == id) {
                        DiaChi diaChi = new DiaChi();
                        diaChi.setDetail(mSanPham.getString("detail"));
                        diaChi.setProvinceId(mSanPham.getString("provinceid"));
                        diaChi.setDistrictId(mSanPham.getString("districtid"));
                        diaChi.setWardId(mSanPham.getString("wardid"));
                        mSanPham2.setDiaChi(diaChi);
                        mSanPham2.setId(mSanPham.getInt("sanPhamId"));
                        mSanPham2.setAnh("http://mbns.esy.es/images/"
                                + mSanPham.getInt("hinhAnhId")
                                + ".jpg");
                        mSanPham2.setTen(mSanPham.getString("tenSanPham"));
                        mSanPham2.setPrice(Float.parseFloat(mSanPham.getString("giaSanPham")));
                        txtTenSanPham.setText(mSanPham2.getTen());
                        txtGiaBan.setText(DinhDangSo.fixNumnerToString((int) mSanPham2.getPrice()) + " vnd");
                        txtDonVi.setText(" / " + mSanPham.getString("tenDonVi"));
                        Picasso.with(ChiTietSanPham.this).load(
                                mSanPham2.getAnh()
                        ).into(imgAnhChiTietSanPham);
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
