package com.wimex.mbns.NguoiMua;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.SanPhamAdapter;
import com.wimex.mbns.MenuToolbar;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DifferenceTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

public class SanPhamActivity extends MenuToolbar {
    public static String DANH_MUC_ID;
    GridView gridViewSanPham;
   // String idDanhMucSanPham;
    ArrayList<com.wimex.mbns.Model.SanPham> listSanPham;
    SanPhamAdapter sanPhamAdapter;
    Date now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_san_pham2);
        super.onCreate(savedInstanceState);
        addControls();
        addEvents();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Toast.makeText(SanPhamActivity.this,"oncreate sp",Toast.LENGTH_SHORT).show();
    }

    private void addControls() {
        //Toast.makeText(SanPhamActivity.this,""+ Auth.khachHangId,Toast.LENGTH_SHORT).show();
        gridViewSanPham = (GridView) findViewById(R.id.gridViewSanPham);
        Intent i = getIntent();
       // idDanhMucSanPham = i.getStringExtra("id");
        if (i.hasExtra("id")){
            DANH_MUC_ID = i.getStringExtra("id");
        }
        //  Toast.makeText(SanPhamActivity.this,""+idDanhMucSanPham,Toast.LENGTH_SHORT).show();
        listSanPham = new ArrayList<com.wimex.mbns.Model.SanPham>();
        sanPhamAdapter = new SanPhamAdapter(SanPhamActivity.this, listSanPham);
        gridViewSanPham.setNumColumns(2);
        gridViewSanPham.setAdapter(sanPhamAdapter);
        /////////
        now = getTimeNow();
        //////////////////////////
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //    new Task().execute("http://mbns.esy.es/sanphamjson.php");
            }
        });*/
        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", DANH_MUC_ID);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(this, postData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                //       Toast.makeText(SanPhamActivity.this, "id= "+idDanhMucSanPham+s, Toast.LENGTH_LONG).show();
                //txtTaoGianHangDonGia.setText(s);
                try {
                    JSONArray root = new JSONArray(s);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject mSanPham = root.getJSONObject(i);
                        com.wimex.mbns.Model.SanPham mSanPham2 = new com.wimex.mbns.Model.SanPham();
                        mSanPham2.setId(mSanPham.getInt("SanPhamId"));
                        mSanPham2.setAnh(Auth.domain+"/images/" + mSanPham.getString("logoId"));
                        mSanPham2.setTen(mSanPham.getString("tenSanPham"));
                        mSanPham2.setPrice(Float.parseFloat(mSanPham.getString("giaSanPham")));
                        mSanPham2.setDate(mSanPham.getString("ngayThuHoach"));
                        String date = mSanPham2.getDate();
                        String a[] = date.split("-");
                        Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);

                        if (DifferenceTime.getDifferenceMilliSeconds(two, now) < 0) {
                            mSanPham2.setStatus(0);
                            if (Float.parseFloat(mSanPham.getString("sanLuong")) > 0) {

                             //   Toast.makeText(SanPhamActivity.this, mSanPham.getString("tenSanPham")+"--"+Float.parseFloat(mSanPham.getString("sanLuong")), Toast.LENGTH_LONG).show();
                                mSanPham2.setStatus(0);
                            } else {
                                // hết hàng - hết số lượng đặt hàng
                                mSanPham2.setStatus(1);
                            }
                        }else{
                            //hết hàng - hết thời gian đặt hàng
                            mSanPham2.setStatus(1);
                        }
                        listSanPham.add(mSanPham2);
                        sanPhamAdapter.notifyDataSetChanged();
                        //Toast.makeText(SanPhamActivity.this, mSanPham.getString("tenSanPham")+"--"+mSanPham.getString("sanLuong"), Toast.LENGTH_LONG).show();
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
        postResponseAsyncTask.execute(Auth.domain+"/sanphamjson2.php");

    }

    private Date getTimeNow() {
        Date current_time;
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calTZ = new GregorianCalendar(tz);
        calTZ.setTimeInMillis(new Date().getTime());
// tạo một biến calendar khác để set time
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
        ca.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
        ca.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
        ca.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
        ca.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
// in ra thoi gian cua timezone vietnam
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        int minute = ca.get(Calendar.MINUTE);
        int second = ca.get(Calendar.SECOND);
        current_time = new Date(year, month, day, hour, minute, second);
        return current_time;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(SanPhamActivity.this, TrangChu.class);
        startActivity(i);
    }*/

    @Override
    protected void onResume() {
        //  Toast.makeText(SanPhamActivity.this,"onresume sp",Toast.LENGTH_SHORT).show();
        super.onResume();
        //addControls();
        // addEvents();
    }

    private void addEvents() {
        gridViewSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Intent mMoveToChiTietSanPham = new Intent(SanPhamActivity.this,ChiTietSanPham.class);
                Intent mMoveToChiTietSanPham = new Intent(SanPhamActivity.this, SanPhamChiTietActivity.class);
                SanPham sanPham = new SanPham();
                sanPham = listSanPham.get(position);
                mMoveToChiTietSanPham.putExtra("id", sanPham.getId());
                mMoveToChiTietSanPham.putExtra("id2", DANH_MUC_ID);
                onPause();
                startActivity(mMoveToChiTietSanPham);
            }
        });
    }

/*    class Task extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray root = new JSONArray(s);
                for (int i = 0; i < root.length(); i++) {
                    JSONObject mSanPham = root.getJSONObject(i);
                    com.wimex.mbns.Model.SanPham mSanPham2 = new com.wimex.mbns.Model.SanPham();
                    if (mSanPham.getString("loaiSanPham").equals(idDanhMucSanPham)) {
                        mSanPham2.setId(mSanPham.getInt("sanPhamId"));
                        mSanPham2.setAnh("http://mbns.esy.es/images/" + mSanPham.getString("logoId"));
                        mSanPham2.setTen(mSanPham.getString("tenSanPham"));
                        mSanPham2.setPrice(Float.parseFloat(mSanPham.getString("giaSanPham")));
                        listSanPham.add(mSanPham2);
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/
}
