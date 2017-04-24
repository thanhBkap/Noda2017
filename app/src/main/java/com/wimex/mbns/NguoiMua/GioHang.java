package com.wimex.mbns.NguoiMua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wimex.mbns.Adapter.GioHangAdapter;
import com.wimex.mbns.DiaChi.DiaChiActivity;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;
import com.wimex.mbns.XuLyData.DinhDangSo;

import java.util.ArrayList;

/**
 * Created by MyPC on 1/20/2017.
 */

public class GioHang extends Menu_CT {
    public static String THONG_BAO_1 = "Bạn chưa đặt hàng sản phẩm nào";
    public static String THONG_BAO_2 = "Bạn chưa đăng nhập\nXin mời bạn đăng nhập để mua sản phẩm";
    Toolbar toolbar;
    SanPham mSanPham;
    ListView listGioHang;
    ArrayList<SanPham> arrayListSanPham;
    GioHangAdapter gioHangAdapter;
    public static Button mBtMuaGioHang;
    public static TextView mTxtTongTienGioHang;
    TextView mTxtGioHangThongBao;
    // trạng thái có thể mua
    int status = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.layout_giohang2);
        super.onCreate(savedInstanceState);

        /*toolbar = (Toolbar) findViewById(R.id.toolbar_giohang);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/
        //Toast.makeText(GioHang.this,""+ Auth.khachHangId,Toast.LENGTH_SHORT).show();
        addControls();
        addEvents();
    }

    private void addEvents() {
        //      if (Auth.signInStatus.equals("yes")) {
        mBtMuaGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Auth.signInStatus.equals("yes")) {
                    if (Auth.gioHang.size() == 0) {
                        Toast.makeText(getBaseContext(), THONG_BAO_1, Toast.LENGTH_LONG).show();
                    } else {
                        Intent mMoveToDiaChi = new Intent(GioHang.this, DiaChiActivity.class);
                        startActivity(mMoveToDiaChi);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), THONG_BAO_2, Toast.LENGTH_SHORT).show();
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                Intent iVeDN = new Intent(GioHang.this, SigninActivity.class);
                                startActivity(iVeDN);
                            }

                        }
                    };
                    thread.start();

                }

            }
        });
    }

    private void addControls() {
        /*Intent i = getIntent();
        mSanPham= (SanPham) i.getSerializableExtra("SanPham");*/
        mTxtGioHangThongBao = (TextView) findViewById(R.id.txtGioHangThongBao);
        mBtMuaGioHang = (Button) findViewById(R.id.btMua_GioHang);
        if (Auth.gioHang.size() == 0) {
            mTxtGioHangThongBao.setVisibility(View.VISIBLE);
         //   Toast.makeText(getBaseContext(), THONG_BAO_1, Toast.LENGTH_LONG).show();
        } else {
            mBtMuaGioHang.setEnabled(true);
            mTxtGioHangThongBao.setVisibility(View.INVISIBLE);
            mTxtTongTienGioHang = (TextView) findViewById(R.id.item_TongTien);
            listGioHang = (ListView) findViewById(R.id.listGioHang);
            arrayListSanPham = new ArrayList<SanPham>();
            for (SanPham sanPham : Auth.gioHang) {
                arrayListSanPham.add(sanPham);
            }
            gioHangAdapter = new GioHangAdapter(GioHang.this, R.layout.mathang_giohang2, arrayListSanPham);
            listGioHang.setAdapter(gioHangAdapter);
            //Toast.makeText(GioHang.this,"tên sản phẩm :"+mSanPham.getTen(),Toast.LENGTH_SHORT).show();
            /*Auth.giaTriGioHang = 0.0f;
            for (SanPham sanPham : Auth.gioHang) {
                Auth.giaTriGioHang += (int) (sanPham.getPrice() * sanPham.getDonViTieuChuan());
            }*/
            Auth.updateGiaTriGioHang();
            mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
        }

    }
}
