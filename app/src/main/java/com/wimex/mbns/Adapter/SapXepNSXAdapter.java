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
import com.wimex.mbns.Model.NhaSanXuat;
import com.wimex.mbns.R;

import java.util.List;

/**
 * Created by admin on 3/15/2017.
 */

public class SapXepNSXAdapter extends ArrayAdapter<NhaSanXuat> {
    Context context;
    int resource;
    List<NhaSanXuat> objects;

    public SapXepNSXAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<NhaSanXuat> objects) {
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
            holder.imgAvatarNSX = (ImageView) convertView.findViewById(R.id.imgAvatarNSX);
            holder.txtSapXepNSXTen = (TextView) convertView.findViewById(R.id.txtSapXepNSXTen);
            holder.txtSapXepNSXDanhMuc = (TextView) convertView.findViewById(R.id.txtSapXepNSXDanhMuc);
            holder.txtSapXepNSXSoSanPham = (TextView) convertView.findViewById(R.id.txtSapXepNSXSoSanPham);
            holder.txtSapXepNSXDiemSo = (TextView) convertView.findViewById(R.id.txtSapXepNSXDiemSo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        NhaSanXuat nhaSanXuat = objects.get(position);
        Picasso.with(context).load(nhaSanXuat.getAnh()).into(holder.imgAvatarNSX);
        holder.txtSapXepNSXTen.setText(nhaSanXuat.getName());
        StringBuilder stringBuilder = new StringBuilder();
      /*  for (String s : nhaSanXuat.getDanhmuc()) {
            stringBuilder.append(s);
            stringBuilder.append(", ");
        }*/
        for (int i = 0; i < nhaSanXuat.getDanhmuc().size(); i++) {
            String s = nhaSanXuat.getDanhmuc().get(i);
            if (i == (nhaSanXuat.getDanhmuc().size() - 1)) {
                stringBuilder.append(s);
            } else{
                stringBuilder.append(s);
                stringBuilder.append(", ");
            }
        }
        // stringBuilder.append("...");
        holder.txtSapXepNSXDanhMuc.setText(stringBuilder.toString());
        holder.txtSapXepNSXSoSanPham.setText(nhaSanXuat.getSoSanPham());
        holder.txtSapXepNSXDiemSo.setText(nhaSanXuat.getVote());
        return convertView;
    }

    static class ViewHolder {
        ImageView imgAvatarNSX;
        TextView txtSapXepNSXTen;
        TextView txtSapXepNSXDanhMuc;
        TextView txtSapXepNSXSoSanPham;
        TextView txtSapXepNSXDiemSo;
    }
}
