package com.wimex.mbns.DiaChi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.DiaChi;
import com.wimex.mbns.NguoiMua.DatHangActivity;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.JsonToString;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MyPC on 2/7/2017.
 */

public class DiaChiActivity extends Menu_CT {
    Button mThemDiaChi, mTiepTucDiaChi,mXoaDiaChi;
    TextView txtDiaChiCuThongBao;
    ListView listDiaChiCu;
    ArrayAdapter<String> adapterDicChiCu;
    ArrayList<String> arrayListDiaChiCu;
    ArrayList<DiaChi> arrayListDiaChiCu2;
    String mIdDiaChiXoa="";
    //id địa chỉ đi kèm tài khoản, ko xóa dk
    String rootId="97";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.dia_chi_cu2);
        super.onCreate(savedInstanceState);
        addControls();
        addEvents();
    }

    private void addControls() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        mThemDiaChi = (Button) findViewById(R.id.btThemDiaChi);
        txtDiaChiCuThongBao= (TextView) findViewById(R.id.txtDiaChiCuThongBao);
        mTiepTucDiaChi = (Button) findViewById(R.id.btTiepTucDiaChi);
        mXoaDiaChi= (Button) findViewById(R.id.btXoaDiaChi);
        listDiaChiCu = (ListView) findViewById(R.id.listDiachiCu);
        arrayListDiaChiCu2 = new ArrayList<DiaChi>();
        arrayListDiaChiCu = new ArrayList<String>();
        //nếu chỉ sử dụng 2 tham số cho adapter thì ko sử dụng notifydatasetchanged đc
        adapterDicChiCu = new ArrayAdapter<String>(this, R.layout.list_item_dia_chi_cu, arrayListDiaChiCu);
        listDiaChiCu.setAdapter(adapterDicChiCu);

        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", Auth.khachHangId);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                rootId=s;
            //    Toast.makeText(getApplicationContext(),rootId,Toast.LENGTH_LONG).show();
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
        postResponseAsyncTask.execute(Auth.domain+"/diachijson2.php");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain+"/diachijson.php");
            }
        });
    }

    private void addEvents() {
        mThemDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyThemDiaChi();
            }
        });
        mTiepTucDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyTiepTucDiaChi();
            }
        });
        mXoaDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyXoaDiaChi();
            }
        });
    }

    private void xuLyTiepTucDiaChi() {
        int pos=listDiaChiCu.getCheckedItemPosition();
        String mDiaChi= arrayListDiaChiCu.get(pos);
        DiaChi diaChi = arrayListDiaChiCu2.get(pos);
        if (diaChi.getProvinceId().equals("01")||diaChi.getProvinceId().equals("79")){
            Intent mMoveToDatHang = new Intent(DiaChiActivity.this,DatHangActivity.class);
            mMoveToDatHang.putExtra("diachi",mDiaChi);
            mMoveToDatHang.putExtra("diachi2",diaChi);
            mMoveToDatHang.putExtra("diachi3",diaChi.getProvinceId());

            startActivity(mMoveToDatHang);
        }else{
            Toast.makeText(getApplicationContext(),"Xin lỗi, hiện tại chúng tôi chỉ hỗ trợ cho khách hàng ở khu vực hà nội và hồ chí minh",Toast.LENGTH_LONG).show();
        }

    }

    private void xuLyThemDiaChi() {
        Intent iThemDiaChi = new Intent(DiaChiActivity.this, DiaChiMoi.class);
        startActivity(iThemDiaChi);
    }

    private void xuLyXoaDiaChi() {
        mIdDiaChiXoa=arrayListDiaChiCu2.get(listDiaChiCu.getCheckedItemPosition()).getId();
        if (mIdDiaChiXoa.equals(rootId)){
            Toast.makeText(getApplicationContext(),"Không thể xóa địa chỉ nởi ở của bạn, hãy thêm địa chỉ giao hàng khác",Toast.LENGTH_LONG).show();
        }else{
            arrayListDiaChiCu2.remove(listDiaChiCu.getCheckedItemPosition());
            arrayListDiaChiCu.remove(listDiaChiCu.getCheckedItemPosition());
            //  adapterDicChiCu.remove(arrayListDiaChiCu.get(listDiaChiCu.getCheckedItemPosition()));
            adapterDicChiCu.notifyDataSetChanged();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Task2().execute(Auth.domain+"/xulydiachi.php");
                }
            });
        }

        //Toast.makeText(DiaChiActivity.this ," số thứ tự "+listDiaChiCu.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
    }

    private String makePostRequest(String url) {
        String kq = "";
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);
        // Các tham số truyền
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("xuLy", "deleteDiaChi"));
        nameValuePair.add(new BasicNameValuePair("DiaChiId",mIdDiaChiXoa));
        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

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
                    DiaChi diaChi = new DiaChi();
                    StringBuilder stringBuilder = new StringBuilder();
                    JSONObject mDiaChi = root.getJSONObject(i);
                    if(mDiaChi.getString("khachHangId").equals(Auth.khachHangId)){
                        if (mDiaChi.getString("provinceid").equals("97")){

                        }else{
                            stringBuilder.append(mDiaChi.getString("detail") + " ,");
                            stringBuilder.append(mDiaChi.getString("wardType") + " " + mDiaChi.getString("wardName") + " ,");
                            stringBuilder.append(mDiaChi.getString("districtType") + " " + mDiaChi.getString("districtName") + " ,");
                            stringBuilder.append(mDiaChi.getString("provinceType") + " " + mDiaChi.getString("provinceName"));
                            String diaChiMoi = stringBuilder.toString();
                            arrayListDiaChiCu.add(diaChiMoi);
                            diaChi.setProvinceId(mDiaChi.getString("provinceid"));
                            diaChi.setDistrictId(mDiaChi.getString("districtid"));
                            diaChi.setWardId(mDiaChi.getString("wardid"));
                            diaChi.setDetail(mDiaChi.getString("detail"));
                            diaChi.setDiachi(stringBuilder.toString());
                            diaChi.setId(mDiaChi.getString("id"));
                            diaChi.setKhachHangId(mDiaChi.getString("khachHangId"));
                            arrayListDiaChiCu2.add(diaChi);
                            adapterDicChiCu.notifyDataSetChanged();
                            listDiaChiCu.setItemChecked(0,true);
                        }
                    }
                }
                if (arrayListDiaChiCu.size()==0){
                    mTiepTucDiaChi.setEnabled(false);
                    mXoaDiaChi.setEnabled(false);
                    txtDiaChiCuThongBao.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }

    class Task2 extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
