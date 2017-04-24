package com.wimex.mbns.NhaSanXuat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.NguoiMua.ChinhSuaThongTinActivity;
import com.wimex.mbns.NguoiMua.ThongTinTaiKhoanActivity;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;


public class ThongTinTaiKhoanNSXActivity extends AppCompatActivity {
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
        } else if (id == R.id.nav_home) {
            Intent mMoveToTrangChuNSXActivity = new Intent(getApplicationContext(), TrangChuNSXActivity.class);
            mMoveToTrangChuNSXActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mMoveToTrangChuNSXActivity);
            finish();
        }
        return super.onOptionsItemSelected(item);

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }*/
}
