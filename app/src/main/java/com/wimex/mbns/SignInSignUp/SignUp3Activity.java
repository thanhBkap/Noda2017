package com.wimex.mbns.SignInSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.wimex.mbns.XuLyData.Check;
import com.wimex.mbns.XuLyData.Cipher;
import com.wimex.mbns.R;
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

public class SignUp3Activity extends AppCompatActivity {
    String phoneNumber;
    EditText edHoVaTen, edMatKhau, edEmail,edNhacLaiMatKhau;
    Button btDangKy;
    String id = "0";
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_33);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addControls() {
        edEmail = (EditText) findViewById(R.id.edEmail);
        edHoVaTen = (EditText) findViewById(R.id.edHoVaTen);
        edMatKhau = (EditText) findViewById(R.id.edMatKhau);
        edNhacLaiMatKhau = (EditText) findViewById(R.id.edNhacLaiMatKhau);
        btDangKy = (Button) findViewById(R.id.btDangKy);
        Intent i = getIntent();
        phoneNumber = i.getStringExtra("soDienThoai");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task2().execute("http://mbns.esy.es/signup3json.php");
            }
        });

        edHoVaTen.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
        edEmail.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
        edMatKhau.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
        edNhacLaiMatKhau.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
    }

    private void addEvents() {
        btDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edHoVaTen.getText().toString().length()<2){
                    Toast.makeText(SignUp3Activity.this,"Hãy điền họ tên",Toast.LENGTH_LONG).show();
                }else if (edMatKhau.getText().toString().length()<6||edMatKhau.getText().toString().length()>20){
                    Toast.makeText(SignUp3Activity.this,"Mật khẩu nằm trong khoảng 6~20 kí tự",Toast.LENGTH_LONG).show();
                }else if(!edMatKhau.getText().toString().equals(edNhacLaiMatKhau.getText().toString())){
                    Toast.makeText(SignUp3Activity.this,"Mật khẩu không giống nhau ...",Toast.LENGTH_LONG).show();
                }else if(!Check.isEmailValid(edEmail.getText().toString())){
                    Toast.makeText(SignUp3Activity.this,"Không đúng định dạng email",Toast.LENGTH_LONG).show();
                }else{
                    thucHienDangKy();
                }
            }
        });
    }

    private void thucHienDangKy() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute("http://mbns.esy.es/xulytaikhoan.php");
            }
        });

    }

    //up thông tin đăng kí
    class Task extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            // gửi yêu cầu chèn dữ liệu lên server
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //đăng ký thành công => quay về trang chủ và đi kèm 1 biến SignUpStatus = success
            Intent mMoveToSignInActivity = new Intent(SignUp3Activity.this, SigninActivity.class);
            mMoveToSignInActivity.putExtra("action", "signup");
            // Toast.makeText(SignUp3Activity.this,"?"+s,Toast.LENGTH_SHORT).show();
            startActivity(mMoveToSignInActivity);
        }
    }

    //lấy số khachhangid max hiện tại
    class Task2 extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(SignUp3Activity.this);
            dialog.setMessage("Please wait a minute");
            dialog.setTitle("Loading");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            // gửi yêu cầu chèn dữ liệu lên server
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                JSONObject data = root.getJSONObject(0);
                int tmp = Integer.parseInt(data.getString("maxid"))+1;
                id = tmp+"";

            } catch (JSONException e) {
                e.printStackTrace();
            }
            dialog.dismiss();
        }
    }

    private String makePostRequest(String url) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(6);
        nameValuePair.add(new BasicNameValuePair("xuLy", "insertTK"));
        nameValuePair.add(new BasicNameValuePair("hoTen", edHoVaTen.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("matKhau", Cipher.encryptMD5(edMatKhau.getText().toString())));
        nameValuePair.add(new BasicNameValuePair("email", edEmail.getText().toString()));
        nameValuePair.add(new BasicNameValuePair("soDienThoai", "" + phoneNumber));
        nameValuePair.add(new BasicNameValuePair("khachHangId",id));
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
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

}
