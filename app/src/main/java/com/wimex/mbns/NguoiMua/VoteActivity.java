package com.wimex.mbns.NguoiMua;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.DanhGiaAdapter;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.DanhGia;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.MyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoteActivity extends Menu_CT {
    ListView lvVote;
    ArrayList<DanhGia> listDanhGia;
    DanhGiaAdapter danhGiaAdapter;
    Button btnVote;
    TextView txtVoteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vote);
        super.onCreate(savedInstanceState);
        addControls();
        addEvents();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(VoteActivity.this,TrangChu.class);
        startActivity(i);
    }

    private void addEvents() {
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VoteActivity.this,"Cập nhật đánh giá thành công\nHệ thống đang quay về trang chủ.",Toast.LENGTH_LONG).show();
                final int listSize = listDanhGia.size();
                /*if (listSize>0){
                    pushData(0);
                }*/
                final MyCommand myCommand = new MyCommand(getApplicationContext());
                for (int i = 0; i < listDanhGia.size(); i++) {
                    try {

                        String url = Auth.domain+"/votejson2.php";
                        StringRequest stringRequest;
                        final int finalI = i;
                        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("success")){
                                    if (finalI==(listDanhGia.size()-1)){
//                                        Toast.makeText(VoteActivity.this,"Cập nhật đánh giá thành công\nHệ thống đang quay về trang chủ.",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                //  Toast.makeText(TaoGianHangActivity.this,"Sự cố khi up ảnh",Toast.LENGTH_LONG).show();
                                // Toast.makeText(TaoGianHangActivity.this,"Sự cố khi up ảnh"+error.toString(),Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("id", listDanhGia.get(finalI).getId());
                                map.put("point", listDanhGia.get(finalI).getPoint());
                                return map;
                            }
                        };


                        myCommand.add(stringRequest);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        /*Toast.makeText(this,"idDanhMuc"+idDanhMuc
                        +"tenSanPham"+txtTaoGianHangTenSanPham.getText().toString()+
                "donGia"+txtTaoGianHangDonGia.getText().toString()+
                "ngayThuHoach"+txtTaoGianHangNgayThuHoach.getText().toString()+
                "soLuong"+txtTaoGianHangSoLuongBan.getText().toString()+
                "KhachHangId"+Auth.khachHangId,Toast.LENGTH_LONG).show();*/
                myCommand.execute();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            Intent iveTC = new Intent(VoteActivity.this, TrangChu.class);
                            startActivity(iveTC);
                        }
                        super.run();
                    }
                };
                thread.start();
            }
        });
    }



    private void addControls() {
        lvVote = (ListView) findViewById(R.id.lvVote);
        btnVote = (Button) findViewById(R.id.btnVote);
        txtVoteNotice = (TextView) findViewById(R.id.txtVoteNotice);
        listDanhGia = new ArrayList<>();
        danhGiaAdapter = new DanhGiaAdapter(VoteActivity.this, R.layout.list_item_vote, listDanhGia);
        lvVote.setAdapter(danhGiaAdapter);

        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", Auth.khachHangId);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
               // Toast.makeText(VoteActivity.this, s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                if (s.equals("none_result")) {
                    // Toast.makeText(VoteActivity.this,"Bạn chưa có đơn hàng nào",Toast.LENGTH_LONG).show();
                    txtVoteNotice.setVisibility(View.VISIBLE);
                } else {
                    txtVoteNotice.setVisibility(View.GONE);
                    try {
                        JSONArray root = new JSONArray(s);
                        for (int i = 0; i < root.length(); i++) {
                            JSONObject data = root.getJSONObject(i);
                            DanhGia dg = new DanhGia();
                            dg.setAnh(Auth.domain+"/images/" + data.getString("logoId"));
                            dg.setPoint(data.getString("point"));
                            dg.setId(data.getString("VoteId"));
                            dg.setTenNSX(data.getString("tenKhachHangNSX"));
                            dg.setTenSanPham(data.getString("tenSanPham"));
                            listDanhGia.add(dg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    danhGiaAdapter.notifyDataSetChanged();
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
        postResponseAsyncTask.execute(Auth.domain+"/votejson1.php");
    }
}
