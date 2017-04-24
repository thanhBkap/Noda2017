package com.wimex.mbns.NguoiMua;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wimex.mbns.Adapter.SapXepNSXAdapter;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.NhaSanXuat;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.XuLyData.JsonToString;
import com.wimex.mbns.XuLyData.SapXepNSX;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UyTinNSXFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UyTinNSXFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UyTinNSXFragment extends Fragment {
    ListView lvSapXepTheoTenNSX;
    SapXepNSXAdapter sapXepNSXAdapter;
    ArrayList<NhaSanXuat> listNSX;
    ArrayList<AsyncTask> listTask;
    int khachHangId, task_num, tab;
    int current_task_num = 0;
    private ProgressDialog progressDialog;

    public UyTinNSXFragment() {
    }
/*
    @Override
    public void onAttach(Context context) {
        Toast.makeText(getActivity(), "attach-ten", Toast.LENGTH_SHORT).show();
        super.onAttach(context);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "onViewCreated-ten", Toast.LENGTH_SHORT).show();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "onActivityCreated-ten", Toast.LENGTH_SHORT).show();

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        Toast.makeText(getActivity(), "onResume-ten", Toast.LENGTH_SHORT).show();

        super.onResume();
    }*/

    @Override
    public void onPause() {
        if (listTask.size()>0){
            for (AsyncTask task : listTask) {
                task.cancel(true);
            }
        }

       /* listNSX.clear();
        sapXepNSXAdapter.notifyDataSetChanged();*/
        super.onPause();
    }

    /* @Override
     public void onStop() {
         Toast.makeText(getActivity(), "onStop-ten", Toast.LENGTH_SHORT).show();

         super.onStop();
     }

     @Override
     public void onDestroyView() {
         Toast.makeText(getActivity(), "onDestroyView-ten", Toast.LENGTH_SHORT).show();

         super.onDestroyView();
     }

     @Override
     public void onDestroy() {
         Toast.makeText(getActivity(), "onDestroy-ten", Toast.LENGTH_SHORT).show();

         super.onDestroy();
     }

     @Override
     public void onDetach() {
         Toast.makeText(getActivity(), "onDetach-ten", Toast.LENGTH_SHORT).show();

         super.onDetach();
     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         Toast.makeText(getActivity(), "onCreate-ten", Toast.LENGTH_SHORT).show();
         savedInstanceState = null;
         super.onCreate(savedInstanceState);
     }
 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        listTask = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_sap_xep_nsxtheo_uy_tin, container, false);
        lvSapXepTheoTenNSX = (ListView) view.findViewById(R.id.lvSapXepTheoUyTinNSX);
        listNSX = new ArrayList<>();
        if (Auth.checked.equals("1")){
            listNSX=Auth.sapXepNSXTheoTenList;
            SapXepNSX.sosanh(listNSX,2);
        }
        sapXepNSXAdapter = new SapXepNSXAdapter(getContext(), R.layout.list_item_sap_xep_nha_san_xuat2, listNSX);
        lvSapXepTheoTenNSX.setAdapter(sapXepNSXAdapter);
        lvSapXepTheoTenNSX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                xuLyClickNSX(position);
            }
        });
        return view;
    }

    private void xuLyClickNSX(int pos) {
        Intent mMoveToNSX = new Intent(getActivity(), NhaSanXuatActivity.class);
        NhaSanXuat nhaSanXuat = listNSX.get(pos);
        mMoveToNSX.putExtra("NSXId", nhaSanXuat.getId());
        startActivity(mMoveToNSX);
        getActivity().finish();

    }

    @Override
    public void onStart() {
        super.onStart();
        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
            }
        });
        thread.start();*/
        if (Auth.checked.equals("0")){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listTask.add(new Task().execute(Auth.domain+"/sapxepnsxjson1.php"));
                }
            });
        }

    }

    class Task extends AsyncTask<String, Void, String> {
        private boolean running = true;

        public Task() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait a minute ...");
            progressDialog.setTitle("Loading");
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            if (running) {
                return JsonToString.docNoiDung_Tu_URL(params[0]);
            } else {
                return result;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                int position = 0;
                task_num = root.length() * 3 + 1;
                for (int i = 0; i < root.length(); i++) {
                    if (isCancelled()) {

                    } else {
                        JSONObject nsx = root.getJSONObject(i);
                        if (nsx.getString("LoaiKhachHangId").equals("1")) {
                            final NhaSanXuat nhaSanXuat = new NhaSanXuat();
                            nhaSanXuat.setAnh(Auth.domain+"/images/avarta/" + nsx.getString("AvartaId") );
                            nhaSanXuat.setName(nsx.getString("tenKhachHang"));
                            nhaSanXuat.setId(nsx.getString("KhachHangId"));
                            listNSX.add(nhaSanXuat);
                            //sosanh(listNSX);
                            /*Collections.sort(listNSX, new Comparator<NhaSanXuat>() {
                                @Override
                                public int compare(NhaSanXuat o1, NhaSanXuat o2) {
                                    if (o1.getName().compareToIgnoreCase(o2.getName()) > 0) {
                                        return 1;
                                    } else if (o1.getName().compareToIgnoreCase(o2.getName()) == 0) {
                                        return 0;
                                    } else {
                                        return -1;
                                    }
                                }
                            });*/
                            sapXepNSXAdapter.notifyDataSetChanged();
                            final int finalPosition = position;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listTask.add(new Task2().execute(Auth.domain+"/sanphamjson.php", "" + finalPosition, nhaSanXuat.getId()));
                                    listTask.add(new Task3().execute(Auth.domain+"/votejson.php", "" + finalPosition, nhaSanXuat.getId()));
                                    listTask.add(new Task4().execute(Auth.domain+"/danhmucsanpham.php", "" + finalPosition, nhaSanXuat.getId()));
                                }
                            });
                            position++;
                        } else {
                            current_task_num += 3;
                        }
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Toast.makeText(getActivity(),""+current_task_num+"--"+task_num,Toast.LENGTH_SHORT).show();
            current_task_num++;
            if (current_task_num >= task_num) {
                SapXepNSX.sosanh(listNSX,2);
                sapXepNSXAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                current_task_num=0;
                Auth.sapXepNSXTheoTenList=listNSX;
                Auth.checked="1";
                //thongbao();
            }
            /*sosanh(listNSX);
            sapXepNSXAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
            current_task_num=0;*/
        }
    }

    public void thongbao() {
        for (NhaSanXuat nhaSanXuat : listNSX) {
            Toast.makeText(getActivity(),
                    "danh mục: " + nhaSanXuat.getDanhmuc() +
                            "name" + nhaSanXuat.getName() +
                            "so luong" + nhaSanXuat.getSoSanPham() +
                            "anh" + nhaSanXuat.getAnh() +
                            "vote" + nhaSanXuat.getVote(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    //tính số sản phẩm
    class Task2 extends AsyncTask<String, Void, String> {
        String id;
        int position;
        private boolean running = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            position = Integer.parseInt(params[1]);
            id = params[2];
            String result = "";
            if (isCancelled()) {
                return result;
            } else {
                return JsonToString.docNoiDung_Tu_URL(params[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                int soLuong = 0;
                int trung = 0;
                ArrayList<String> listsanphamid = new ArrayList<>();
                for (int i = 0; i < root.length(); i++) {
                    JSONObject sanPham = root.getJSONObject(i);

                    if (isCancelled()) {

                    } else {
                      //  synchronized (sanPham) {
                            if (sanPham.getString("KhachHangId").equals(id)) {
                                    listsanphamid.add(sanPham.getString("sanPhamId"));
                                 /*else {
                                    for (String sanphamid : listsanphamid) {
                                        if (sanphamid.equals(sanPham.getString("sanPhamId"))) {
                                            trung++;
                                            break;
                                        }
                                    }
                                }
                                if (trung == 0) {
                                    soLuong++;
                                }*/
                                //  Toast.makeText(getActivity(),""+current_task_num+sanPham.getString("sanPhamId")+"--"+sanPham.getString("tenSanPham"),Toast.LENGTH_SHORT).show();
                            }
                        }
                   // }
                }

                for (int v1=0;v1<listsanphamid.size();v1++){
                    String s2=listsanphamid.get(v1);
                    for (int v2=v1+1;v2<listsanphamid.size();v2++){
                        if (s2.equals(listsanphamid.get(v2))){
                            trung=trung+1;
                            break;
                        }
                    }
                }
                soLuong=listsanphamid.size()-trung;
                listNSX.get(position).setSoSanPham(soLuong + "");
                sapXepNSXAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            current_task_num++;
            if (current_task_num >= task_num) {
                SapXepNSX.sosanh(listNSX,2);
                sapXepNSXAdapter.notifyDataSetChanged();
                // thongbao();
                current_task_num=0;
                progressDialog.dismiss();
                Auth.sapXepNSXTheoTenList=listNSX;
                Auth.checked="1";
            }
        }
    }

    /// lấy vote
    class Task3 extends AsyncTask<String, Void, String> {
        String id;
        int position;
        private boolean running = true;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            position = Integer.parseInt(params[1]);
            id = params[2];
            String result = "";
            if (isCancelled()) {
                return result;
            } else {
                return JsonToString.docNoiDung_Tu_URL(params[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);
                int soLuong = 0;
                double tongDiem = 0.0f;
                float diemVote = 0.0f;
                for (int i = 0; i < root.length(); i++) {
                    JSONObject sanPham = root.getJSONObject(i);
                    if (isCancelled()) {

                    } else {
                        if (sanPham.getString("nsxId").equals(id)) {
                            soLuong++;
                            String point = sanPham.getString("point");
                            tongDiem += (Double.parseDouble(point));
                            //listNSX.add(nhaSanXuat);
                            //Toast.makeText(getContext(), nhaSanXuat.getName(), Toast.LENGTH_SHORT).show();
                        }
                        diemVote = DinhDangSo.lamTronsothapphan(1, (float) (tongDiem / soLuong));
                        listNSX.get(position).setVote(diemVote + "");
                        sapXepNSXAdapter.notifyDataSetChanged();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //    Toast.makeText(getActivity(),""+current_task_num+"--"+task_num,Toast.LENGTH_SHORT).show();
            current_task_num++;
            if (current_task_num >= task_num) {
                SapXepNSX.sosanh(listNSX,2);
                sapXepNSXAdapter.notifyDataSetChanged();
                // thongbao();
                current_task_num=0;
                Auth.sapXepNSXTheoTenList=listNSX;
                Auth.checked="1";
                progressDialog.dismiss();
            }
        }
    }

    //lấy danh mục
    class Task4 extends AsyncTask<String, Void, String> {

        String id;
        int position;
        private boolean running = true;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //    progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            position = Integer.parseInt(params[1]);
            id = params[2];
            String result = "";
            if (isCancelled()) {
                return result;
            } else {
                return JsonToString.docNoiDung_Tu_URL(params[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONArray root = new JSONArray(s);

                for (int i = 0; i < root.length(); i++) {
                    JSONObject sanPham = root.getJSONObject(i);
                    int trung = 0;
                    if (isCancelled()) {

                    } else {
                        if (sanPham.getString("KhachHangId").equals(id)) {
                            if (listNSX.get(position).getDanhmuc().size() > 0) {
                                for (int j = 0; j < listNSX.get(position).getDanhmuc().size(); j++) {
                                    if (listNSX.get(position).getDanhmuc().get(j).equals(sanPham.getString("tenLoaiSanPham"))) {
                                        trung++;
                                        break;
                                    }
                                }
                                if (trung == 0) {
                                    listNSX.get(position).getDanhmuc().add(sanPham.getString("tenLoaiSanPham"));
                                }
                            } else {
                                listNSX.get(position).getDanhmuc().add(sanPham.getString("tenLoaiSanPham"));
                            }
                        }
                        sapXepNSXAdapter.notifyDataSetChanged();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            current_task_num++;
            if (current_task_num >= task_num) {
                SapXepNSX.sosanh(listNSX,2);
                sapXepNSXAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
                // thongbao();
                Auth.sapXepNSXTheoTenList=listNSX;
                Auth.checked="1";
                current_task_num=0;
            }
        }
    }

}
