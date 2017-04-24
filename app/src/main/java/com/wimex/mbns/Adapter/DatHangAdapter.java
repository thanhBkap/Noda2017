package com.wimex.mbns.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.XuLyData.DinhDangSo;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;

import java.util.List;

/**
 * Created by admin on 3/8/2017.
 */

public class DatHangAdapter extends ArrayAdapter<SanPham> {
    Context context;
    int resource;
    List<SanPham> objects;
    public DatHangAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resource,null);
            holder.imgAnh= (ImageView) convertView.findViewById(R.id.imgAnhDatHang);
            holder.txtGia= (TextView) convertView.findViewById(R.id.txtDatHangGiaTien);
            holder.txtSoLuong= (TextView) convertView.findViewById(R.id.txtDatHangSoLuong);
            holder.txtTen= (TextView) convertView.findViewById(R.id.txtDatHangTenSanPham);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        SanPham mSanPham = new SanPham();
        mSanPham=objects.get(position);
        Picasso.with(context).load(mSanPham.getAnh()).into(holder.imgAnh);
        holder.txtGia.setText(DinhDangSo.fixNumnerToString((int)(mSanPham.getPrice()*Float.parseFloat(mSanPham.getDonViTieuChuan())))+" đ");
        holder.txtTen.setText(mSanPham.getTen()+"");
        holder.txtSoLuong.setText("Số lượng : "+mSanPham.getDonViTieuChuan()+" kg");
        return convertView;
    }
    static class ViewHolder{
        ImageView imgAnh;
        TextView txtTen;
        TextView txtGia;
        TextView txtSoLuong;
    }
}
