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

public class SignUp2Activity extends AppCompatActivity {
    EditText edXacNhanSoDienThoai;
    Button btXacNhanMaDienThoai;
    String phoneNumber;
    String getPhoneNumber="";
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up22);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        addControls();
        addEvents();
    }

    private void addEvents() {
        btXacNhanMaDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> postData = new HashMap<>();
                postData.put("phone_number", getPhoneNumber);
                postData.put("code",edXacNhanSoDienThoai.getText().toString().trim());
                PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(SignUp2Activity.this, postData, new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                       //  Toast.makeText(SignUp2Activity.this, s, Toast.LENGTH_LONG).show();
                        if (s.equals("SUCCESS")){
                            Intent mMoveToSignUp3Activity = new Intent(SignUp2Activity.this,SignUp3Activity.class);
                            mMoveToSignUp3Activity.putExtra("soDienThoai",phoneNumber);
                            startActivity(mMoveToSignUp3Activity);
                        }else if(s.equals("LONG_TIME")){
                            Toast.makeText(SignUp2Activity.this,"Đã quá thời gian xác nhận mã (5 phút)\nXin vui lòng đang ký lại.",Toast.LENGTH_LONG).show();
                            Intent iQuayLai = new Intent(SignUp2Activity.this, SignUp1Activity.class);
                            startActivity(iQuayLai);
                        }else if(s.equals("WRONG_CODE")){
                            Toast.makeText(SignUp2Activity.this,"Quý khách nhập sai mã\nVui lòng kiểm tra lại.",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(SignUp2Activity.this,"Lỗi xảy ra",Toast.LENGTH_LONG).show();
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
        SharedPreferences sharedPreferences = getSharedPreferences("phone_number_sign_up",MODE_PRIVATE);
        getPhoneNumber = sharedPreferences.getString("phone_number","");
        Toast.makeText(this,"Mã xác nhận đã được gửi tới\n" + getPhoneNumber,Toast.LENGTH_LONG).show();
        edXacNhanSoDienThoai = (EditText) findViewById(R.id.edXacNhanSoDienThoai);

        edXacNhanSoDienThoai.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        btXacNhanMaDienThoai = (Button) findViewById(R.id.btXacNhanMaDienThoai);
        Intent i = getIntent();
        phoneNumber = i.getStringExtra("soDienThoai");
    }
}
