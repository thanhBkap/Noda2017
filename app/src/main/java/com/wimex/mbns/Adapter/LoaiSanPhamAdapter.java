package com.wimex.mbns.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.Model.LoaiSanPham;
import com.wimex.mbns.R;

import java.util.List;

import static com.wimex.mbns.R.id.txtTenLoaiSanPham;
import static com.wimex.mbns.R.id.txtchitiet_LSP;

/**
 * Created by admin on 1/19/2017.
 */

public class LoaiSanPhamAdapter extends ArrayAdapter<LoaiSanPham>{
    Activity context;
    int resource;
    List<LoaiSanPham> objects;
    public LoaiSanPhamAdapter(Activity context, int resource, List<LoaiSanPham> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.grid_item2,null);
            holder = new ViewHolder();
            holder.imgAnhLoaiSanPham = (ImageView) convertView.findViewById(R.id.imgAnhLoaiSanPham);
            holder.txtTenLoaiSanPham = (TextView) convertView.findViewById(txtTenLoaiSanPham);
            holder.txtchitiet_LSP = (TextView) convertView.findViewById(txtchitiet_LSP);
            holder.txtSoLuongLoaiSanPham = (TextView) convertView.findViewById(R.id.txtSoLuongLoaiSanPham);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        LoaiSanPham sp = objects.get(position);
        Picasso.with(context).load(sp.getHinhLoaiSanPham()).fit().into(holder.imgAnhLoaiSanPham);
        //imgAnhSanPham.setImageResource(sp.getAnh());
        holder.txtTenLoaiSanPham.setText(sp.getTenLoaiSanPham()+"");
        holder.txtchitiet_LSP.setText(sp.getChiTiet());
        holder.txtSoLuongLoaiSanPham.setText(" ("+sp.getSoLuong()+")");
        return convertView;
    }
    static class ViewHolder{
        private ImageView imgAnhLoaiSanPham;
        private TextView txtTenLoaiSanPham;
        private TextView txtchitiet_LSP;
        private TextView txtSoLuongLoaiSanPham;
    }
}
