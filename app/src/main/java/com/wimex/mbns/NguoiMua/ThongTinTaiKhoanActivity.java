package com.wimex.mbns.NguoiMua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public class ThongTinTaiKhoanActivity extends Menu_CT {
    TextView txtThongtinTaiKhoanTen, txtThongtinTaiKhoanEmail,
            txtThongtinTaiKhoanDiaChiChiTiet, txtThongtinTaiKhoanDiaChiPhuong,
            txtThongtinTaiKhoanDiaChiQuan, txtThongtinTaiKhoanDiaChiTinh,
            txtThongtinTaiKhoanSoDienThoai, txtThongtinTaiKhoanDoiTuong, txtThongTinTaiKhoanChinhSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thong_tin_tai_khoan);
        super.onCreate(savedInstanceState);
        addControls();
        addEvents();
    }

    private void addEvents() {
        txtThongTinTaiKhoanChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mMoveToChinhSuaThongTinActivity = new Intent(getApplicationContext(), ChinhSuaThongTinActivity.class);
                startActivity(mMoveToChinhSuaThongTinActivity);
                finish();
            }
        });
    }


    private void addControls() {
        txtThongtinTaiKhoanTen = (TextView) findViewById(R.id.txtThongtinTaiKhoanTen);
        txtThongtinTaiKhoanEmail = (TextView) findViewById(R.id.txtThongtinTaiKhoanEmail);
        txtThongtinTaiKhoanDiaChiChiTiet = (TextView) findViewById(R.id.txtThongtinTaiKhoanDiaChiChiTiet);
        txtThongtinTaiKhoanDiaChiPhuong = (TextView) findViewById(R.id.txtThongtinTaiKhoanDiaChiPhuong);
        txtThongtinTaiKhoanDiaChiQuan = (TextView) findViewById(R.id.txtThongtinTaiKhoanDiaChiQuan);
        txtThongtinTaiKhoanDiaChiTinh = (TextView) findViewById(R.id.txtThongtinTaiKhoanDiaChiTinh);
        txtThongtinTaiKhoanSoDienThoai = (TextView) findViewById(R.id.txtThongtinTaiKhoanSoDienThoai);
        txtThongtinTaiKhoanDoiTuong = (TextView) findViewById(R.id.txtThongtinTaiKhoanDoiTuong);
        txtThongTinTaiKhoanChinhSua = (TextView) findViewById(R.id.txtThongTinTaiKhoanChinhSua);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // new Task().execute("http://mbns.esy.es/thongtintaikhoanjson.php");
            }
        });
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
                        txtThongtinTaiKhoanDiaChiChiTiet.setText(thongTinTaiKhoan.getString("detail"));
                        txtThongtinTaiKhoanDiaChiPhuong.setText(thongTinTaiKhoan.getString("ward"));
                        txtThongtinTaiKhoanDiaChiQuan.setText(thongTinTaiKhoan.getString("district"));
                        txtThongtinTaiKhoanDiaChiTinh.setText(thongTinTaiKhoan.getString("province"));
                        txtThongtinTaiKhoanDoiTuong.setText(thongTinTaiKhoan.getString("doiTuong"));
                        txtThongtinTaiKhoanEmail.setText(thongTinTaiKhoan.getString("email"));
                        txtThongtinTaiKhoanSoDienThoai.setText(thongTinTaiKhoan.getString("soDienThoai"));
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

    }

    //lay du lieu tai khoan tu server
    /*class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject thongTinTaiKhoan = root.getJSONObject(i);
                    //  Toast.makeText(ThongTinTaiKhoanActivity.this, "ok"+Auth.khachHangId +"---"+thongTinTaiKhoan.getString("khachHangId"), Toast.LENGTH_SHORT).show();
                    if (thongTinTaiKhoan.getString("khachHangId").equals(Auth.khachHangId)) {
                        //Toast.makeText(ThongTinTaiKhoanActivity.this, "ok", Toast.LENGTH_SHORT).show();
                        txtThongtinTaiKhoanTen.setText(thongTinTaiKhoan.getString("tenKhachHang"));
                        txtThongtinTaiKhoanDiaChiChiTiet.setText(thongTinTaiKhoan.getString("detail"));
                        txtThongtinTaiKhoanDiaChiPhuong.setText(thongTinTaiKhoan.getString("ward"));
                        txtThongtinTaiKhoanDiaChiQuan.setText(thongTinTaiKhoan.getString("district"));
                        txtThongtinTaiKhoanDiaChiTinh.setText(thongTinTaiKhoan.getString("province"));
                        txtThongtinTaiKhoanDoiTuong.setText(thongTinTaiKhoan.getString("doiTuong"));
                        txtThongtinTaiKhoanEmail.setText(thongTinTaiKhoan.getString("email"));
                        txtThongtinTaiKhoanSoDienThoai.setText(thongTinTaiKhoan.getString("soDienThoai"));
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/
}
