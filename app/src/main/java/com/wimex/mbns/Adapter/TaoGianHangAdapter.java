package com.wimex.mbns.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.wimex.mbns.R;

import java.util.List;

/**
 * Created by admin on 3/24/2017.
 */

public class TaoGianHangAdapter extends ArrayAdapter<Drawable> {
    Activity context;
    int resource;
    List<Drawable> objects;

    public TaoGianHangAdapter(Activity context, int resource, List<Drawable> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item_tao_gian_hang, null);
            holder.imgTaoGianHangAnhSanPham = (ImageView) convertView.findViewById(R.id.imgTaoGianHangAnhSanPham);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imgTaoGianHangAnhSanPham.setImageDrawable(objects.get(position));
        return convertView;
    }



    static class ViewHolder {
        ImageView imgTaoGianHangAnhSanPham;
    }

}
