package com.wimex.mbns.NguoiMua;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.DiaChiAdapter;
import com.wimex.mbns.DiaChi.DiaChiMoi;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.District;
import com.wimex.mbns.Model.Province;
import com.wimex.mbns.Model.Ward;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangChu;
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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChinhSuaThongTinActivity extends AppCompatActivity {
    public static String WARD_ID = "32249";
    public static String DISTRICT_ID = "974";
    public static String PROVINCE_ID = "97";
    public static String DETAIL = "Chưa rõ";
    EditText txtThongtinTaiKhoanTen, txtThongtinTaiKhoanEmail;
    Button mBtTiepTucDiaChiMoi;
    EditText edDiaChiCuThe, txtDiaChiMoiTinh, txtDiaChiMoiQuan, txtDiaChiMoiPhuong;
    AutoCompleteTextView actvTinh, actvQuan, actvXa;
    ArrayList<Ward> arrayListWard;
    ArrayList<String> arrayListXa;
    ArrayList<String> arrayListXaKhongDau;
    ArrayList<String> arrayListXaMain;
    ArrayList<District> arrayListDistrict;
    ArrayList<String> arrayListQuan;
    ArrayList<String> arrayListQuanKhongDau;
    ArrayList<String> arrayListQuanMain;
    ArrayList<Province> arrayListProvince;
    ArrayList<String> arrayListTinh;
    ArrayList<String> arrayListTinhKhongDau;
    ArrayList<String> arrayListTinhMain;
    ArrayAdapter<String> arrayAdapterXa, arrayAdapterQuan, arrayAdapterTinh;
    String provinceId, districtId, wardId, detail;
    String tmp = "hohoho";
    DiaChiAdapter diaChiAdapterTinh, diaChiAdapterQuan, diaChiAdapterPhuong;
    ListView lvDiaChiMoiTinh, lvDiaChiMoiQuan, lvDiaChiMoiPhuong;
    public static ArrayList<String> listDiaChiMoi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chinh_sua_thong_tin);
        super.onCreate(savedInstanceState);
        addControls();
        addEvents();
    }

    private void addControls() {
        tmp = Auth.khachHangId;
        // Toast.makeText(DiaChiMoi.this, "" + tmp, Toast.LENGTH_SHORT).show();

        txtThongtinTaiKhoanTen = (EditText) findViewById(R.id.txtThongtinTaiKhoanTen);
        txtThongtinTaiKhoanEmail = (EditText) findViewById(R.id.txtThongtinTaiKhoanEmail);

        txtThongtinTaiKhoanTen.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
        txtThongtinTaiKhoanEmail.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        mBtTiepTucDiaChiMoi = (Button) findViewById(R.id.btTiepTucDiaChi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        edDiaChiCuThe = (EditText) findViewById(R.id.edDiaChiCuThe);

        edDiaChiCuThe.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        arrayListWard = new ArrayList<>();
        arrayListDistrict = new ArrayList<>();
        arrayListProvince = new ArrayList<>();
        arrayListQuanKhongDau = new ArrayList<>();
        arrayListQuanMain = new ArrayList<>();
        arrayListQuan = new ArrayList<>();
        arrayListTinhKhongDau = new ArrayList<>();
        arrayListTinhMain = new ArrayList<>();
        arrayListTinh = new ArrayList<>();
        arrayListXaKhongDau = new ArrayList<>();
        arrayListXaMain = new ArrayList<>();
        arrayListXa = new ArrayList<>();

       /* actvQuan = (AutoCompleteTextView) findViewById(R.id.actvQuanHuyen);
        actvTinh = (AutoCompleteTextView) findViewById(R.id.actvTinh);
        actvXa = (AutoCompleteTextView) findViewById(R.id.actvXaPhuong);*/
        /*String[] a ={"aaa","abfgfg","sdsd","fdffd"};
        ArrayList<String> cart_number = new ArrayList();
        cart_number.add("bbbb");
        cart_number.add("bbbbdsdfd");
        cart_number.add("dbbb");
        cart_number.add("cbbb");
        cart_number.add("bèdbb");
        cart_number.add("rbbb");*/
        /*arrayAdapterTinh = new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayListTinh);
        arrayAdapterTinh=new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,abc);
        arrayAdapterQuan = new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayListQuan);
        arrayAdapterXa = new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayListXa);*/
        txtDiaChiMoiTinh = (EditText) findViewById(R.id.txtDiaChiMoiTinh);

        txtDiaChiMoiTinh.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        lvDiaChiMoiTinh = (ListView) findViewById(R.id.lvDiaChiMoiTinh);
        diaChiAdapterTinh = new DiaChiAdapter(this, arrayListTinh, arrayListTinhKhongDau, arrayListTinhMain);
        lvDiaChiMoiTinh.setAdapter(diaChiAdapterTinh);

        txtDiaChiMoiQuan = (EditText) findViewById(R.id.txtDiaChiMoiQuan);

        txtDiaChiMoiQuan.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        lvDiaChiMoiQuan = (ListView) findViewById(R.id.lvDiaChiMoiQuan);
        diaChiAdapterQuan = new DiaChiAdapter(this, arrayListQuan, arrayListQuanKhongDau, arrayListQuanMain);
        lvDiaChiMoiQuan.setAdapter(diaChiAdapterQuan);

        txtDiaChiMoiPhuong = (EditText) findViewById(R.id.txtDiaChiMoiPhuong);

        txtDiaChiMoiPhuong.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        lvDiaChiMoiPhuong = (ListView) findViewById(R.id.lvDiaChiMoiPhuong);
        diaChiAdapterPhuong = new DiaChiAdapter(this, arrayListXa, arrayListXaKhongDau, arrayListXaMain);
        lvDiaChiMoiPhuong.setAdapter(diaChiAdapterPhuong);
        /*actvTinh.setAdapter(arrayAdapterTinh);
        actvQuan.setAdapter(arrayAdapterQuan);
        actvXa.setAdapter(arrayAdapterXa);
        actvTinh.setThreshold(1);
        actvTinh.setDropDownHeight(400);
        actvQuan.setThreshold(1);
        actvQuan.setDropDownHeight(400);
        actvXa.setThreshold(1);
        actvXa.setDropDownHeight(400);*/
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
                        JSONObject thongTinTaiKhoan = root.getJSONObject(i);
                        //  Toast.makeText(ThongTinTaiKhoanActivity.this, "ok"+Auth.khachHangId +"---"+thongTinTaiKhoan.getString("khachHangId"), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(ThongTinTaiKhoanActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        txtThongtinTaiKhoanTen.setText(thongTinTaiKhoan.getString("tenKhachHang"));
                        txtThongtinTaiKhoanEmail.setText(thongTinTaiKhoan.getString("email"));

                        WARD_ID = thongTinTaiKhoan.getString("wardid");
                        DISTRICT_ID = thongTinTaiKhoan.getString("districtid");
                        PROVINCE_ID = thongTinTaiKhoan.getString("provinceid");
                        DETAIL = thongTinTaiKhoan.getString("detail");
                        txtDiaChiMoiTinh.setHint(thongTinTaiKhoan.getString("province"));
                        txtDiaChiMoiQuan.setHint(thongTinTaiKhoan.getString("district"));
                        txtDiaChiMoiPhuong.setHint(thongTinTaiKhoan.getString("ward"));
                        edDiaChiCuThe.setText(thongTinTaiKhoan.getString("detail"));
                        break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        postResponseAsyncTask.execute(Auth.domain + "/thongtintaikhoanjson.php");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain + "/phuongjson.php");
                new Task2().execute(Auth.domain + "/quanjson.php");
                new Task3().execute(Auth.domain + "/tinhjson.php");
            }
        });

    }

    private void addEvents() {
        mBtTiepTucDiaChiMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTiepTucDiaChiMoi();
            }
        });

        txtDiaChiMoiTinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lvDiaChiMoiTinh.setVisibility(View.VISIBLE);
                //  diaChiMoiAdapter.getFilter().filter(s);
                diaChiAdapterTinh.filter2(DinhDangChu.unAccent(s.toString()));
                // Toast.makeText(getApplicationContext(),s.toString()+"txt",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtDiaChiMoiQuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lvDiaChiMoiQuan.setVisibility(View.VISIBLE);
                //  diaChiMoiAdapter.getFilter().filter(s);
                diaChiAdapterQuan.filter2(DinhDangChu.unAccent(s.toString()));
                // Toast.makeText(getApplicationContext(),s.toString()+"txt",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtDiaChiMoiPhuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lvDiaChiMoiPhuong.setVisibility(View.VISIBLE);
                //  diaChiMoiAdapter.getFilter().filter(s);
                diaChiAdapterPhuong.filter2(DinhDangChu.unAccent(s.toString()));
                // Toast.makeText(getApplicationContext(),s.toString()+"txt",Toast.LENGTH_LONG).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvDiaChiMoiTinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String name = parent.getItemAtPosition(position).toString();
                txtDiaChiMoiTinh.setText(DiaChiMoi.listDiaChiMoi.get(position));
                //  txtDiaChiMoiTinh.setText(name);
                for (Province province : arrayListProvince) {
                    if (province.getName().equals(txtDiaChiMoiTinh.getText().toString())) {
                        provinceId = province.getProvinceId();
                        PROVINCE_ID = provinceId;
                        break;
                    }
                }
                for (District district : arrayListDistrict) {
                    if (district.getProvinceId().equals(provinceId)) {
                        arrayListQuan.add(district.getName());
                        // Toast.makeText(DiaChiMoi.this,"aa!"+"wardId"+"districtId",Toast.LENGTH_SHORT).show();
                        arrayListQuanMain.add(district.getName());
                        arrayListQuanKhongDau.add(DinhDangChu.unAccent(district.getName()));
                        // Toast.makeText(getApplicationContext(),DinhDangChu.unAccent(mProvince.getName()),Toast.LENGTH_LONG).show();
                        diaChiAdapterQuan.notifyDataSetChanged();
                    }
                }

                //  DiaChiMoi.listDiaChiMoi.clear();
                lvDiaChiMoiTinh.setVisibility(View.GONE);
            }
        });

        lvDiaChiMoiQuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = parent.getItemAtPosition(position).toString();
                //   txtDiaChiMoiQuan.setText(name);
                txtDiaChiMoiQuan.setText(DiaChiMoi.listDiaChiMoi.get(position));

                for (District district : arrayListDistrict) {
                    if (district.getName().equals(txtDiaChiMoiQuan.getText().toString())) {
                        districtId = district.getDistrictId();
                        DISTRICT_ID = districtId;
                        break;
                    }
                }
                for (Ward ward : arrayListWard) {
                    if (ward.getDistrictId().equals(districtId)) {
                        arrayListXa.add(ward.getName());
                        arrayListXaMain.add(ward.getName());
                        arrayListXaKhongDau.add(DinhDangChu.unAccent(ward.getName()));
                        // Toast.makeText(getApplicationContext(),DinhDangChu.unAccent(mProvince.getName()),Toast.LENGTH_LONG).show();
                        diaChiAdapterPhuong.notifyDataSetChanged();
                    }
                }
                lvDiaChiMoiQuan.setVisibility(View.GONE);
            }
        });

        lvDiaChiMoiPhuong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                txtDiaChiMoiPhuong.setText(DiaChiMoi.listDiaChiMoi.get(position));
                for (Ward ward : arrayListWard) {
                    if (ward.getName().equals(txtDiaChiMoiPhuong.getText().toString())) {
                        wardId = ward.getWardId();
                        WARD_ID = wardId;
                        break;
                    }
                }
                // DiaChiMoi.listDiaChiMoi.clear();
                lvDiaChiMoiPhuong.setVisibility(View.GONE);
            }
        });
    }

    private void xuLyTiepTucDiaChiMoi() {
        detail = edDiaChiCuThe.getText().toString();
        DETAIL = detail;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task4().execute(Auth.domain + "/chinhsuathongtintaikhoanjson.php");
            }
        });
        Intent mMoveToDiaChi = new Intent(ChinhSuaThongTinActivity.this, ThongTinTaiKhoanActivity.class);
        startActivity(mMoveToDiaChi);
        finish();
        Toast.makeText(getApplicationContext(), "Bạn đã cập nhật thành công", Toast.LENGTH_LONG).show();
    }

    private String makePostRequest(String url) {
        String kq = "";
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);
        // Các tham số truyền
        List nameValuePair = new ArrayList(8);
        nameValuePair.add(new BasicNameValuePair("xuLy", "insertDiaChi"));
        nameValuePair.add(new BasicNameValuePair("KhachHangId", tmp));
        nameValuePair.add(new BasicNameValuePair("provinceId", PROVINCE_ID));
        nameValuePair.add(new BasicNameValuePair("districtId", DISTRICT_ID));
        nameValuePair.add(new BasicNameValuePair("wardId", WARD_ID));
        nameValuePair.add(new BasicNameValuePair("detail", DETAIL));
        nameValuePair.add(new BasicNameValuePair("name", txtThongtinTaiKhoanTen.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("email", txtThongtinTaiKhoanEmail.getText().toString()));

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
        } catch (IOException e) {
            e.printStackTrace();
        }


        return kq;
    }

    class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject ward = root.getJSONObject(i);
                    if (ward.getString("wardId").equals("32249")) {

                    } else {
                        Ward mWard = new Ward();
                        mWard.setWardId(ward.getString("wardId"));
                        mWard.setDistrictId(ward.getString("districtId"));
                        mWard.setName(ward.getString("name"));
                        mWard.setType(ward.getString("type"));
                        arrayListWard.add(mWard);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(DiaChiMoi.this, "download finished !!!" + arrayListWard.size(), Toast.LENGTH_SHORT).show();
        }
    }

    class Task2 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject district = root.getJSONObject(i);
                    if (district.getString("provinceId").equals("974")) {

                    } else {
                        District mDistrict = new District();
                        mDistrict.setProvinceId(district.getString("provinceId"));
                        mDistrict.setDistrictId(district.getString("districtId"));
                        mDistrict.setName(district.getString("name"));
                        mDistrict.setType(district.getString("type"));
                        arrayListDistrict.add(mDistrict);

                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //   Toast.makeText(DiaChiMoi.this, "download finished !!!" + arrayListDistrict.size(), Toast.LENGTH_SHORT).show();
        }
    }

    class Task3 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ChinhSuaThongTinActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setMessage(getResources().getString(R.string.loading));
            progressDialog.setMax(100);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject province = root.getJSONObject(i);
                    if (province.getString("provinceId").equals("97")) {

                    } else {
                        Province mProvince = new Province();
                        mProvince.setProvinceId(province.getString("provinceId"));
                        mProvince.setName(province.getString("name"));
                        mProvince.setType(province.getString("type"));
                        arrayListProvince.add(mProvince);
                        arrayListTinh.add(mProvince.getName());

                        arrayListTinhMain.add(mProvince.getName());
                        arrayListTinhKhongDau.add(DinhDangChu.unAccent(mProvince.getName()));
                        // Toast.makeText(getApplicationContext(),DinhDangChu.unAccent(mProvince.getName()),Toast.LENGTH_LONG).show();
                        diaChiAdapterTinh.notifyDataSetChanged();

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.cancel();
            // Toast.makeText(DiaChiMoi.this, "download finished !!!" + arrayListProvince.size(), Toast.LENGTH_SHORT).show();
        }
    }

    class Task4 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }
    }
}
