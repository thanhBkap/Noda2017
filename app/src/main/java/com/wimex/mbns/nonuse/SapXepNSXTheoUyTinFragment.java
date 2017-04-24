package com.wimex.mbns.nonuse;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.wimex.mbns.Adapter.SapXepNSXAdapter;
import com.wimex.mbns.Model.NhaSanXuat;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.XuLyData.JsonToString;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SapXepNSXTheoUyTinFragment extends Fragment {
    ListView lvSapXepTheoTenNSX;
    SapXepNSXAdapter sapXepNSXAdapter;
    ArrayList<NhaSanXuat> listNSX;
    ArrayList<AsyncTask> listTask;
    int khachHangId, task_num;
    int current_task_num = 0;
    private ProgressDialog progressDialog;
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
        Toast.makeText(getActivity(), "onPause-ten", Toast.LENGTH_SHORT).show();
        for (AsyncTask task : listTask) {
            task.cancel(true);
        }
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
        View view = inflater.inflate(R.layout.fragment_sap_xep_nha_san_xuat_theo_ten, container, false);
        lvSapXepTheoTenNSX = (ListView) view.findViewById(R.id.lvSapXepTheoTenNSX);
        listNSX = new ArrayList<>();
        sapXepNSXAdapter = new SapXepNSXAdapter(getContext(), R.layout.list_item_sap_xep_nha_san_xuat, listNSX);
        lvSapXepTheoTenNSX.setAdapter(sapXepNSXAdapter);

        return view;
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listTask.add(new Task().execute("http://mbns.esy.es/thongtintaikhoanjson.php"));
            }
        });
    }
/*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (!isVisibleToUser) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

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
                            nhaSanXuat.setAnh("http://mbns.esy.es/images/avarta/" + nsx.getString("AvartaId") + ".png");
                            nhaSanXuat.setName(nsx.getString("tenKhachHang"));
                            nhaSanXuat.setId(nsx.getString("khachHangId"));
                            listNSX.add(nhaSanXuat);
                            Collections.sort(listNSX, new Comparator<NhaSanXuat>() {
                                @Override
                                public int compare(NhaSanXuat o1, NhaSanXuat o2) {
                                    if (Float.parseFloat(o1.getVote()) > Float.parseFloat(o2.getVote())) {
                                        return 1;
                                    } else if (Float.parseFloat(o1.getVote()) == Float.parseFloat(o2.getVote())) {
                                        return 0;
                                    } else {
                                        return -1;
                                    }
                                }
                            });
                            sapXepNSXAdapter.notifyDataSetChanged();
                            final int finalPosition = position;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listTask.add(new Task2().execute("http://mbns.esy.es/sanphamjson.php", "" + finalPosition, nhaSanXuat.getId()));
                                    listTask.add(new Task3().execute("http://mbns.esy.es/votejson.php", "" + finalPosition, nhaSanXuat.getId()));
                                    listTask.add(new Task4().execute("http://mbns.esy.es/danhmucsanpham.php", "" + finalPosition, nhaSanXuat.getId()));
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
                progressDialog.dismiss();
            }
        }
    }

    //tính số sản phẩm
    class Task2 extends AsyncTask<String, Void, String> {
        String id;
        int position;
        private boolean running = true;

        @Override
        protected void onCancelled() {
            running = false;
            onDestroyView();
        }

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
                for (int i = 0; i < root.length(); i++) {
                    JSONObject sanPham = root.getJSONObject(i);
                    if (isCancelled()) {

                    } else {
                        if (sanPham.getString("KhachHangId").equals(id)) {
                            soLuong++;
                        }
                        listNSX.get(position).setSoSanPham(soLuong + "");
                        sapXepNSXAdapter.notifyDataSetChanged();
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //  Toast.makeText(getActivity(),""+current_task_num+"--"+task_num,Toast.LENGTH_SHORT).show();
            current_task_num++;
            if (current_task_num >= task_num) {
                progressDialog.dismiss();
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
                    if (isCancelled()) {

                    }else{
                        if (sanPham.getString("KhachHangId").equals(id)) {
                            if (listNSX.get(position).getDanhmuc().size() > 0) {
                                for (int j = 0; j < listNSX.get(position).getDanhmuc().size(); j++) {
                                    if (listNSX.get(position).getDanhmuc().get(j).equals(sanPham.getString("tenLoaiSanPham"))) {
                                    } else {
                                        listNSX.get(position).getDanhmuc().add(sanPham.getString("tenLoaiSanPham"));
                                    }
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
                progressDialog.dismiss();
            }
        }
    }
}
