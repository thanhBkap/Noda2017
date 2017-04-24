package com.wimex.mbns.NhaSanXuat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;
import com.wimex.mbns.MenuToolbar;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.NguoiMua.ThongTinTaiKhoanActivity;
import com.wimex.mbns.R;
import com.wimex.mbns.SignInSignUp.SigninActivity;
import com.wimex.mbns.XuLyData.MyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ThongTinMoTaActivity extends MenuToolbar {
    ImageView imgThongTinMoTa;
    Button btnThongTinMoTaTaiAnh, btnThongTinMoTaChinhSua, btnThongTinMoTaCapNhat;
    EditText txtThongTinMoTa;
    GalleryPhoto galleryPhoto;
    int GALLERY_REQUEST = 99;
    int CHANGE_PHOTO = 0;
    ProgressDialog dialog;
    ArrayList<String> listAnhString;
    int requestType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thong_tin_mo_ta);
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
        }else if (id == android.R.id.home) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        /*Toast.makeText(getBaseContext(),CHANGE_PHOTO+"",Toast.LENGTH_LONG).show();
        CHANGE_PHOTO=1;*/
        super.onResume();

    }

    private void addControls() {
        requestType = 0;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dialog = new ProgressDialog(this);
        dialog.setTitle(getResources().getString(R.string.loading));
        imgThongTinMoTa = (ImageView) findViewById(R.id.imgThongTinMoTa);
        txtThongTinMoTa = (EditText) findViewById(R.id.txtThongTinMoTa);
        btnThongTinMoTaTaiAnh = (Button) findViewById(R.id.btnThongTinMoTaTaiAnh);
        btnThongTinMoTaChinhSua = (Button) findViewById(R.id.btnThongTinMoTaChinhSua);
        btnThongTinMoTaCapNhat = (Button) findViewById(R.id.btnThongTinMoTaCapNhat);
        imgThongTinMoTa.requestFocus();
        listAnhString = new ArrayList<>();
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute("http://mbns.esy.es/thongtinmotajson.php");
            }
        });*/
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
                        JSONObject taiKhoan = root.getJSONObject(i);
                        Picasso.with(ThongTinMoTaActivity.this).
                                load(Auth.domain + "/images/avarta/" + taiKhoan.getString("AvartaId")).
                                into(imgThongTinMoTa);
                        txtThongTinMoTa.setText(taiKhoan.getString("thongTinMoTa"));
                        txtThongTinMoTa.clearFocus();
                        imgThongTinMoTa.requestFocus();
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
        postResponseAsyncTask.execute(Auth.domain + "/thongtinmotajson.php");
    }

    private void addEvents() {
        btnThongTinMoTaCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyCapNhat();
            }
        });
        btnThongTinMoTaTaiAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTaiAnh();
            }


        });
        btnThongTinMoTaChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChinhSua();
            }
        });
    }

    private void xuLyChinhSua() {
        txtThongTinMoTa.setFocusable(true);
        txtThongTinMoTa.requestFocus();
    }

    private void xuLyTaiAnh() {
        requestType=1;
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        Intent in = galleryPhoto.openGalleryIntent();
        startActivityForResult(in, GALLERY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                try {
                    //avoid pushing two images to server
                    listAnhString.clear();
                    Drawable drawable = ImageLoader.init().from(photoPath).requestSize(256, 256).getImageDrawable();
                    listAnhString.add(0, photoPath);
                    imgThongTinMoTa.setImageDrawable(drawable);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void xuLyCapNhat() {
        dialog.show();
        if (listAnhString.size() <= 0) {
            HashMap<String, String> postData = new HashMap<>();
            postData.put("thongtinmota", txtThongTinMoTa.getText().toString());
            postData.put("KhachHangId", Auth.khachHangId);
            postData.put("type", "" + requestType);
            PostResponseAsyncTask postResponseAsyncTask2 = new PostResponseAsyncTask(ThongTinMoTaActivity.this, postData,false, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    dialog.dismiss();
                    Toast.makeText(ThongTinMoTaActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    txtThongTinMoTa.clearFocus();
                }
            });
            postResponseAsyncTask2.setEachExceptionsHandler(new EachExceptionsHandler() {
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
            postResponseAsyncTask2.execute(Auth.domain + "/thongtinmotajson2.php");
        } else {
            final MyCommand myCommand = new MyCommand(getApplicationContext());
            for (int i = 0; i < listAnhString.size(); i++) {
                try {
                    Bitmap bitmap = ImageLoader.init().from(listAnhString.get(i)).requestSize(512, 512).getBitmap();
                    final String encodedBitMap = ImageBase64.encode(bitmap);
                    String url = Auth.domain + "/thongtinmotajson2.php";
                    StringRequest stringRequest;
                    stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            //Toast.makeText(ThongTinMoTaActivity.this, response, Toast.LENGTH_LONG).show();
                             Toast.makeText(ThongTinMoTaActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ThongTinMoTaActivity.this, "Sự cố khi up ảnh", Toast.LENGTH_LONG).show();
                            // Toast.makeText(ThongTinMoTaActivity.this,"Sự cố khi up ảnh"+error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("image", encodedBitMap);
                            map.put("thongtinmota", txtThongTinMoTa.getText().toString());
                            map.put("KhachHangId", Auth.khachHangId);
                            map.put("type", "" + requestType);
                            return map;
                        }
                    };
                    myCommand.add(stringRequest);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            myCommand.execute();
        }


    }
 /*   class Task extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... params) {
                return JsonToString.docNoiDung_Tu_URL(params[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONArray root = new JSONArray(s);
                    for (int i=0;i<root.length();i++){
                        JSONObject taiKhoan = root.getJSONObject(i);
                        if (taiKhoan.getString("khachHangId").equals(Auth.khachHangId)){
                            Picasso.with(ThongTinMoTaActivity.this).
                                    load("http://mbns.esy.es/images/avarta/"+taiKhoan.getString("AvartaId")+".png").
                                    into(imgThongTinMoTa);
                            txtThongTinMoTa.setText(taiKhoan.getString("thongTinMoTa"));
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
    }*/
}
