package com.wimex.mbns.NguoiMua;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.squareup.picasso.Picasso;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 3/13/2017.
 */

public class GioiThieuNhaSanXuatFragment extends Fragment {
    CircleImageView imgGioiThieuNSX;
    TextView txtGioiThieuNSXThongTin;
    String nsxId = "0";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gioi_thieu_nha_san_xuat_fragment, container, false);
        nsxId = getArguments().getString("nsxId");
        imgGioiThieuNSX = (CircleImageView) rootView.findViewById(R.id.imgGioiThieuNSX);
        txtGioiThieuNSXThongTin = (TextView) rootView.findViewById(R.id.txtGioiThieuNSXThongTin);
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                   new Task().execute("http://mbns.esy.es/thongtinmotajson.php");
            }
        });
        thread.start();*/
        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", nsxId);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(getContext(), postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //  Toast.makeText(TaoGianHangActivity.this, s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                try {
                    JSONArray root = new JSONArray(s);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject taiKhoan = root.getJSONObject(i);
                        Picasso.with(getActivity()).
                                load(Auth.domain + "/images/avarta/" + taiKhoan.getString("AvartaId")).
                                into(imgGioiThieuNSX);
                        txtGioiThieuNSXThongTin.setText(taiKhoan.getString("thongTinMoTa"));
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
                Toast.makeText(getContext(), getResources().getString(R.string.network_error), Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {

            }
        });
        postResponseAsyncTask.execute(Auth.domain + "/thongtinmotajson.php");
        return rootView;
    }
/*
    class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject taiKhoan = root.getJSONObject(i);
                    if (taiKhoan.getString("khachHangId").equals(nsxId)) {
                        Picasso.with(getContext()).
                                load(Auth.domain + "/images/avarta/" + taiKhoan.getString("AvartaId")).
                                into(imgGioiThieuNSX);
                        txtGioiThieuNSXThongTin.setText(taiKhoan.getString("thongTinMoTa"));
                        break;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/

}
