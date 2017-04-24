package com.wimex.mbns.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wimex.mbns.Model.GiaoDich;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;

import java.util.List;

/**
 * Created by admin on 3/22/2017.
 */

public class LichSuAdapter extends ArrayAdapter<GiaoDich> {
    Context context;
    int resource;
    List<GiaoDich> objects;
    public LichSuAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<GiaoDich> objects) {
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
            holder.txtLichSuBanHangDonGia= (TextView) convertView.findViewById(R.id.txtLichSuBanHangDonGia);
            holder.txtLichSuBanHangTenSanPham= (TextView) convertView.findViewById(R.id.txtLichSuBanHangTenSanPham);
            holder.txtLichSuBanHangNgayThuHoach= (TextView) convertView.findViewById(R.id.txtLichSuBanHangNgayThuHoach);
            holder.txtLichSuBanHangSoLuong= (TextView) convertView.findViewById(R.id.txtLichSuBanHangSoLuong);
            holder.txtLichSuBanHangTongTienMotSanPham= (TextView) convertView.findViewById(R.id.txtLichSuBanHangTongTienMotSanPham);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        GiaoDich giaoDich = objects.get(position);
        holder.txtLichSuBanHangDonGia.setText(DinhDangSo.fixNumnerToString(Integer.parseInt(giaoDich.getDonGia()))+" đ");
        holder.txtLichSuBanHangTenSanPham.setText(giaoDich.getTen());
        holder.txtLichSuBanHangNgayThuHoach.setText(giaoDich.getDate());
        holder.txtLichSuBanHangSoLuong.setText(giaoDich.getSoLuong());
        holder.txtLichSuBanHangTongTienMotSanPham.setText(giaoDich.thanhTien()+" đ");
        return convertView;
    }
    class ViewHolder{
        TextView txtLichSuBanHangTenSanPham;
        TextView txtLichSuBanHangNgayThuHoach;
        TextView txtLichSuBanHangDonGia;
        TextView txtLichSuBanHangSoLuong;
        TextView txtLichSuBanHangTongTienMotSanPham;

    }
}
