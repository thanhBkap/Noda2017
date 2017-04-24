package com.wimex.mbns.NhaSanXuat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.TaoGianHangAdapter;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.LoaiSanPham;
import com.wimex.mbns.NguoiMua.ThongTinTaiKhoanActivity;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;
import com.wimex.mbns.XuLyData.MyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaoGianHangActivity extends AppCompatActivity {
    Spinner spinnerTaoGianHangDanhMuc,spinnerTaoGianHangSanPham;
    EditText  txtTaoGianHangDonGia, txtTaoGianHangSoLuongBan;
    GridView gridViewTaoGianHang;
    RelativeLayout layoutTaoGianHangNgayThuHoach;
    Button btnTaoGianHangDangBan;
    TextView txtTaoGianHangNgayThuHoach;
    ArrayList<String> listDanhMucString, listAnhString,listSanPhamChiTietString,listSanPhamChiTietTamThoiString;
    ArrayList<LoaiSanPham> listDanhMuc,listSanPhamChiTiet,listSanPhamChiTietTamThoi;
    ArrayList<Drawable> listAnh;
    ArrayAdapter<String> mDanhMucAdapter,mDanhMucSanPhamAdapter;
    TaoGianHangAdapter mTaoGianHangAdapter;
    int year, month, dayOfMonth;
    SimpleDateFormat sdf;
    String date, idDanhMuc,idDanhMucSanPham,tenDanhMucSanPham;
    GalleryPhoto galleryPhoto;
    int GALLERY_REQUEST = 98;
    int loi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_gian_hang);
        addControls();
        addEvents();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == 99) {
            return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Date pickedDate = new Date(year - 1900, month, dayOfMonth);
                    date = sdf.format(pickedDate);
                    txtTaoGianHangNgayThuHoach.setText(date);
                }
            }, year, month, dayOfMonth);
        } else {
            return null;
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

    private void addControls() {
        //get and format date
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        //set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //convert ui to java
        spinnerTaoGianHangDanhMuc = (Spinner) findViewById(R.id.spinnerTaoGianHangDanhMuc);
        spinnerTaoGianHangSanPham = (Spinner) findViewById(R.id.spinnerTaoGianHangSanPham);
        txtTaoGianHangDonGia = (EditText) findViewById(R.id.txtTaoGianHangDonGia);

        txtTaoGianHangDonGia.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        txtTaoGianHangSoLuongBan = (EditText) findViewById(R.id.txtTaoGianHangSoLuongBan);

        txtTaoGianHangSoLuongBan.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        gridViewTaoGianHang = (GridView) findViewById(R.id.gridViewTaoGianHang);
        layoutTaoGianHangNgayThuHoach = (RelativeLayout) findViewById(R.id.layoutTaoGianHangNgayThuHoach);
        btnTaoGianHangDangBan = (Button) findViewById(R.id.btnTaoGianHangDangBan);
        txtTaoGianHangNgayThuHoach = (TextView) findViewById(R.id.txtTaoGianHangNgayThuHoach);
        //init and setup spinner
        listDanhMuc = new ArrayList<>();
        listAnhString = new ArrayList<>();
        listDanhMucString = new ArrayList<>();
        listSanPhamChiTietString = new ArrayList<>();
        listSanPhamChiTiet = new ArrayList<>();
        listSanPhamChiTietTamThoi = new ArrayList<>();
        listSanPhamChiTietTamThoiString=new ArrayList<>();
        mDanhMucAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listDanhMucString);
        mDanhMucSanPhamAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listSanPhamChiTietTamThoiString);

        spinnerTaoGianHangDanhMuc.setAdapter(mDanhMucAdapter);
        spinnerTaoGianHangSanPham.setAdapter(mDanhMucSanPhamAdapter);

        //init and setup grid ảnh
        listAnh = new ArrayList<>();
        listAnh.add(getResources().getDrawable(R.drawable.them_anh));
        mTaoGianHangAdapter = new TaoGianHangAdapter(this, R.layout.grid_item_tao_gian_hang, listAnh);
        gridViewTaoGianHang.setAdapter(mTaoGianHangAdapter);
        //post data and get needed data
        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", Auth.khachHangId);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //  Toast.makeText(TaoGianHangActivity.this, s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                try {
                    JSONArray root = new JSONArray(s);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        LoaiSanPham loaiSanPham = new LoaiSanPham();
                        LoaiSanPham loaiSanPham2 = new LoaiSanPham();
                        if (listDanhMuc.size()>0){
                            int trung =0;
                            for (int j=0;j<listDanhMuc.size();j++){
                                if (listDanhMuc.get(j).getId().equals(data.getString("LoaiSanPhamId"))){
                                    trung=1;
                                    break;
                                }
                            }
                            if (trung==0){
                                listDanhMucString.add(data.getString("tenLoaiSanPham"));
                                loaiSanPham.setId(data.getString("LoaiSanPhamId"));
                                loaiSanPham.setTenLoaiSanPham(data.getString("tenLoaiSanPham"));
                                listDanhMuc.add(loaiSanPham);
                            }
                        }else{
                            listDanhMucString.add(data.getString("tenLoaiSanPham"));
                            loaiSanPham.setId(data.getString("LoaiSanPhamId"));
                            loaiSanPham.setTenLoaiSanPham(data.getString("tenLoaiSanPham"));
                            listDanhMuc.add(loaiSanPham);
                        }
                        listSanPhamChiTietString.add(data.getString("name"));

                        loaiSanPham2.setId(data.getString("id"));
                        loaiSanPham2.setTenLoaiSanPham(data.getString("name"));
                        loaiSanPham2.setLoaiSanPhamGocId(data.getString("LoaiSanPhamId"));

                        if (data.getString("LoaiSanPhamId").equals(listDanhMuc.get(0).getLoaiSanPhamGocId())){
                            listSanPhamChiTietTamThoiString.add(data.getString("name"));
                            listSanPhamChiTietTamThoi.add(loaiSanPham2);
                        }
                        listSanPhamChiTiet.add(loaiSanPham2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (listDanhMuc.size() == 0) {
                    listDanhMucString.add("chưa có danh mục nào");
                }
                if (listSanPhamChiTietTamThoi.size() == 0) {
                    listSanPhamChiTietTamThoiString.add("chưa có danh mục nào");
                }
                mDanhMucAdapter.notifyDataSetChanged();
                mDanhMucSanPhamAdapter.notifyDataSetChanged();
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
        postResponseAsyncTask.execute(Auth.domain+"/taogianhangjson.php");
    }

    private void addEvents() {
        layoutTaoGianHangNgayThuHoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(99);
            }
        });
        btnTaoGianHangDangBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getApplicationContext(),"selected id"+listDanhMucString.get(spinnerTaoGianHangDanhMuc.getSelectedItemPosition())+"sp selected"+
                        listSanPhamChiTietTamThoiString.get(spinnerTaoGianHangSanPham.getSelectedItemPosition()),Toast.LENGTH_LONG).show();*/
                xuLyPost();

            }
        });
        spinnerTaoGianHangDanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listDanhMuc.size() > 0) {
                    idDanhMuc = listDanhMuc.get(position).getId();
                    listSanPhamChiTietTamThoiString.clear();
                    listSanPhamChiTietTamThoi.clear();
                    for (int i=0;i<listSanPhamChiTiet.size();i++){
                      //  Toast.makeText(TaoGianHangActivity.this, idDanhMuc+"--id danh mục"+listSanPhamChiTiet.get(i).getLoaiSanPhamGocId()+"goc id", Toast.LENGTH_LONG).show();
                        if (listSanPhamChiTiet.get(i).getLoaiSanPhamGocId().equals(idDanhMuc)){
                            listSanPhamChiTietTamThoi.add(listSanPhamChiTiet.get(i));
                            listSanPhamChiTietTamThoiString.add(listSanPhamChiTietString.get(i));
                            mDanhMucSanPhamAdapter.notifyDataSetChanged();
                        }
                    }
                    // Toast.makeText(TaoGianHangActivity.this, idDanhMuc, Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerTaoGianHangSanPham.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listSanPhamChiTietTamThoi.size()>0){
                    idDanhMucSanPham=listSanPhamChiTietTamThoi.get(position).getId();
                    tenDanhMucSanPham=listSanPhamChiTietTamThoi.get(position).getTenLoaiSanPham();
                   // Toast.makeText(TaoGianHangActivity.this, idDanhMuc, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gridViewTaoGianHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (listAnh.size() - 1)) {
                    xuLyThemAnh();
                    //Toast.makeText(TaoGianHangActivity.this, "aaaaaa", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void xuLyPost() {
        if (listAnhString.size() < 5) {
            Toast.makeText(TaoGianHangActivity.this, "Hãy thêm ảnh sản phẩm", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(),"Hàng của bạn đang được up, xin đợi phản hồi !!!",Toast.LENGTH_LONG).show();
            final MyCommand myCommand = new MyCommand(getApplicationContext());
            for (int i = 0; i < listAnhString.size(); i++) {
                try {
                    Bitmap bitmap = ImageLoader.init().from(listAnhString.get(i)).requestSize(256, 256).getBitmap();
                    final String encodedBitMap = ImageBase64.encode(bitmap);
                    String url = Auth.domain+"/taogianhangjson2.php";
                    StringRequest stringRequest;
                    if (i == 0) {
                        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Toast.makeText(TaoGianHangActivity.this,response,Toast.LENGTH_LONG).show();
                                txtTaoGianHangNgayThuHoach.setText("Ngày thu hoạch");
                                txtTaoGianHangDonGia.setText("");
                                txtTaoGianHangSoLuongBan.setText("");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loi++;
                                //  Toast.makeText(TaoGianHangActivity.this,"Sự cố khi up ảnh",Toast.LENGTH_LONG).show();
                                // Toast.makeText(TaoGianHangActivity.this,"Sự cố khi up ảnh"+error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("loop", "0");
                                map.put("image", encodedBitMap);
                                map.put("idDanhMuc", idDanhMuc);
                                map.put("tenSanPham", tenDanhMucSanPham);
                                map.put("donGia", txtTaoGianHangDonGia.getText().toString());
                                map.put("ngayThuHoach", txtTaoGianHangNgayThuHoach.getText().toString());
                                map.put("soLuong", txtTaoGianHangSoLuongBan.getText().toString());
                                map.put("KhachHangId", Auth.khachHangId);
                                map.put("LoaiSanPhamChiTietId", idDanhMucSanPham);
                                return map;
                            }
                        };
                    } else {
                        final int vitri = i;
                        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Toast.makeText(TaoGianHangActivity.this,response,Toast.LENGTH_LONG).show();
                                if (vitri == (listAnhString.size() - 1)) {
                                    listAnh.clear();
                                    listAnh.add(getResources().getDrawable(R.drawable.them_anh));
                                    listAnhString.clear();
                                    mTaoGianHangAdapter.notifyDataSetChanged();
                                    if (loi == 0) {
                                        Toast.makeText(TaoGianHangActivity.this, "Đăng tin thành công", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(TaoGianHangActivity.this, "Đã có sự cố xảy ra", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                loi++;
                                // Toast.makeText(TaoGianHangActivity.this,"Sự cố khi up ảnh",Toast.LENGTH_LONG).show();
                                // Toast.makeText(TaoGianHangActivity.this,"Sự cố khi up ảnh"+error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("loop", "1");
                                map.put("image", encodedBitMap);
                                map.put("KhachHangId", Auth.khachHangId);
                                return map;
                            }
                        };
                    }

                    myCommand.add(stringRequest);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        /*Toast.makeText(this,"idDanhMuc"+idDanhMuc
                        +"tenSanPham"+txtTaoGianHangTenSanPham.getText().toString()+
                "donGia"+txtTaoGianHangDonGia.getText().toString()+
                "ngayThuHoach"+txtTaoGianHangNgayThuHoach.getText().toString()+
                "soLuong"+txtTaoGianHangSoLuongBan.getText().toString()+
                "KhachHangId"+Auth.khachHangId,Toast.LENGTH_LONG).show();*/
            myCommand.execute();

        }

    }

    private void xuLyThemAnh() {
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        Intent in = galleryPhoto.openGalleryIntent();
        startActivityForResult(in, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                try {
                    if (listAnh.size() > 10) {
                        Toast.makeText(TaoGianHangActivity.this, "Số ảnh được phép đăng đã đạt tối đa", Toast.LENGTH_LONG).show();
                    } else {
                        Drawable drawable = ImageLoader.init().from(photoPath).requestSize(256, 256).getImageDrawable();
                        listAnh.add(0, drawable);
                        listAnhString.add(0, photoPath);
                        mTaoGianHangAdapter.notifyDataSetChanged();
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}