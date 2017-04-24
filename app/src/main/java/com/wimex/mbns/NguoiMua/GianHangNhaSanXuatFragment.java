package com.wimex.mbns.NguoiMua;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
import com.wimex.mbns.Adapter.GianHangNSXAdapter;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by admin on 3/13/2017.
 */

public class GianHangNhaSanXuatFragment extends Fragment {
    ListView lvGianHangNhaSanXuat;
 //   GianHangNSXAdapterError gianHangNSXAdapter;
    GianHangNSXAdapter ha;
    ArrayList<SanPham> listSanPham;
    private Handler timerHandler = new Handler();
    String nsxId = "0";

    // cứ sau 1 giây sẽ cập nhật thời gian
  /*  private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            for (SanPham sanPham : listSanPham) {
                String date = sanPham.getDate();
                String a[] = date.split("-");
                Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
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
                Date now = new Date(year, month, day, hour, minute, second);
                String s = DifferenceTime.getDifferenceHours(now, two) + ":"
                        + DifferenceTime.getDifferenceMinutes(now, two) + ":"
                        + DifferenceTime.getDifferenceSeconds(now, two) + "-date-";
                sanPham.setRemainTime(s);
                gianHangNSXAdapter.notifyDataSetChanged();
                //       timerHandler.postDelayed(this, 60000); //run every minute
            }
            timerHandler.postDelayed(this, 1000);
        }
    };*/
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gian_hang_nha_san_xuat_fragment, container, false);
        nsxId = getArguments().getString("nsxId");
        lvGianHangNhaSanXuat = (ListView) rootView.findViewById(R.id.lvGianHangNhaSanXuat);
        listSanPham = new ArrayList<>();
       // gianHangNSXAdapter = new GianHangNSXAdapterError(getContext(), R.layout.list_item_gian_hang_nha_san_xuat, listSanPham);
        ha = new GianHangNSXAdapter(getContext(), listSanPham);
        //lvGianHangNhaSanXuat.setAdapter(gianHangNSXAdapter);
        lvGianHangNhaSanXuat.setAdapter(ha);
        //  timerHandler.postDelayed(timerRunnable, 1000);

        //run every minute

        //    timerHandler.post(timerRunnable);
        //  getActivity().runOnUiThread(timerRunnable);


        ;
  /*      Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (SanPham sanPham:listSanPham){
                    String date = sanPham.getDate();
                    String a[] = date.split("-");
                    Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
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
                    Date now = new Date(year, month, day, hour, minute, second);
                    String s=DifferenceTime.getDifferenceHours(now, two) + ":"
                            + DifferenceTime.getDifferenceMinutes(now, two) + ":"
                            + DifferenceTime.getDifferenceSeconds(now, two) + "-date-";
                    sanPham.setRemainTime(s);
                    gianHangNSXAdapter.notifyDataSetChanged();

                }
            }
        };
       // timer.schedule(timerTask,1000);
        timer.scheduleAtFixedRate(timerTask,0,1000);
*/
        /*Thread thread2= new Thread(timerRunnable);
        thread2.start();*/

       /* Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new Task().execute(Auth.domain + "/sanphamjson.php");
            }
        });
        thread.start();*/
        HashMap<String, String> postData = new HashMap<>();
        postData.put("id", nsxId);
        PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(getContext(), postData, true, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                try {
                    JSONArray root = new JSONArray(s);
                    ArrayList<String> listSanPhamId = new ArrayList<>();
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject mSanPham = root.getJSONObject(i);
                            if (listSanPhamId.size() == 0) {
                                SanPham sanPham = new SanPham();
                                sanPham.setAnh(Auth.domain + "/images/" + mSanPham.getString("logoId"));
                                sanPham.setPrice(Float.parseFloat(mSanPham.getString("giaSanPham")));
                                sanPham.setTen(mSanPham.getString("tenSanPham"));
                                sanPham.setId(mSanPham.getInt("sanPhamId"));
                                sanPham.setDate(mSanPham.getString("date"));
                                sanPham.setStatus(mSanPham.getInt("status"));
                                sanPham.setSoLuongConLai(Float.parseFloat(mSanPham.getString("sanLuong")));
                                sanPham.setDonViTieuChuan(mSanPham.getString("donViTieuChuan"));
                                listSanPhamId.add(mSanPham.getString("sanPhamId"));
                                listSanPham.add(sanPham);
                                ha.notifyDataSetChanged();
                            } else {
                                int trung = 0;
                                for (String sanPhamId : listSanPhamId) {
                                    if (sanPhamId.equals(mSanPham.getString("sanPhamId"))) {
                                        trung++;
                                        break;
                                    }
                                }
                                if (trung == 0) {
                                    SanPham sanPham = new SanPham();
                                    sanPham.setAnh(Auth.domain + "/images/" + mSanPham.getString("logoId"));
                                    sanPham.setPrice(Float.parseFloat(mSanPham.getString("giaSanPham")));
                                    sanPham.setTen(mSanPham.getString("tenSanPham"));
                                    sanPham.setId(mSanPham.getInt("sanPhamId"));
                                    sanPham.setDate(mSanPham.getString("date"));
                                    sanPham.setStatus(mSanPham.getInt("status"));
                                    sanPham.setSoLuongConLai(Float.parseFloat(mSanPham.getString("sanLuong")));
                                    sanPham.setDonViTieuChuan(mSanPham.getString("donViTieuChuan"));
                                    listSanPham.add(sanPham);
                                    listSanPhamId.add(mSanPham.getString("sanPhamId"));
                                    ha.notifyDataSetChanged();
                                }
                            }
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
        postResponseAsyncTask.execute(Auth.domain + "/gianhangnhasanxuatjson.php");
        return rootView;
    }

    /*class Task extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return JsonToString.docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                ArrayList<String> listSanPhamId = new ArrayList<>();
                for (int i = 0; i < root.length(); i++) {
                    JSONObject mSanPham = root.getJSONObject(i);
                    if (mSanPham.getString("KhachHangId").equals(nsxId)) {
                        if (listSanPhamId.size() == 0) {
                            SanPham sanPham = new SanPham();
                            sanPham.setAnh(Auth.domain + "/images/" + mSanPham.getString("logoId"));
                            sanPham.setPrice(Float.parseFloat(mSanPham.getString("giaSanPham")));
                            sanPham.setTen(mSanPham.getString("tenSanPham"));
                            sanPham.setId(mSanPham.getInt("sanPhamId"));
                            sanPham.setDate(mSanPham.getString("date"));
                            sanPham.setStatus(mSanPham.getInt("status"));
                            sanPham.setDonViTieuChuan(mSanPham.getString("donViTieuChuan"));
                            listSanPhamId.add(mSanPham.getString("sanPhamId"));
                            listSanPham.add(sanPham);
                            ha.notifyDataSetChanged();
                        } else {
                            int trung = 0;
                            for (String sanPhamId : listSanPhamId) {
                                if (sanPhamId.equals(mSanPham.getString("sanPhamId"))) {
                                    trung++;
                                    break;
                                }
                            }
                            if (trung == 0) {
                                SanPham sanPham = new SanPham();
                                sanPham.setAnh(Auth.domain + "/images/" + mSanPham.getString("logoId"));
                                sanPham.setPrice(Float.parseFloat(mSanPham.getString("giaSanPham")));
                                sanPham.setTen(mSanPham.getString("tenSanPham"));
                                sanPham.setId(mSanPham.getInt("sanPhamId"));
                                sanPham.setDate(mSanPham.getString("date"));
                                sanPham.setStatus(mSanPham.getInt("status"));
                                sanPham.setSoLuongConLai(Float.parseFloat(mSanPham.getString("sanLuong")));
                                sanPham.setDonViTieuChuan(mSanPham.getString("donViTieuChuan"));
                                listSanPham.add(sanPham);
                                listSanPhamId.add(mSanPham.getString("sanPhamId"));
                                ha.notifyDataSetChanged();
                            }
                        }
                        // gianHangNSXAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            */
}


