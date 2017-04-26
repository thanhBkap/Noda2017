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

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.EachExceptionsHandler;
import com.kosalgeek.genasync12.PostResponseAsyncTask;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;

import static com.android.volley.Request.Method.HEAD;

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
        sapXepNSXAdapter = new SapXepNSXAdapter(getContext(), R.layout.list_item_sap_xep_nha_san_xuat2, listNSX);
        lvSapXepTheoTenNSX.setAdapter(sapXepNSXAdapter);
        if (Auth.checked.equals("1")) {
            listNSX = Auth.sapXepNSXTheoTenList;
            SapXepNSX.sosanh(listNSX, 2);
            sapXepNSXAdapter.notifyDataSetChanged();
        } else if (Auth.checked.equals("0")) {
            PostResponseAsyncTask postResponseAsyncTask = new PostResponseAsyncTask(getContext(), true, new AsyncResponse() {
                @Override
                public void processFinish(String s) {
                    JSONArray root = null;
                    try {
                        root = new JSONArray(s);
                        for (int i = 0; i < root.length(); i++) {
                            JSONObject nsx = root.getJSONObject(i);
                            NhaSanXuat nhaSanXuat = new NhaSanXuat();
                            nhaSanXuat.setAnh(Auth.domain + "/images/avarta/" + nsx.getString("image"));
                            nhaSanXuat.setName(nsx.getString("name"));
                            nhaSanXuat.setId(nsx.getString("id"));
                            nhaSanXuat.setSoSanPham(nsx.getString("product_nums"));
                            nhaSanXuat.setVote(nsx.getString("point"));
                            listNSX.add(nhaSanXuat);
                            SapXepNSX.sosanh(listNSX, 2);
                            sapXepNSXAdapter.notifyDataSetChanged();
                        }
                        Auth.sapXepNSXTheoTenList = listNSX;

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
            postResponseAsyncTask.execute(Auth.domain + "/danhsachnhasanxuatjson.php");

            if (Auth.checked.equals("1")) {
                listNSX = Auth.sapXepNSXTheoTenList;
                SapXepNSX.sosanh(listNSX, 2);
            }
            lvSapXepTheoTenNSX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    xuLyClickNSX(position);
                }
            });
        }
        return view;
    }
    private void xuLyClickNSX(int pos) {
        Intent mMoveToNSX = new Intent(getActivity(), NhaSanXuatActivity.class);
        NhaSanXuat nhaSanXuat = listNSX.get(pos);
        mMoveToNSX.putExtra("NSXId", nhaSanXuat.getId());
        startActivity(mMoveToNSX);
        //   getActivity().finish();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}


