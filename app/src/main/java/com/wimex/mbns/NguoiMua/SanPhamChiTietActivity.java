package com.wimex.mbns.NguoiMua;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;
import com.wimex.mbns.Adapter.SanPhamChiTietAdapter;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;
import com.wimex.mbns.XuLyData.DifferenceTime;
import com.wimex.mbns.XuLyData.DinhDangNgay;
import com.wimex.mbns.XuLyData.DinhDangSo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import static android.R.id.tabhost;

public class SanPhamChiTietActivity extends Menu_CT {
    public static String sanPhamChiTietActivityNowId = "1";
    public static ImageView imgSanPhamChiTiet;
    TextView txtSanPhamChiTietDonGia;
    TextView txtSanPhamChiTietTimeThuHoach;
    TextView txtSanPhamChiTietTimeGiaoHang;
    TextView txtSanPhamChiTietSoLuong, txtChiTietSanPhamDetail, txtSanPhamChiTietNSX;
    Button btMuaNgay, btThemVaoGio_ChiTiet;
    RecyclerView recyclerViewAnh;
    ArrayList<SanPham> listSanPham;
    ArrayList<String> listAnhSP;
    SanPhamChiTietAdapter sanPhamChiTietAdapter;
    TabHost tabHostChiTietSanPham;
    int id;
    String id2, nsxId, nsxName, notice;
    LinearLayout layoutTab1ChiTietSanPhamNSX;
    com.wimex.mbns.Model.SanPham mSanPham2 = new SanPham();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_san_pham_chi_tiet);
        super.onCreate(savedInstanceState);
        adđControls();
        addEvents();
    }

   /* @Override
    public void onBackPressed() {
        Intent i = new Intent(SanPhamChiTietActivity.this, SanPhamActivity.class);
        i.putExtra("id", id2);
        startActivity(i);
        //super.onBackPressed();
        Menu_CT.txtCartMenu.setText(Auth.gioHang.size() + "");
    }*/

    private void adđControls() {
        Intent i = getIntent();
        //id sản phẩm chi tiết
        if (i.hasExtra("id")) {
            id = i.getIntExtra("id", 0);
            SanPhamChiTietActivity.sanPhamChiTietActivityNowId = id + "";
        }


        //id danh mục để quay lại
        id2 = i.getStringExtra("id2");
        tabHostChiTietSanPham = (TabHost) findViewById(tabhost);
        tabHostChiTietSanPham.setup();
        //tabHostChiTietSanPham.getTabWidget().setDividerDrawable(R.drawable.tab_widget_divider);
        TabHost.TabSpec tabSpec = tabHostChiTietSanPham.newTabSpec("tab1");
        tabSpec.setContent(R.id.tab_chi_tiet_san_pham_1);
        //   tabSpec.setContent(R.id.tab_chi_tiet_san_pham_1);
        tabSpec.setIndicator("Thông tin chung");
        tabHostChiTietSanPham.addTab(tabSpec);
        TabHost.TabSpec tabSpec2 = tabHostChiTietSanPham.newTabSpec("tab2");
        tabSpec2.setContent(R.id.tab_chi_tiet_san_pham_2);
        tabSpec2.setIndicator("Chi tiết sản phẩm");
        tabHostChiTietSanPham.setCurrentTab(0);
        tabHostChiTietSanPham.addTab(tabSpec2);
        for (int i2 = 0; i2 < tabHostChiTietSanPham.getTabWidget().getChildCount(); i2++) {
            tabHostChiTietSanPham.getTabWidget().getChildAt(i2).setBackgroundColor(Color.parseColor("#ffffff"));
            TextView tv = (TextView) tabHostChiTietSanPham.getTabWidget().getChildAt(i2).findViewById(android.R.id.title);
            if (i2 == 0) {
                tv.setTextColor(Color.parseColor("#00e677"));
            } else {
                tv.setTextColor(Color.parseColor("#000000"));
            }

        }

        imgSanPhamChiTiet = (ImageView) findViewById(R.id.imgSanPhamChiTiet);
        recyclerViewAnh = (RecyclerView) findViewById(R.id.recyclerViewAnh);
        txtSanPhamChiTietDonGia = (TextView) findViewById(R.id.txtSanPhamChiTietDonGia);
        txtSanPhamChiTietTimeThuHoach = (TextView) findViewById(R.id.txtSanPhamChiTietTimeThuHoach);
        txtSanPhamChiTietTimeGiaoHang = (TextView) findViewById(R.id.txtSanPhamChiTietTimeGiaoHang);
        txtSanPhamChiTietSoLuong = (TextView) findViewById(R.id.txtSanPhamChiTietSoLuong);
        txtChiTietSanPhamDetail = (TextView) findViewById(R.id.txtChiTietSanPhamDetail);
        txtSanPhamChiTietNSX = (TextView) findViewById(R.id.txtSanPhamChiTietNSX);
        btThemVaoGio_ChiTiet = (Button) findViewById(R.id.btThemVaoGio_ChiTiet);
        btMuaNgay = (Button) findViewById(R.id.btMuaNgay);
        layoutTab1ChiTietSanPhamNSX = (LinearLayout) findViewById(R.id.layoutTab1ChiTietSanPhamNSX);
        listSanPham = new ArrayList<>();
        listAnhSP = new ArrayList<>();
       /* listAnhSP.add("http://mbns.esy.es/images/1.jpg");
        listAnhSP.add("http://mbns.esy.es/images/2.jpg");
        listAnhSP.add("http://mbns.esy.es/images/3.jpg");
        listAnhSP.add("http://mbns.esy.es/images/4.jpg");
        listAnhSP.add("http://mbns.esy.es/images/5.jpg");*/
        recyclerViewAnh.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewAnh.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewAnh.getContext(), DividerItemDecoration.HORIZONTAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));

        recyclerViewAnh.setItemAnimator(new DefaultItemAnimator());
        sanPhamChiTietAdapter = new SanPhamChiTietAdapter(listAnhSP, this);
        recyclerViewAnh.setAdapter(sanPhamChiTietAdapter);
        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", SanPhamChiTietActivity.sanPhamChiTietActivityNowId + "");
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, true, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //     Toast.makeText(SanPhamChiTietActivity.this, "id= "+id+s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                try {
                    JSONArray root = new JSONArray(s);
                    JSONObject data = root.getJSONObject(0);
                    SanPham sp = new SanPham();
                    //    sp.setAnh("http://mbns.esy.es/images/" + data.getString("logoId"));
                    //thêm vào giỏ hàng nếu cần thiết
                    mSanPham2.setTen(data.getString("tenSanPham"));
                    mSanPham2.setDate(data.getString("ngayThuHoach"));
                    mSanPham2.setPrice(Float.parseFloat(data.getString("giaSanPham")));
                    mSanPham2.setDateGiaoHang(data.getString("ngayGiaoHang"));
                    mSanPham2.setId(data.getInt("SanPhamId"));
                    mSanPham2.setSoLuongConLai(Float.parseFloat(data.getString("sanLuong")));
                    mSanPham2.setProvinceid(data.getString("provinceid"));
                    mSanPham2.setDonViTieuChuan(data.getString("donViTieuChuan"));
                    mSanPham2.setSoLuong(Float.parseFloat(data.getString("donViTieuChuan")));
                    nsxId = data.getString("KhachHangId");
                    nsxName = data.getString("tenKhachHang");
                    mSanPham2.setSoLuong(Float.parseFloat(data.getString("sanLuong")));
                    sp.setTen(data.getString("tenSanPham"));
                    sp.setDate(data.getString("ngayThuHoach"));
                    sp.setPrice(Float.parseFloat(data.getString("giaSanPham")));
                    sp.setDateGiaoHang(data.getString("ngayGiaoHang"));
                    sp.setSoLuong(Float.parseFloat(data.getString("sanLuong")));
                    //Đặt tên cho label activity
                    setTitle(data.getString("tenSanPham"));
                    //đổ dữ liệu ra màn hình
                    txtSanPhamChiTietTimeThuHoach.setText(DinhDangNgay.convertDate(data.getString("ngayThuHoach")));
                    txtSanPhamChiTietDonGia.setText(DinhDangSo.fixNumnerToString(Integer.parseInt(data.getString("giaSanPham"))) + " đ/kg");
                    txtSanPhamChiTietTimeGiaoHang.setText(DinhDangNgay.convertDate(data.getString("ngayGiaoHang")));
                    txtSanPhamChiTietSoLuong.setText(data.getString("sanLuong") + " kg");
                    txtSanPhamChiTietNSX.setText(nsxName);
                    txtChiTietSanPhamDetail.setText(data.getString("thongTinMoTa"));
                    //lấy list ảnh từ jsson
                    String s2 = data.getString("list");
                    JSONArray listAnh = new JSONArray(s2);
                    for (int j = 0; j < listAnh.length(); j++) {
                        JSONObject o = listAnh.getJSONObject(j);
                        listAnhSP.add(Auth.domain + "/images/" + o.getString("name"));
                    }
                    //  listSanPham.add(sp);

                    ///////// check time conf hay heet
                    String date = sp.getDate();
                    String a[] = date.split("-");
                    Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
                    TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                    Calendar calTZ = new GregorianCalendar(tz);
                    calTZ.setTimeInMillis(new Date().getTime());
// tạo một biến calendar khác để set time
                    Calendar ca = Calendar.getInstance();
                    ca.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
                    ca.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
                    ca.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
                    ca.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
                    ca.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
                    ca.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
                    ca.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
// in ra thoi gian cua timezone vietnam
                    int year = ca.get(Calendar.YEAR);
                    int month = ca.get(Calendar.MONTH) + 1;
                    int day = ca.get(Calendar.DAY_OF_MONTH);
                    int hour = ca.get(Calendar.HOUR_OF_DAY);
                    int minute = ca.get(Calendar.MINUTE);
                    int second = ca.get(Calendar.SECOND);
                    Date now = new Date(year, month, day, hour, minute, second);
                    if (DifferenceTime.getDifferenceMilliSeconds(now, two) <= 0) {
                       /* btMuaNgay.setEnabled(false);
                        btThemVaoGio_ChiTiet.setEnabled(false);*/
                        notice = getResources().getString(R.string.time_up);
                    } else {
                        notice = "";
                       /* btMuaNgay.setEnabled(true);
                        btThemVaoGio_ChiTiet.setEnabled(true);*/
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (listAnhSP.size() > 0) {
                    Picasso.with(SanPhamChiTietActivity.this).load(listAnhSP.get(0)).into(SanPhamChiTietActivity.imgSanPhamChiTiet);
                    mSanPham2.setAnh(listAnhSP.get(0));
                }
                // Toast.makeText(SanPhamChiTietActivity.this, listAnhSP.size()+" anh", Toast.LENGTH_LONG).show();

                sanPhamChiTietAdapter.notifyDataSetChanged();
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
        postResponseAsyncTask.execute(Auth.domain + "/sanphamchitietjson.php");
    }

    private void addEvents() {
        btMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Auth.signInStatus.equals("no")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.login_request), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SanPhamChiTietActivity.this, SigninActivity.class);
                    startActivity(intent);
                } else {
                    Intent iMua = new Intent(SanPhamChiTietActivity.this, GioHang.class);
                    xuLyThemGioHang();
                    // iMua.putExtra("SanPham",mSanPham2);
                    if (mSanPham2.getSoLuongConLai() > 0 && notice.equals("")) {
                        startActivity(iMua);
                    }
                }

            }
        });

        btThemVaoGio_ChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemGioHang();

            }
        });
        layoutTab1ChiTietSanPhamNSX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMoveToNSX = new Intent(getApplicationContext(), NhaSanXuatActivity.class);
                mMoveToNSX.putExtra("NSXId", nsxId);
                startActivity(mMoveToNSX);
            }
        });

        tabHostChiTietSanPham.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //  Toast.makeText(getApplicationContext(),tabId,Toast.LENGTH_LONG).show();
                //tabHostChiTietSanPham.getCurrentTabView().setBackgroundColor(Color.BLUE);
                // Toast.makeText(getApplicationContext(),tabHostChiTietSanPham.getCurrentTabTag(),Toast.LENGTH_LONG).show();
                for (int i = 0; i < tabHostChiTietSanPham.getTabWidget().getTabCount(); i++) {
                    TextView tv = (TextView) tabHostChiTietSanPham.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#000000"));
                }
                if (tabHostChiTietSanPham.getCurrentTabTag().equals(tabId)) {
                    TextView tv = (TextView) tabHostChiTietSanPham.getCurrentTabView().findViewById(android.R.id.title);
                    // TextView tv = (TextView) tabHostChiTietSanPham.getTabWidget().getChildAt(i2).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#00e677"));
                }
            }
        });
    }

    private void xuLyThemGioHang() {
        int trung = 0;
        for (SanPham sanpham : Auth.gioHang) {
            if (mSanPham2.getId() == sanpham.getId()) {
                trung = 1;
                break;
            }
        }
        //check amout > 0 and not duplicate in their'cart and the time for sale either
        if (trung == 0) {
            if (mSanPham2.getSoLuongConLai() > 0) {
                if (notice.equals(getResources().getString(R.string.time_up))) {
                    Toast.makeText(SanPhamChiTietActivity.this, notice, Toast.LENGTH_LONG).show();
                } else {
                    Auth.gioHang.add(mSanPham2);
                    Menu_CT.txtCartMenu.setVisibility(View.VISIBLE);
                    Menu_CT.txtCartMenu.setText(Auth.gioHang.size() + "");
                    Auth.updateGiaTriGioHang();
                    Toast.makeText(SanPhamChiTietActivity.this, getResources().getString(R.string.add_cart_successfully), Toast.LENGTH_LONG).show();
                }
                //btThemVaoGio_ChiTiet.setClickable(false);
            } else {
                Toast.makeText(SanPhamChiTietActivity.this, getResources().getString(R.string.sold_out), Toast.LENGTH_LONG).show();
                // onBackPressed();
            }

        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.duplicate_in_cart), Toast.LENGTH_LONG).show();
        }

    }

}
