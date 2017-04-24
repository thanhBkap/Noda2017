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
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.Model.DanhGia;
import com.wimex.mbns.R;

import java.util.List;

/**
 * Created by admin on 3/29/2017.
 */

public class DanhGiaAdapter extends ArrayAdapter<DanhGia> {
    Context context;
    int resource;
    List<DanhGia> objects;

    public DanhGiaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DanhGia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder holder;
        DanhGia mDanhGia = new DanhGia();
        mDanhGia = objects.get(position);
        final DanhGia finalMDanhGia = mDanhGia;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, null);
            holder.imgVote = (ImageView) convertView.findViewById(R.id.imgVote);
            holder.txtVoteTenSanPham = (TextView) convertView.findViewById(R.id.txtVoteTenSanPham);
            holder.txtVoteTenNSX = (TextView) convertView.findViewById(R.id.txtVoteTenNSX);
            holder.ratinngVote = (RatingBar) convertView.findViewById(R.id.ratinngVote);
            holder.ratinngVote.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //finalMDanhGia.setPoint(rating+"");
                   // objects.get(position).setPoint(rating+"");
                    objects.get((Integer) ratingBar.getTag()).setPoint(rating+"");
                    //notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
            convertView.setTag(R.id.ratinngVote, holder.ratinngVote);
            convertView.setTag(R.id.imgVote, holder.imgVote);
            convertView.setTag(R.id.txtVoteTenSanPham, holder.txtVoteTenSanPham);
            convertView.setTag(R.id.txtVoteTenNSX, holder.txtVoteTenNSX);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(mDanhGia.getAnh()).into(holder.imgVote);
        holder.txtVoteTenSanPham.setText(mDanhGia.getTenSanPham());
        holder.txtVoteTenNSX.setText(mDanhGia.getTenNSX());
        //holder.ratinngVote.setNumStars(5);
        holder.ratinngVote.setTag(position);
        holder.ratinngVote.setRating(Float.parseFloat(objects.get(position).getPoint()));
       // holder.ratinngVote.setTag(Float.parseFloat(mDanhGia.getPoint()));
       // holder.ratinngVote.setRating((Float) holder.ratinngVote.getTag());
        // holder.ratinngVote.setOnRatingBarChangeListener(context);
       /* holder.ratinngVote.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                finalMDanhGia.setPoint(rating+"");
                objects.get(position).setPoint(rating+"");
                //notifyDataSetChanged();
            }
        });*/
        return convertView;
    }

    static class ViewHolder {
        ImageView imgVote;
        TextView txtVoteTenSanPham;
        TextView txtVoteTenNSX;
        RatingBar ratinngVote;
    }

}
