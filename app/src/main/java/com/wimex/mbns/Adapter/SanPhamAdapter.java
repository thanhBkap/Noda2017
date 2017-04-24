package com.wimex.mbns.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;

import java.util.List;

/**
 * Created by admin on 2/22/2017.
 */

public class SanPhamAdapter extends BaseAdapter {
    Context context;
    List<SanPham> objects;

    public SanPhamAdapter(Context context, List<SanPham> list) {
        this.context=context;
        this.objects=list;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.grid_item_san_pham2,null);
            viewHolder.imgAnhSanPham= (ImageView) convertView.findViewById(R.id.imgAnhSanPham);
            viewHolder.imgSanPhamActivityHetHang= (ImageView) convertView.findViewById(R.id.imgSanPhamActivityHetHang);
            viewHolder.txtGiaSanPham= (TextView) convertView.findViewById(R.id.txtGiaSanPham);
            viewHolder.txtTenSanPham= (TextView) convertView.findViewById(R.id.txtTenSanPham);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(objects.get(position).getAnh()).into(viewHolder.imgAnhSanPham);
        viewHolder.txtTenSanPham.setText(objects.get(position).getTen());
        viewHolder.txtGiaSanPham.setText(DinhDangSo.fixNumnerToString((int)objects.get(position).getPrice())+" đ");
        if (objects.get(position).getStatus()==1){
            viewHolder.imgSanPhamActivityHetHang.setVisibility(View.VISIBLE);
        }else{
            viewHolder.imgSanPhamActivityHetHang.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    static class ViewHolder {
        ImageView imgAnhSanPham,imgSanPhamActivityHetHang;
        TextView txtTenSanPham, txtGiaSanPham;
    }
}
