package com.wimex.mbns.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.NguoiMua.SanPhamChiTietActivity;
import com.wimex.mbns.R;

import java.util.ArrayList;

/**
 * Created by admin on 3/29/2017.
 */

public class SanPhamChiTietAdapter extends RecyclerView.Adapter<SanPhamChiTietAdapter.ViewHolder> {
    ArrayList<String> listSanPham;
    Context context;

    public SanPhamChiTietAdapter() {
    }
    public SanPhamChiTietAdapter(ArrayList<String> listSanPham, Context context) {
        this.listSanPham = listSanPham;
        this.context = context;
    }
    @Override
    public SanPhamChiTietAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.recycler_view_item_san_pham_chi_tiet,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SanPhamChiTietAdapter.ViewHolder holder, int position) {
        String mLabel;
        mLabel=listSanPham.get(position);
        Picasso.with(context).load(mLabel).into(holder.imgSanPhamChiTietItem);
    }
    public void removeItem(int position){
        listSanPham.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return listSanPham.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPhamChiTietItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imgSanPhamChiTietItem= (ImageView) itemView.findViewById(R.id.imgSanPhamChiTietItem);
            imgSanPhamChiTietItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Picasso.with(context).load(listSanPham.get(getAdapterPosition())).into(SanPhamChiTietActivity.imgSanPhamChiTiet);
                }
            });

        }
    }
}
