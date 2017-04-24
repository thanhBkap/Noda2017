package com.wimex.mbns.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.MenuToolbar;
import com.wimex.mbns.Menu_CT;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.NguoiMua.GioHang;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DinhDangSo;

import java.util.List;

/**
 * Created by admin on 3/2/2017.
 */

public class GioHangAdapter extends ArrayAdapter<SanPham> {
    Activity context;
    int resource;
    List<SanPham> objects;
    int i,j;
    String a;

    public GioHangAdapter(Activity context, int resource, List<SanPham> objects) {
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
            convertView = inflater.inflate(R.layout.mathang_giohang2, null);
            holder.imgAnh = (ImageView) convertView.findViewById(R.id.item_Anh_GioHang);
            holder.txtGia = (TextView) convertView.findViewById(R.id.item_Gia_GioHang);
            holder.txtDonVi = (TextView) convertView.findViewById(R.id.item_DonVi);
            holder.txtSoLuong = (EditText) convertView.findViewById(R.id.txtGioHangSoLuong);
            holder.txtTen = (TextView) convertView.findViewById(R.id.item_TenSanPham_GioHang);
            holder.txtDonGia = (TextView) convertView.findViewById(R.id.txtGioHangDonGia);
            holder.btnLuaChon = (Button) convertView.findViewById(R.id.btLuaChon_GioHang);
            holder.btnMinus = (Button) convertView.findViewById(R.id.btnGioHangMinus);
            holder.btnPlus = (Button) convertView.findViewById(R.id.btnGioHangPlus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final SanPham mSanPham = objects.get(position);
        Picasso.with(context).load(mSanPham.getAnh()).into(holder.imgAnh);
        holder.txtSoLuong.setText(Float.parseFloat(mSanPham.getDonViTieuChuan()) + "");
        holder.txtDonVi.setText("Kg");
        holder.txtDonGia.setText("Đơn giá : " + DinhDangSo.fixNumnerToString((int) mSanPham.getPrice()) + " đ/kg");
        holder.txtGia.setText(DinhDangSo.fixNumnerToString(Math.round(mSanPham.getPrice() * (Float.parseFloat(holder.txtSoLuong.getText().toString())))));
        holder.txtTen.setText(mSanPham.getTen());
        float soLuongNhapVao;
        try {
            soLuongNhapVao = Float.parseFloat(holder.txtSoLuong.getText().toString());
        } catch (Exception ex) {
            soLuongNhapVao = 0.0f;
            Toast.makeText(getContext(), "Hãy nhập số chính xác", Toast.LENGTH_LONG).show();
        }
        ///event
        //GioHang.mTxtTongTienGioHang.setText(""+(int)Auth.giaTriGioHang);
        final ViewHolder finalHolder = holder;
        final float finalSoLuongNhapVao = soLuongNhapVao;
        i=j=0;
        a="";
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float dvt = 0.1f;
                float mSoLuong = (float) DinhDangSo.lamTronsothapphan(1, finalSoLuongNhapVao + dvt);
                //finalHolder.txtSoLuong.setText("" + mSoLuong);
                objects.get(position).setDonViTieuChuan(mSoLuong + "");
               /* if (objects.get(position).getSoLuong()>objects.get(position).getSoLuongConLai()){
                    objects.get(position).setSoLuong(objects.get(position).getSoLuongConLai());
                    Toast.makeText(getContext(),"Sản phẩm "+objects.get(position).getTen()+" chỉ còn "+objects.get(position).getSoLuong()+" kg",Toast.LENGTH_LONG).show();
                }*/
                finalHolder.txtGia.setText(
                        DinhDangSo.fixNumnerToString(
                                Math.round(
                                        Float.parseFloat(finalHolder.txtSoLuong.getText().toString()) * mSanPham.getPrice())));
                finalHolder.txtSoLuong.setText("" + mSoLuong);

                if (finalHolder.txtSoLuong.getText().toString().equals("")) {
                    finalHolder.txtGia.setText("0");
                    finalHolder.txtSoLuong.setText("0");
                    objects.get(position).setDonViTieuChuan("" + Float.parseFloat(finalHolder.txtSoLuong.getText().toString()));
                    Auth.updateGiaTriGioHang();
                    GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                } else {
                    float soLuongNhapVao;
                    try {
                        soLuongNhapVao = Float.parseFloat(finalHolder.txtSoLuong.getText().toString());
                    } catch (Exception ex) {
                        soLuongNhapVao = 0.0f;
                        Toast.makeText(getContext(), "Hãy nhập số chính xác", Toast.LENGTH_LONG).show();
                    }
                    objects.get(position).setDonViTieuChuan("" + soLuongNhapVao);
                    if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()
                            || Float.parseFloat(objects.get(position).getDonViTieuChuan()) <= 0) {
                        if (objects.get(position).getSoLuong() <= 0) {
                            Toast.makeText(getContext(), "Không thể mua hàng với số lượng nhở hơn hay bằng 0", Toast.LENGTH_LONG).show();
                            objects.get(position).setDonViTieuChuan("0");
                            GioHang.mTxtTongTienGioHang.setText("0");
                        } else if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()) {
                            objects.get(position).setDonViTieuChuan("" + objects.get(position).getSoLuongConLai());
                            Toast.makeText(getContext(), "Sản phẩm " + objects.get(position).getTen() + " chỉ còn " + objects.get(position).getDonViTieuChuan() + " kg", Toast.LENGTH_LONG).show();
                            finalHolder.txtSoLuong.setText(objects.get(position).getDonViTieuChuan() + "");
                        }
                        GioHang.mBtMuaGioHang.setEnabled(false);
                        notifyDataSetChanged();
                        Auth.updateGiaTriGioHang();
                        GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                    } else {
                        //finalHolder.txtSoLuong.setText(s.toString());
                        GioHang.mBtMuaGioHang.setEnabled(true);
                        notifyDataSetChanged();
                        Auth.updateGiaTriGioHang();
                        GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                    }

                    finalHolder.txtGia.setText(DinhDangSo.fixNumnerToString(
                            Math.round(mSanPham.getPrice() * (Float.parseFloat(finalHolder.txtSoLuong.getText().toString())))));
                }


            }
        });

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float dvt = 0.1f;
                float mSoLuong = (float) DinhDangSo.lamTronsothapphan(1, Float.parseFloat(finalHolder.txtSoLuong.getText().toString()) - dvt);
                if (mSoLuong >= 0) {
                    finalHolder.txtSoLuong.setText("" + mSoLuong);
                    //   Auth.giaTriGioHang -= (0.1 * mSanPham.getPrice());
                    finalHolder.txtGia.setText(DinhDangSo.fixNumnerToString(
                            Math.round(
                                    Float.parseFloat(finalHolder.txtSoLuong.getText().toString()) * mSanPham.getPrice())));
                    objects.get(position).setDonViTieuChuan(mSoLuong + "");
                   // notifyDataSetChanged();
                }

                if (finalHolder.txtSoLuong.getText().toString().equals("")) {
                    finalHolder.txtGia.setText("0");
                    finalHolder.txtSoLuong.setText("0");
                    objects.get(position).setDonViTieuChuan("" + Float.parseFloat(finalHolder.txtSoLuong.getText().toString()));
                    Auth.updateGiaTriGioHang();
                    GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                } else {
                    float soLuongNhapVao;
                    try {
                        soLuongNhapVao = Float.parseFloat(finalHolder.txtSoLuong.getText().toString());
                    } catch (Exception ex) {
                        soLuongNhapVao = 0.0f;
                        Toast.makeText(getContext(), "Hãy nhập số chính xác", Toast.LENGTH_LONG).show();
                    }
                    objects.get(position).setDonViTieuChuan("" + soLuongNhapVao);
                    if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()
                            || Float.parseFloat(objects.get(position).getDonViTieuChuan()) <= 0) {
                        if (objects.get(position).getSoLuong() <= 0) {
                            Toast.makeText(getContext(), "Không thể mua hàng với số lượng nhở hơn hay bằng 0", Toast.LENGTH_LONG).show();
                            objects.get(position).setDonViTieuChuan("0");
                            GioHang.mTxtTongTienGioHang.setText("0");
                        } else if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()) {
                            objects.get(position).setDonViTieuChuan("" + objects.get(position).getSoLuongConLai());
                            Toast.makeText(getContext(), "Sản phẩm " + objects.get(position).getTen() + " chỉ còn " + objects.get(position).getDonViTieuChuan() + " kg", Toast.LENGTH_LONG).show();
                            finalHolder.txtSoLuong.setText(objects.get(position).getDonViTieuChuan() + "");
                        }
                        GioHang.mBtMuaGioHang.setEnabled(false);
                        notifyDataSetChanged();
                        Auth.updateGiaTriGioHang();
                        GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                    } else {
                        //finalHolder.txtSoLuong.setText(s.toString());
                        GioHang.mBtMuaGioHang.setEnabled(true);
                        notifyDataSetChanged();
                        Auth.updateGiaTriGioHang();
                        GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                    }

                    finalHolder.txtGia.setText(DinhDangSo.fixNumnerToString(
                            Math.round(mSanPham.getPrice() * (Float.parseFloat(finalHolder.txtSoLuong.getText().toString())))));
                }


            }
        });

        holder.btnLuaChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.gioHang.remove(position);
                objects.remove(position);
                notifyDataSetChanged();
                Auth.updateGiaTriGioHang();
                GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                MenuToolbar.txtCartMenu.setText(Auth.gioHang.size() + "");
                Menu_CT.txtCartMenu.setText(Auth.gioHang.size() + "");
            }
        });

        holder.txtSoLuong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    v.setBackgroundColor(Color.BLUE);
                    v.requestFocus();
                    finalHolder.txtSoLuong.setFocusable(true);
                    finalHolder.txtSoLuong.requestFocusFromTouch();
                    finalHolder.txtSoLuong.requestFocus();

                    if (finalHolder.txtSoLuong.getText().toString().equals("")) {
                        finalHolder.txtGia.setText("0");
                        finalHolder.txtSoLuong.setText("0");
                        objects.get(position).setDonViTieuChuan("" + Float.parseFloat(finalHolder.txtSoLuong.getText().toString()));
                        Auth.updateGiaTriGioHang();
                        GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                    }/* else {
                        float soLuongNhapVao;
                        try {
                            soLuongNhapVao = Float.parseFloat(finalHolder.txtSoLuong.getText().toString());
                        } catch (Exception ex) {
                            soLuongNhapVao = 0.0f;
                            Toast.makeText(getContext(), "Hãy nhập số chính xác", Toast.LENGTH_LONG).show();
                        }
                        objects.get(position).setDonViTieuChuan("" + soLuongNhapVao);
                        if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()
                                || Float.parseFloat(objects.get(position).getDonViTieuChuan()) <= 0) {
                            if (objects.get(position).getSoLuong() <= 0) {
                                Toast.makeText(getContext(), "Không thể mua hàng với số lượng nhở hơn hay bằng 0", Toast.LENGTH_LONG).show();
                                objects.get(position).setDonViTieuChuan("0");
                                GioHang.mTxtTongTienGioHang.setText("0");
                            } else if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()) {
                                objects.get(position).setDonViTieuChuan("" + objects.get(position).getSoLuongConLai());
                                Toast.makeText(getContext(), "Sản phẩm " + objects.get(position).getTen() + " chỉ còn " + objects.get(position).getDonViTieuChuan() + " kg", Toast.LENGTH_LONG).show();
                                finalHolder.txtSoLuong.setText(objects.get(position).getDonViTieuChuan() + "");
                            }
                            GioHang.mBtMuaGioHang.setEnabled(false);
                            notifyDataSetChanged();
                            Auth.updateGiaTriGioHang();
                            GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                        } else {
                            //finalHolder.txtSoLuong.setText(s.toString());
                            GioHang.mBtMuaGioHang.setEnabled(true);
                            notifyDataSetChanged();
                            Auth.updateGiaTriGioHang();
                            GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                        }

                        finalHolder.txtGia.setText(DinhDangSo.fixNumnerToString(
                                Math.round(mSanPham.getPrice() * (Float.parseFloat(finalHolder.txtSoLuong.getText().toString())))));
                    }*/
                    v.requestFocus();
                    finalHolder.txtSoLuong.setFocusable(true);
                    finalHolder.txtSoLuong.requestFocusFromTouch();
                    finalHolder.txtSoLuong.requestFocus();
                }
            }
        });
        holder.txtSoLuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                a=s.toString();
                i=0;
                j++;
               // Toast.makeText(context,i+"edit"+j,Toast.LENGTH_LONG).show();


               /* if (finalHolder.txtSoLuong.getText().toString().equals("")) {
                    finalHolder.txtGia.setText("0");
                    finalHolder.txtSoLuong.setText("0");
                    objects.get(position).setDonViTieuChuan("" + Float.parseFloat(finalHolder.txtSoLuong.getText().toString()));
                    Auth.updateGiaTriGioHang();
                    GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                } else {
                    float soLuongNhapVao;
                    try {
                        soLuongNhapVao = Float.parseFloat(finalHolder.txtSoLuong.getText().toString());
                    } catch (Exception ex) {
                        soLuongNhapVao = 0.0f;
                        Toast.makeText(getContext(), "Hãy nhập số chính xác", Toast.LENGTH_LONG).show();
                    }
                    objects.get(position).setDonViTieuChuan("" + soLuongNhapVao);
                    if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()
                            || Float.parseFloat(objects.get(position).getDonViTieuChuan()) <= 0) {
                        if (objects.get(position).getSoLuong() <= 0) {
                            Toast.makeText(getContext(), "Không thể mua hàng với số lượng nhở hơn hay bằng 0", Toast.LENGTH_LONG).show();
                            objects.get(position).setDonViTieuChuan("0");
                            GioHang.mTxtTongTienGioHang.setText("0");
                        } else if (Float.parseFloat(objects.get(position).getDonViTieuChuan()) > objects.get(position).getSoLuongConLai()) {
                            objects.get(position).setDonViTieuChuan("" + objects.get(position).getSoLuongConLai());
                            Toast.makeText(getContext(), "Sản phẩm " + objects.get(position).getTen() + " chỉ còn " + objects.get(position).getDonViTieuChuan() + " kg", Toast.LENGTH_LONG).show();
                            finalHolder.txtSoLuong.setText(objects.get(position).getDonViTieuChuan() + "");
                        }
                        GioHang.mBtMuaGioHang.setEnabled(false);
                        notifyDataSetChanged();
                        Auth.updateGiaTriGioHang();
                        GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                    } else {
                        //finalHolder.txtSoLuong.setText(s.toString());
                        GioHang.mBtMuaGioHang.setEnabled(true);
                        notifyDataSetChanged();
                        Auth.updateGiaTriGioHang();
                        GioHang.mTxtTongTienGioHang.setText(DinhDangSo.fixNumnerToString((int) Auth.giaTriGioHang));
                    }

                    finalHolder.txtGia.setText(DinhDangSo.fixNumnerToString(
                            Math.round(mSanPham.getPrice() * (Float.parseFloat(finalHolder.txtSoLuong.getText().toString())))));
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
              //  finalHolder.txtSoLuong.requestFocus();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        ImageView imgAnh;
        TextView txtTen;
        TextView txtDonVi;
        TextView txtGia;
        TextView txtDonGia;
        EditText txtSoLuong;
        Button btnLuaChon, btnMinus, btnPlus;
    }
}
