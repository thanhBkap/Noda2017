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
import com.wimex.mbns.Model.DanhMuc;
import com.wimex.mbns.R;

import java.util.List;

/**
 * Created by admin on 3/22/2017.
 */

public class DanhMucSanPhamNSXAdapter extends ArrayAdapter<DanhMuc> {
    Context context;
    int resource;
    List<DanhMuc> objects;

    public DanhMucSanPhamNSXAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhMuc> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
            holder.imgDanhMucSanPhamNSXAnh = (ImageView) convertView.findViewById(R.id.imgDanhMucSanPhamNSXAnh);
            holder.txtDanhMucSanPhamNSXChiTiet = (TextView) convertView.findViewById(R.id.txtDanhMucSanPhamNSXChiTiet);
            holder.txtDanhMucSanPhamNSXTen = (TextView) convertView.findViewById(R.id.txtDanhMucSanPhamNSXTen);
            holder.txtDanhMucSanPhamNSXSoLuong = (TextView) convertView.findViewById(R.id.txtDanhMucSanPhamNSXSoLuong);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DanhMuc danhMuc = objects.get(position);
        holder.txtDanhMucSanPhamNSXChiTiet.setText(danhMuc.getVidu());
        holder.txtDanhMucSanPhamNSXTen.setText(danhMuc.getTen());
        holder.txtDanhMucSanPhamNSXSoLuong.setText(" ("+danhMuc.getSoluong()+") ");
        Picasso.with(context).load(danhMuc.getAnh()).into(holder.imgDanhMucSanPhamNSXAnh);
        return convertView;
    }

    class ViewHolder {
        TextView txtDanhMucSanPhamNSXChiTiet;
        TextView txtDanhMucSanPhamNSXSoLuong;
        TextView txtDanhMucSanPhamNSXTen;
        ImageView imgDanhMucSanPhamNSXAnh;
    }

}
