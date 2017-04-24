package com.wimex.mbns.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wimex.mbns.DiaChi.DiaChiMoi;
import com.wimex.mbns.R;

import java.util.ArrayList;

/**
 * Created by admin on 4/7/2017.
 */

public class DiaChiAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> list;
    ArrayList<String> list_khong_dau;
    ArrayList<String> list_main;
    ArrayList<String> tmp;

    public DiaChiAdapter(Context context, ArrayList<String> list, ArrayList<String> list_khong_dau,ArrayList<String> list_main) {
        this.context = context;
        this.list = list;
        this.list_khong_dau = list_khong_dau;
        this.list_main = list_main;
        tmp=new ArrayList<>();
        tmp=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_item_dia_chi_moi, null);
        TextView txtItemTest = (TextView) convertView.findViewById(R.id.txtItemDiaChiMoi);
        txtItemTest.setText(list.get(position));
        return convertView;
    }

    // Filter Class
    public void filter2(String charText) {
        charText = charText.toLowerCase();
        list.clear();
        DiaChiMoi.listDiaChiMoi.clear();
        if (charText.length() == 0) {
            list.addAll(list_main);
            for (String s:list_main){
                DiaChiMoi.listDiaChiMoi.add(s);
            }
        } else {
            for (int i=0;i<list_khong_dau.size();i++){
                if (list_khong_dau.get(i).toLowerCase().contains(charText)){
                    list.add(list_main.get(i));
                    DiaChiMoi.listDiaChiMoi.add(list_main.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }


}
