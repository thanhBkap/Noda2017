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
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;

import java.util.List;

/**
 * Created by admin on 3/23/2017
 */

public class NhomSanPhamNSXAdapter extends ArrayAdapter<SanPham> {
    Context context;
    int resource;
    List<SanPham> objects;

    public NhomSanPhamNSXAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
            holder.txtNhomSanPhamNSXDonGia = (TextView) convertView.findViewById(R.id.txtNhomSanPhamNSXDonGia);
            holder.txtNhomSanPhamNSXTenSP = (TextView) convertView.findViewById(R.id.txtNhomSanPhamNSXTenSP);
            holder.txtNhomSanPhamNSXGiaoHang = (TextView) convertView.findViewById(R.id.txtNhomSanPhamNSXNgayThuHoach);
            holder.imgNhomSanPhamNSX = (ImageView) convertView.findViewById(R.id.imgNhomSanPhamNSX);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = objects.get(position);
        final SanPham finalSanPham = sanPham;
        holder.txtNhomSanPhamNSXDonGia.setText(DinhDangSo.fixNumnerToString( (int)sanPham.getPrice()) + " đ");
        holder.txtNhomSanPhamNSXTenSP.setText(sanPham.getTen());
        holder.txtNhomSanPhamNSXGiaoHang.setText(sanPham.getDate());
        Picasso.with(context).load(sanPham.getAnh()).into(holder.imgNhomSanPhamNSX);
        return convertView;
    }

    static class ViewHolder {
        TextView txtNhomSanPhamNSXTenSP;
        TextView txtNhomSanPhamNSXDonGia;
        TextView txtNhomSanPhamNSXGiaoHang;
        ImageView imgNhomSanPhamNSX;
    }

}
