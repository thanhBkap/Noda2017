package com.wimex.mbns.SignInSignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
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
import com.wimex.mbns.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

public class QuenMatKhau2 extends AppCompatActivity {
    EditText edQMKXacNhanSoDienThoai;
    Button btQMKXacNhanMaDienThoai;
    String phoneNumber;
    String getPhoneNumber="";
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quen_mat_khau2);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }
    private void addEvents() {
        btQMKXacNhanMaDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> postData = new HashMap<>();
                postData.put("phone_number", getPhoneNumber);
                postData.put("code",edQMKXacNhanSoDienThoai.getText().toString().trim());
                PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(QuenMatKhau2.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                     //   Toast.makeText(QuenMatKhau2.this, s, Toast.LENGTH_LONG).show();
                        if (s.equals("SUCCESS")){
                            Intent mMoveToQuenMatKhau3 = new Intent(QuenMatKhau2.this,QuenMatKhau3.class);
                            mMoveToQuenMatKhau3.putExtra("soDienThoai",phoneNumber);
                            startActivity(mMoveToQuenMatKhau3);
                        }else if(s.equals("LONG_TIME")){
                            Toast.makeText(QuenMatKhau2.this,"Code đã hết giờ (5 phút), hãy tạo lại ... ",Toast.LENGTH_LONG).show();
                        }else if(s.equals("WRONG_CODE")){
                            Toast.makeText(QuenMatKhau2.this,"Sai mã, xin nhập lại chính xác",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(QuenMatKhau2.this,"Lỗi xảy ra ",Toast.LENGTH_LONG).show();
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
                postResponseAsyncTask.execute(Auth.domain+"/esms_xuly.php");

            }
        });
    }

    private void addControls() {
        SharedPreferences sharedPreferences = getSharedPreferences("phone_number_miss_pass",MODE_PRIVATE);
        getPhoneNumber = sharedPreferences.getString("phone_number","");
        edQMKXacNhanSoDienThoai = (EditText) findViewById(R.id.edQMKXacNhanSoDienThoai);
        btQMKXacNhanMaDienThoai = (Button) findViewById(R.id.btQMKXacNhanMaDienThoai);
        Intent i = getIntent();
        phoneNumber = i.getStringExtra("soDienThoai");
        edQMKXacNhanSoDienThoai.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
    }
}
