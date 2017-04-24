package com.wimex.mbns;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        /*Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(MainActivity.this, TrangChu.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();*/

       // Intent intent = new Intent(MainActivity.this, SigninActivity.class);
        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
        }else{
            Intent intent = new Intent(MainActivity.this, Welcome.class);
            // Intent intent = new Intent(MainActivity.this, DiaChiMoi.class);

            //  Intent intent = new Intent(MainActivity.this, Test.class);

            //  Intent intent = new Intent(MainActivity.this, DiaChiMoi.class);
            //  Intent intent = new Intent(MainActivity.this, DiaChiActivity.class);
            startActivity(intent);
        }

    }
}
