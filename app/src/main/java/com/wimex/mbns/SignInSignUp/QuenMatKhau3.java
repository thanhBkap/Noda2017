package com.wimex.mbns.SignInSignUp;

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

import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.Cipher;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class QuenMatKhau3 extends AppCompatActivity {
    EditText txtQMKNhapLaiPass1,txtQMKNhapLaiPass2;
    Button btnQMKXacNhan;
    String soDienThoai;
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau3);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnQMKXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXacNhan();
            }
        });
    }

    private void xuLyXacNhan() {
        if (txtQMKNhapLaiPass1.getText().toString().equals("")||txtQMKNhapLaiPass2.getText().toString().equals("")){
            Toast.makeText(QuenMatKhau3.this,"Hãy nhập đầy đủ các thông tin",Toast.LENGTH_SHORT).show();
        } else if (txtQMKNhapLaiPass1.getText().toString().length()<6||txtQMKNhapLaiPass1.getText().toString().length()>20){
            Toast.makeText(QuenMatKhau3.this,"Mật khẩu nằm trong khoảng 6~20 kí tự",Toast.LENGTH_LONG).show();
        }if(txtQMKNhapLaiPass1.getText().toString().equals(txtQMKNhapLaiPass2.getText().toString())){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Task().execute(Auth.domain+"/xulytaikhoan.php");
                }
            });
        }else{
            txtQMKNhapLaiPass1.setText("");
            txtQMKNhapLaiPass2.setText("");
            txtQMKNhapLaiPass1.requestFocus();
            Toast.makeText(QuenMatKhau3.this,"Mật khẩu không giống nhau. Hãy nhập lại",Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        txtQMKNhapLaiPass1= (EditText) findViewById(R.id.txtQMKNhapLaiPass1);
        txtQMKNhapLaiPass2= (EditText) findViewById(R.id.txtQMKNhapLaiPass2);

        txtQMKNhapLaiPass1.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
        txtQMKNhapLaiPass2.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        btnQMKXacNhan = (Button) findViewById(R.id.btnQMKXacNhan);
        Intent i = getIntent();
        soDienThoai=i.getStringExtra("soDienThoai");
    }
    class Task extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent mMoveToSignInActivity = new Intent(QuenMatKhau3.this,SigninActivity.class);
            mMoveToSignInActivity.putExtra("action","doimatkhau");
            startActivity(mMoveToSignInActivity);
        }
    }
    private String makePostRequest(String url) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(3);
        nameValuePair.add(new BasicNameValuePair("xuly","doiMK"));
        nameValuePair.add(new BasicNameValuePair("matKhauMoi", Cipher.encryptMD5(txtQMKNhapLaiPass1.getText().toString())));
        nameValuePair.add(new BasicNameValuePair("soDienThoai", soDienThoai));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kq;
    }
}
