package com.wimex.mbns.SignInSignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.TaiKhoan;
import com.wimex.mbns.NguoiMua.TrangChu;
import com.wimex.mbns.NhaSanXuat.TrangChuNSXActivity;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.Cipher;
import com.wimex.mbns.XuLyData.JsonToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SigninActivity extends AppCompatActivity {
    EditText edSignInPhoneNumber, edSignInPassword;
    Button btSignIn;
    TextView tvSignInQuenMatKhau, btNewSignUp;
    CheckBox cbHienThiMatKhau, cbLuuDangNhap;
    ArrayList<TaiKhoan> listTaiKhoan;
    String soDienThoai = "";
    String matKhau = "";
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in2);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }



    private void addControls() {
       // Toast.makeText(SigninActivity.this,Auth.khachHangId,Toast.LENGTH_SHORT).show();
        edSignInPhoneNumber = (EditText) findViewById(R.id.edSignInPhoneNumber);
        edSignInPassword = (EditText) findViewById(R.id.edSignInPassword);

        edSignInPhoneNumber.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);
        edSignInPassword.getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorControlHighlight), PorterDuff.Mode.SRC_ATOP);

        btSignIn = (Button) findViewById(R.id.btSignIn);
        btNewSignUp = (TextView) findViewById(R.id.btNewSignUp);
        tvSignInQuenMatKhau = (TextView) findViewById(R.id.tvSignInQuenMatKhau);
        cbHienThiMatKhau = (CheckBox) findViewById(R.id.cbHienThiMatKhau);
        cbLuuDangNhap = (CheckBox) findViewById(R.id.cbLuuDangNhap);
        listTaiKhoan = new ArrayList<>();
        //lấy hết dữ liệu từ trên mạng đổ vào danh sách

        // xuLyTuDongSignIn();
    }

    private void addEvents() {
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLySignIn();
            }
        });
        btNewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyDangKyMoi();
            }
        });
        tvSignInQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyQuenMatKhau();
            }
        });
        cbHienThiMatKhau.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //chuyển từ kiểu password sang chữ thường
                    edSignInPassword.setInputType(InputType.TYPE_TEXT_VARIATION_NORMAL);

                } else {
                    //ngược lại
                    // edSignInPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edSignInPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //intent nhận sau khi đăng ký thành công
        SharedPreferences sharedPreferences = getSharedPreferences("ten", MODE_PRIVATE);
        edSignInPhoneNumber.setText(sharedPreferences.getString("soDienThoai", ""));
        edSignInPassword.setText(sharedPreferences.getString("matKhau", ""));
        cbLuuDangNhap.setChecked(sharedPreferences.getBoolean("luuDangNhap", true));
        cbHienThiMatKhau.setChecked(sharedPreferences.getBoolean("hienThiMatKhau", false));
        //Toast.makeText(SigninActivity.this,"not null",Toast.LENGTH_SHORT).show();
        soDienThoai = sharedPreferences.getString("soDienThoai", "");
        matKhau = sharedPreferences.getString("matKhau", "");
        Intent i = getIntent();
        //kiểm tra xem có đăng ký hay nếu có sẽ trả về giá trị trong biến SignUpStatus
        if (i.hasExtra("action")) {
            //nếu nhận giá trị từ intent với giá trị là success tức đăng ký thành công
            String status = i.getStringExtra("action");
            if (status.equals("signup")) {
                Toast.makeText(SigninActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }else if (status.equals("doimatkhau")) {
                Toast.makeText(SigninActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            }else if (status.equals("logout")){
                Toast.makeText(SigninActivity.this,"Đăng xuất thành công",Toast.LENGTH_SHORT).show();
                cbLuuDangNhap.setChecked(false);
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain+"/taikhoanjson.php");
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (cbLuuDangNhap.isChecked()) {
            // lưu dữ liệu trước đó vào các view
            SharedPreferences sharedPreferences = getSharedPreferences("ten", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("soDienThoai", edSignInPhoneNumber.getText().toString());
            editor.putString("matKhau", edSignInPassword.getText().toString());
            editor.putBoolean("luuDangNhap", cbLuuDangNhap.isChecked());
            editor.putBoolean("hienThiMatKhau", cbHienThiMatKhau.isChecked());
            editor.commit();
        } else {
            // lưu dữ liệu trống vào các view
            SharedPreferences sharedPreferences = getSharedPreferences("ten", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("soDienThoai", "");
            editor.putString("matKhau", "");
            editor.putBoolean("luuDangNhap", false);
            editor.putBoolean("hienThiMatKhau", false);
            editor.commit();
        }
    }


    private void xuLyQuenMatKhau() {
        Intent mMoveToQuenMatKhau1 = new Intent(SigninActivity.this, QuenMatKhau1.class);
        startActivity(mMoveToQuenMatKhau1);
    }

    private void xuLyDangKyMoi() {
        //chuyển sang màn hình đăng ký
        Intent mMoveToSignUp1Activity = new Intent(SigninActivity.this, SignUp1Activity.class);
        startActivity(mMoveToSignUp1Activity);
    }

    private void xuLySignIn() {
        String tenDangNhap = edSignInPhoneNumber.getText().toString();
        String pass = edSignInPassword.getText().toString();
        //biến ketQua lưu giá trị trả về khi tìm kiếm tài khoản để biết tài khoản tồn tại hay không
        int ketQua = 0;
        //kiểm tra tài khoản
        for (TaiKhoan tk : listTaiKhoan) {
            //trường hợp nhập đúng tên đăng nhập
            if (tenDangNhap.equals(tk.getTenDangNhap())) {
                //trả về kết quả bằng 1 => tài khoản tồn tại
                ketQua = 1;
                //nhập đúng mật khẩu
                if (Cipher.encryptMD5(pass).equals(tk.getMatKhau())) {
                    //đăng nhập chuyển sang trang chủ
                    Intent mMoveToTrangChu = new Intent(SigninActivity.this, TrangChu.class);
                    Intent mMoveToTrangChuNSX = new Intent(SigninActivity.this, TrangChuNSXActivity.class);
                    Auth.signInStatus="yes";
                    Auth.loaiKhachHangId=tk.getLoaiKhachHangId();
                    Auth.tenDangNhap=tk.getTenDangNhap();
                    Auth.khachHangId=tk.getKhachHangId();
//                    Auth.gioHang = new ArrayList<>();
                     finish();
                    if (Auth.loaiKhachHangId.equals("1")){
                        startActivity(mMoveToTrangChuNSX);
                    }else{
                        startActivity(mMoveToTrangChu);
                    }
                    break;
                } else {
                    // nhập sai mật khẩu => thông báo sai mật khẩu
                    Toast.makeText(SigninActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    edSignInPassword.setText("");
                    edSignInPassword.requestFocus();
                    break;
                }
            }
        }
        //trường hợp không tìm được tài khoản nào => thông báo tài khoản ko tồn tại
        if (ketQua == 0) {
            Toast.makeText(SigninActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            edSignInPassword.setText("");
            edSignInPhoneNumber.setText("");
            edSignInPhoneNumber.requestFocus();
        }
    }
    // tự động đăng nhập khi người dùng yêu cầu
    private void xuLyTuDongSignIn() {
        String tenDangNhap = soDienThoai;
        String pass = matKhau;
        //biến ketQua lưu giá trị trả về khi tìm kiếm tài khoản để biết tài khoản tồn tại hay không
        //kiểm tra tài khoản
        int i = 0;
        for (TaiKhoan tk : listTaiKhoan) {
            //trường hợp nhập đúng tên đăng nhập
            if (tenDangNhap.equals(tk.getTenDangNhap())) {
                //so sánh mật khẩu
                if (Cipher.encryptMD5(pass).equals(tk.getMatKhau())) {
                    //đăng nhập chuyển sang trang chủ
                    Intent mMoveToTrangChu = new Intent(SigninActivity.this, TrangChu.class);
                    Intent mMoveToTrangChuNSX = new Intent(SigninActivity.this, TrangChuNSXActivity.class);
                    Auth.signInStatus="yes";
                    Auth.loaiKhachHangId=tk.getLoaiKhachHangId();
                    Auth.tenDangNhap=tk.getTenDangNhap();
                    Auth.khachHangId=tk.getKhachHangId();
                    Auth.gioHang=new ArrayList<>();
                    finish();
                    if (Auth.loaiKhachHangId.equals("1")){
                        startActivity(mMoveToTrangChuNSX);
                    }else{
                        startActivity(mMoveToTrangChu);
                    }
                    break;
                }
            }
        }
    }

    class Task extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog = new ProgressDialog(SigninActivity.this);

        @Override
        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            //lấy json từ trên mạng về chuỗi
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.cancel();
            // từ xâu json ta duyệt lấy các thành phần muốn lấy
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    // ta lấy thông tin tenDangNhap và thông tin matKhau
                    JSONObject mTaiKhoan = root.getJSONObject(i);
                    TaiKhoan mTaiKhoanMoi = new TaiKhoan();
                    mTaiKhoanMoi.setTenDangNhap(mTaiKhoan.getString("tenDangNhap"));
                    mTaiKhoanMoi.setMatKhau(mTaiKhoan.getString("matKhau"));
                    mTaiKhoanMoi.setKhachHangId(mTaiKhoan.getString("khachHangId"));
                    mTaiKhoanMoi.setLoaiKhachHangId(mTaiKhoan.getString("LoaiKhachHangId"));
                    listTaiKhoan.add(mTaiKhoanMoi);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // tự động đăng nhập sau khi tải xong dữ liệu từ server
            if (cbLuuDangNhap.isChecked()){
                xuLyTuDongSignIn();
            }
        }
    }
}
