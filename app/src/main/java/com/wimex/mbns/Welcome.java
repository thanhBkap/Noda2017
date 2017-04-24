package com.wimex.mbns;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.ExceptionHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.NhaSanXuat;
import com.wimex.mbns.Model.TaiKhoan;
import com.wimex.mbns.NguoiMua.DSNSXActivity;
import com.wimex.mbns.NguoiMua.TrangChu;
import com.wimex.mbns.NhaSanXuat.TrangChuNSXActivity;
import com.wimex.mbns.XuLyData.Cipher;
import com.wimex.mbns.XuLyData.JsonToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;

public class Welcome extends AppCompatActivity {
    String soDienThoai = "";
    String matKhau = "";
    boolean signInStatus = false;
    ArrayList<TaiKhoan> listTaiKhoan;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences sharedPreferences = getSharedPreferences("ten", MODE_PRIVATE);
        soDienThoai = sharedPreferences.getString("soDienThoai", "");
        matKhau = sharedPreferences.getString("matKhau", "");
        signInStatus = sharedPreferences.getBoolean("luuDangNhap", false);
        listTaiKhoan = new ArrayList<>();
        countDownTimer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent i = new Intent(Welcome.this, TrangChu.class);
                startActivity(i);
                finish();
            }
        };
        checkVersionApp();
    }

    private void xuLyTuDongSignIn() {
        String tenDangNhap = soDienThoai;
        String pass = matKhau;
        //biến ketQua lưu giá trị trả về khi tìm kiếm tài khoản để biết tài khoản tồn tại hay không
        //kiểm tra tài khoản
        int ok = 0;
        for (TaiKhoan tk : listTaiKhoan) {
            //trường hợp nhập đúng tên đăng nhập
            if (tenDangNhap.equals(tk.getTenDangNhap())) {
                //so sánh mật khẩu
                if (Cipher.encryptMD5(pass).equals(tk.getMatKhau())) {
                    //đăng nhập chuyển sang trang chủ
                    Intent mMoveToTrangChu = new Intent(Welcome.this, TrangChu.class);
                    Intent mMoveToTrangChuNSX = new Intent(Welcome.this, TrangChuNSXActivity.class);
                    Auth.signInStatus = "yes";
                    Auth.loaiKhachHangId = tk.getLoaiKhachHangId();
                    Auth.tenDangNhap = tk.getTenDangNhap();
                    Auth.khachHangId = tk.getKhachHangId();
                    Auth.gioHang = new ArrayList<>();
                    finish();
                    if (Auth.loaiKhachHangId.equals("1")) {
                        ok++;
                        startActivity(mMoveToTrangChuNSX);
                        finish();
                    } else {
                        ok++;
                        startActivity(mMoveToTrangChu);
                        finish();
                    }
                    break;
                }
            }
        }
        if (ok == 0) {
            countDownTimer.start();
        }
    }

    private void checkVersionApp() {
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String packageName = info.packageName;
            int versionCode = info.versionCode;
            final String versionName = info.versionName;
            //Toast.makeText(this, "version name : " + versionName + "--- version code : " + versionCode, Toast.LENGTH_LONG).show();
            PostResponseAsyncTask postResponseAsyncTaskCheckVersion = new PostResponseAsyncTask(this, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    if (s.equals(versionName)) {
                        if (signInStatus) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    new Task().execute("http://mbns.esy.es/taikhoanjson.php");
                                }
                            });
                        } else {
                            countDownTimer.start();
                        }
                    } else {
                        final Dialog dialog = new Dialog(Welcome.this);
                        dialog.setCancelable(false);
                        dialog.setTitle("CẬP NHẬT PHIÊN BẢN MỚI");
                        dialog.setContentView(R.layout.dialog_trang_chu_update_version);

                        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);
//                        int dividerId = dialog.getContext().getResources().getIdentifier("android:id/dividerId", null, null);
//                        View divider = dialog.findViewById(dividerId);
//                        divider.setBackgroundColor(getResources().getColor(R.color.colorDialog));
                        int textViewId = dialog.getContext().getResources().getIdentifier("android:id/title", null, null);
                        //  int textViewId = dialog.getContext().getResources().getIdentifier("android:id/title", null, null);
                        TextView tv = (TextView) dialog.findViewById(textViewId);
                        if (tv != null) {
                            tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }else{

                        }
                        dialog.show();
                        TextView txtDialogTrangChu = (TextView) dialog.findViewById(R.id.txtDialogTrangChu);
                        txtDialogTrangChu.setText("PHIÊN BẢN " + s + " MỚI NHẤT");
                        Button btnDialogTrangChuOk = (Button) dialog.findViewById(R.id.btnDialogTrangChuOk);
                        Button btnDialogTrangChuCancel = (Button) dialog.findViewById(R.id.btnDialogTrangChuCancel);
                        btnDialogTrangChuCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                DSNSXActivity.listNhaSanXuat = new ArrayList<NhaSanXuat>();
                                if (signInStatus) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            new Task().execute("http://mbns.esy.es/taikhoanjson.php");
                                        }
                                    });
                                } else {
                                    countDownTimer.start();
                                }
                            }
                        });
                        btnDialogTrangChuOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.rovio.angrybirds"));
                                startActivity(intent);
                                finish();
                            }
                        });

                    }
                }
            });
            postResponseAsyncTaskCheckVersion.setExceptionHandler(new ExceptionHandler() {
                @Override
                public void handleException(Exception e) {
                }
            });
            postResponseAsyncTaskCheckVersion.setEachExceptionsHandler(new EachExceptionsHandler() {
                @Override
                public void handleIOException(IOException e) {

                }

                @Override
                public void handleMalformedURLException(MalformedURLException e) {

                }

                @Override
                public void handleProtocolException(ProtocolException e) {
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.network_error),Toast.LENGTH_LONG).show();
                }

                @Override
                public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {

                }
            });
            postResponseAsyncTaskCheckVersion.setLoadingMessage(getResources().getString(R.string.loading));
            postResponseAsyncTaskCheckVersion.execute("http://mbns.esy.es/checkversion.php");
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }
    }

    class Task extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog = new ProgressDialog(Welcome.this);

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Đang tải ...");
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
            xuLyTuDongSignIn();
        }
    }
}
