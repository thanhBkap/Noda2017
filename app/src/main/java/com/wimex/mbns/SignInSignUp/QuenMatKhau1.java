package com.wimex.mbns.SignInSignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.TaiKhoan;
import com.wimex.mbns.R;
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

public class QuenMatKhau1 extends AppCompatActivity {
    EditText edQMKSoDienThoai;
    Button btQMKXacNhanSoDienThoai;
    ArrayList<TaiKhoan> listSoDienThoai;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau12);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btQMKXacNhanSoDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edQMKSoDienThoai.getText().toString().isEmpty()) {
                    Toast.makeText(QuenMatKhau1.this, "Hãy nhập số điện thoại để xác nhận", Toast.LENGTH_LONG).show();
                } else if (edQMKSoDienThoai.getText().toString().length() < 10 || edQMKSoDienThoai.getText().toString().length() > 11) {
                    Toast.makeText(QuenMatKhau1.this, "Hãy nhập đúng số điện thoại để xác nhận (10 hoặc 11 số)", Toast.LENGTH_LONG).show();
                } else {
                    //Check xem đã tồn tại số điện thoại ng dùng đăng ký chưa
                    int trung = 0;
                    for (TaiKhoan tk : listSoDienThoai) {
                        // trường hợp trùng đưa ra thông báo
                        if (tk.getTenDangNhap().equals(edQMKSoDienThoai.getText().toString())) {
                            SharedPreferences sharedPreferences = getSharedPreferences("phone_number_miss_pass", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("phone_number", edQMKSoDienThoai.getText().toString().trim());
                            editor.commit();
                            HashMap<String, String> postData = new HashMap<>();
                            postData.put("phone_number", edQMKSoDienThoai.getText().toString().trim());
                            PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(QuenMatKhau1.this, postData, new AsyncResponse() {
                                @Override
                                public void processFinish(String s) {
                                    // Toast.makeText(SignUp1Activity.this, s, Toast.LENGTH_LONG).show();
                                    Intent mMoveToQuenMatKhau2 = new Intent(QuenMatKhau1.this, QuenMatKhau2.class);
                                    mMoveToQuenMatKhau2.putExtra("soDienThoai", edQMKSoDienThoai.getText().toString());
                                    startActivity(mMoveToQuenMatKhau2);
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
                            postResponseAsyncTask.execute(Auth.domain + "/esms.php");

                            trung = 1;
                            break;
                        }
                    }
                    // không trùng thì chuyển sang bước 2
                    if (trung == 0) {
                        Toast.makeText(QuenMatKhau1.this, "Tài khoản chưa đăng ký", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });

    }

    private void addControls() {
        edQMKSoDienThoai = (EditText) findViewById(R.id.edQMKSoDienThoai);
        edQMKSoDienThoai.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        btQMKXacNhanSoDienThoai = (Button) findViewById(R.id.btQMKXacNhanSoDienThoai);
        listSoDienThoai = new ArrayList<TaiKhoan>();
        new Task().execute(Auth.domain + "/taikhoanjson.php");
    }

    class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // từ xâu json ta duyệt lấy các thành phần muốn lấy
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // ta lấy thông tin tenDangNhap và thông tin matKhau
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject mTaiKhoan = root.getJSONObject(i);
                    TaiKhoan mTaiKhoanMoi = new TaiKhoan();
                    mTaiKhoanMoi.setTenDangNhap(mTaiKhoan.getString("tenDangNhap"));
                    mTaiKhoanMoi.setMatKhau(mTaiKhoan.getString("matKhau"));
                    listSoDienThoai.add(mTaiKhoanMoi);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}
