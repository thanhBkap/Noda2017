package com.wimex.mbns.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.MenuToolbar;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.NguoiMua.GioHang;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DifferenceTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 3/16/2017.
 */

public class GianHangNSXAdapter extends BaseAdapter {
    Context context;
    List<SanPham> sanPhams;
    private List<ViewHolder> lstHolders;
    private Handler mHandler = new Handler();
    private Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (lstHolders) {
                for (ViewHolder holder : lstHolders) {
                    holder.updateTimer();
                }
            }
        }
    };

    public GianHangNSXAdapter(Context context, List<SanPham> sanPhams) {
        this.context = context;
        this.sanPhams = sanPhams;
        lstHolders = new ArrayList<>();
        startUpdateTimer();
    }

    private void startUpdateTimer() {
        Timer tmr = new Timer();
        tmr.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(updateRemainingTimeRunnable);
            }
        }, 0, 1000);
    }

    @Override
    public int getCount() {
        return sanPhams.size();
    }

    @Override
    public Object getItem(int position) {
        return sanPhams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_gian_hang_nha_san_xuat, null);
            holder.txtGianHangNSXDonGia = (TextView) convertView.findViewById(R.id.txtGianHangNSXDonGia);
            holder.txtGianHangNSXTenSP = (TextView) convertView.findViewById(R.id.txtGianHangNSXTenSP);
            holder.txtGianHangNSXGiaoHang = (TextView) convertView.findViewById(R.id.txtGianHangNSXGiaoHang);
            holder.btnGianHangNSXDatHang = (Button) convertView.findViewById(R.id.btnGianHangNSXDatHang);
            holder.imgGianHangNSX = (ImageView) convertView.findViewById(R.id.imgGianHangNSX);
            convertView.setTag(holder);
            synchronized (lstHolders) {
                lstHolders.add(holder);
            }
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SanPham sanPham = (SanPham) getItem(position);
        holder.setData(sanPham);
        final SanPham finalSanPham = sanPham;
        final ViewHolder finalHolder = holder;
        holder.txtGianHangNSXDonGia.setText((int) sanPham.getPrice() + " đ");
        holder.txtGianHangNSXTenSP.setText(sanPham.getTen());
        //finalHolder.txtGianHangNSXGiaoHang.setText("");
        holder.txtGianHangNSXGiaoHang.setText(sanPham.getRemainTime());
        holder.btnGianHangNSXDatHang.setEnabled(true);
        holder.btnGianHangNSXDatHang.setText("Đặt hàng");
        // final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        Picasso.with(context).load(sanPham.getAnh()).into(holder.imgGianHangNSX);
        int i = sanPham.getStatus();
        if (sanPham.getStatus() == 10) {
            holder.txtGianHangNSXGiaoHang.setText("Hết giờ ");
            holder.btnGianHangNSXDatHang.setEnabled(false);
        }else if (sanPham.getStatus() == 11) {
            holder.txtGianHangNSXGiaoHang.setText("Hết hàng ");
            holder.btnGianHangNSXDatHang.setEnabled(false);
        }
        holder.btnGianHangNSXDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Auth.signInStatus.equals("no")) {
                    Toast.makeText(context, "Ban muốn mua hàng ??Hãy đăng nhập", Toast.LENGTH_LONG).show();
                } else {

                    int trung = 0;
                    for (SanPham sanpham : Auth.gioHang) {
                        if (finalSanPham.getId() == sanpham.getId()) {
                            trung = 1;
                            Toast.makeText(context, "Sản phẩm bạn đặt đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    if (trung == 0) {
                        if (finalSanPham.getStatus()==10){
                            Toast.makeText(context, "rất tiếc đã hết giờ đặt hàng", Toast.LENGTH_SHORT).show();
                        }else if (finalSanPham.getStatus()==11){
                            Toast.makeText(context, "rất tiếc đã hết hàng để mua", Toast.LENGTH_SHORT).show();
                        }else{
                            Auth.gioHang.add(finalSanPham);
                            MenuToolbar.txtCartMenu.setVisibility(View.VISIBLE);
                            MenuToolbar.txtCartMenu.setText(Auth.gioHang.size() + "");
                            Intent mMoveToGioHang = new Intent(context, GioHang.class);
                            context.startActivity(mMoveToGioHang);
                            Toast.makeText(context, "thêm thành công", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

/*
        String date = finalSanPham.getDate();
        String a[] = date.split("-");
        Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar calTZ = new GregorianCalendar(tz);
        calTZ.setTimeInMillis(new Date().getTime());
// tạo một biến calendar khác để set time
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
        ca.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
        ca.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
        ca.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
        ca.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
        ca.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
        ca.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
// in ra thoi gian cua timezone vietnam
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        int minute = ca.get(Calendar.MINUTE);
        int second = ca.get(Calendar.SECOND);
        Date now = new Date(year, month, day, hour, minute, second);
        finalHolder.txtGianHangNSXGiaoHang.setText(DifferenceTime.getDifferenceHours(now, two) + ":"
        + DifferenceTime.getDifferenceMinutes(now, two) + ":"
                + DifferenceTime.getDifferenceSeconds(now, two) + "-date-" + finalSanPham.getDate());
*/


        /*if (position==0){
            Auth.test++;
            Toast.makeText(context,""+sanPham.getTen()+"--"+sanPham.getDate()+sanPham.getStatus()+"lần "+Auth.test,Toast.LENGTH_SHORT).show();

        }*/
       /* if (i == 1) {
            ///// đếm ngược

                    String date = finalSanPham.getDate();
                    String a[] = date.split("-");
                    Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
                    Calendar ca = Calendar.getInstance();
            new CountDownTimer(DifferenceTime.getDifferenceMilliSeconds(ca.getTime(), two), 1000) {
                    *//*new CountDownTimer(10000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            finalHolder.txtGianHangNSXGiaoHang.setText(millisUntilFinished/1000+"");
                        }*//*

                         @Override
                        public void onTick(long millisUntilFinished) {
                            String date = finalSanPham.getDate();
                            String a[] = date.split("-");
                            Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
                            TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                            Calendar calTZ = new GregorianCalendar(tz);
                            calTZ.setTimeInMillis(new Date().getTime());
// tạo một biến calendar khác để set time
                            Calendar ca = Calendar.getInstance();
                            ca.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
                            ca.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
                            ca.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
                            ca.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
                            ca.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
                            ca.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
                            ca.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
// in ra thoi gian cua timezone vietnam
                            int year = ca.get(Calendar.YEAR);
                            int month = ca.get(Calendar.MONTH) + 1;
                            int day = ca.get(Calendar.DAY_OF_MONTH);
                            int hour = ca.get(Calendar.HOUR_OF_DAY);
                            int minute = ca.get(Calendar.MINUTE);
                            int second = ca.get(Calendar.SECOND);
                            Date now = new Date(year, month, day, hour, minute, second);
                            *//*finalHolder.txtGianHangNSXGiaoHang.setText(DifferenceTime.getDifferenceHours(now, two) + ":"
                                    + DifferenceTime.getDifferenceMinutes(now, two) + ":"
                                    + DifferenceTime.getDifferenceSeconds(now, two) + "-date-" + finalSanPham.getDate());*//*
                            synchronized (finalHolder){
                                finalHolder.txtGianHangNSXGiaoHang.setText(""+millisUntilFinished/1000);

                            }
                           *//* if (DifferenceTime.getDifferenceMilliSeconds(now, two) <= 0) {
                               // finalHolder.txtGianHangNSXGiaoHang.setText("Hết giờ");
                                onFinish();
                            }*//*
                             if (millisUntilFinished<=0) {
                                 // finalHolder.txtGianHangNSXGiaoHang.setText("Hết giờ");
                                 onFinish();
                             }
                        }

                        @Override
                        public void onFinish() {
                            // Toast.makeText(context, "finished", Toast.LENGTH_SHORT).show();

                            finalHolder.txtGianHangNSXGiaoHang.setText("Hết giờ");
                            //   finalHolder.btnGianHangNSXDatHang.setEnabled(false);
                            // Toast.makeText(context,""+finalSanPham.getTen()+"--"+finalSanPham.getDate(),Toast.LENGTH_SHORT).show();

                        }
                    }.start();

        } else {
         //   Toast.makeText(context, "" + sanPham.getTen() + "--" + sanPham.getDate() + sanPham.getStatus() + "i=" + i, Toast.LENGTH_SHORT).show();
            holder.btnGianHangNSXDatHang.setEnabled(false);
            holder.btnGianHangNSXDatHang.setText("Hết giờ");

        }*/
        /*if (position==0){
            if (i==1){
                Toast.makeText(context,""+sanPham.getTen()+"--"+sanPham.getDate()+sanPham.getStatus()+"i="+i,Toast.LENGTH_SHORT).show();
                holder.btnGianHangNSXDatHang.setEnabled(false);
                holder.btnGianHangNSXDatHang.setText("hahaha");
            }

        }*/
       /* if (position == 0) {

            }
       *//* if ((i+"").equals("0")){
            holder.txtGianHangNSXGiaoHang.setText("Hết giờ");
            holder.btnGianHangNSXDatHang.setEnabled(false);
            holder.btnGianHangNSXDatHang.setText("hahaha");
           // Toast.makeText(context,""+sanPham.getTen()+"--"+sanPham.getDate()+sanPham.getStatus(),Toast.LENGTH_SHORT).show();
        }else{ss
            holder.btnGianHangNSXDatHang.setEnabled(true);
        }*//*
       *//* holder.txtGianHangNSXGiaoHang.setText("status : "+sanPham.getStatus()+"date : "
                +sanPham.getDate()+"status final: "+sanPham.getStatus()+"date : "+sanPham.getDate());*//*

        }else if(position>0){
            if (i == 0) {
                holder.txtGianHangNSXGiaoHang.setText("Hết giờ");
                holder.btnGianHangNSXDatHang.setEnabled(false);
                holder.btnGianHangNSXDatHang.setText("GianHangNSXAdapter");
                Toast.makeText(context, "" + sanPham.getTen() + "--" + sanPham.getDate() + sanPham.getStatus(), Toast.LENGTH_SHORT).show();
            } else {
                holder.btnGianHangNSXDatHang.setEnabled(true);
                ///// đếm ngược
                TimerTask timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        String date = finalSanPham.getDate();
                        String a[] = date.split("-");
                        Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
                        Calendar ca = Calendar.getInstance();
                        new CountDownTimer(DifferenceTime.getDifferenceMilliSeconds(ca.getTime(), two), 1000) {

                            @Override
                            public void onTick(long millisUntilFinished) {
                                String date = finalSanPham.getDate();
                                String a[] = date.split("-");
                                Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
                                TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
                                Calendar calTZ = new GregorianCalendar(tz);
                                calTZ.setTimeInMillis(new Date().getTime());
// tạo một biến calendar khác để set time
                                Calendar ca = Calendar.getInstance();
                                ca.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
                                ca.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
                                ca.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
                                ca.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
                                ca.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
                                ca.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
                                ca.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
// in ra thoi gian cua timezone vietnam
                                int year = ca.get(Calendar.YEAR);
                                int month = ca.get(Calendar.MONTH) + 1;
                                int day = ca.get(Calendar.DAY_OF_MONTH);
                                int hour = ca.get(Calendar.HOUR_OF_DAY);
                                int minute = ca.get(Calendar.MINUTE);
                                int second = ca.get(Calendar.SECOND);
                                Date now = new Date(year, month, day, hour, minute, second);
                                if (DifferenceTime.getDifferenceMilliSeconds(now,two)<=0){
                                    //finalHolder.txtGianHangNSXGiaoHang.setText("Hết giờ");
                                    onFinish();
                                }else{
                                    finalHolder.txtGianHangNSXGiaoHang.setText(DifferenceTime.getDifferenceHours(now, two) + ":"
                                            + DifferenceTime.getDifferenceMinutes(now, two) + ":"
                                            + DifferenceTime.getDifferenceSeconds(now, two)+"-date-"+finalSanPham.getDate());
                                }
                            }
                            @Override
                            public void onFinish() {
                                // Toast.makeText(context, "finished", Toast.LENGTH_SHORT).show();

                                finalHolder.txtGianHangNSXGiaoHang.setText("Hết giờ");
                                //   finalHolder.btnGianHangNSXDatHang.setEnabled(false);
                                // Toast.makeText(context,""+finalSanPham.getTen()+"--"+finalSanPham.getDate(),Toast.LENGTH_SHORT).show();

                            }
                        }.start();
                    }
                };
                timerTask.run();
            }
        }*/
        return convertView;

    }

    static class ViewHolder {
        TextView txtGianHangNSXTenSP;
        TextView txtGianHangNSXDonGia;
        TextView txtGianHangNSXGiaoHang;
        Button btnGianHangNSXDatHang;
        ImageView imgGianHangNSX;
        SanPham mSanPham;

        public void setData(SanPham sanPham) {
            mSanPham = sanPham;
        }

        public void updateTimer() {

            String date = mSanPham.getDate();
            String a[] = date.split("-");
            Date two = new Date(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]), 0, 0, 0);
            TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
            Calendar calTZ = new GregorianCalendar(tz);
            calTZ.setTimeInMillis(new Date().getTime());
// tạo một biến calendar khác để set time
            Calendar ca = Calendar.getInstance();
            ca.set(Calendar.YEAR, calTZ.get(Calendar.YEAR));
            ca.set(Calendar.MONTH, calTZ.get(Calendar.MONTH));
            ca.set(Calendar.DAY_OF_MONTH, calTZ.get(Calendar.DAY_OF_MONTH));
            ca.set(Calendar.HOUR_OF_DAY, calTZ.get(Calendar.HOUR_OF_DAY));
            ca.set(Calendar.MINUTE, calTZ.get(Calendar.MINUTE));
            ca.set(Calendar.SECOND, calTZ.get(Calendar.SECOND));
            ca.set(Calendar.MILLISECOND, calTZ.get(Calendar.MILLISECOND));
// in ra thoi gian cua timezone vietnam
            int year = ca.get(Calendar.YEAR);
            int month = ca.get(Calendar.MONTH) + 1;
            int day = ca.get(Calendar.DAY_OF_MONTH);
            int hour = ca.get(Calendar.HOUR_OF_DAY);
            int minute = ca.get(Calendar.MINUTE);
            int second = ca.get(Calendar.SECOND);
            Date now = new Date(year, month, day, hour, minute, second);
            if (DifferenceTime.getDifferenceMilliSeconds(now, two) <= 0) {
                if (mSanPham.getStatus() < 10) {
                    mSanPham.setStatus(10);
                    txtGianHangNSXGiaoHang.setText("Hết giờ");
                    btnGianHangNSXDatHang.setEnabled(false);
                }

            } else {
                if (mSanPham.getStatus()==11){

                }else{
                    if (mSanPham.getSoLuongConLai() <= 0) {
                        txtGianHangNSXGiaoHang.setText("Hết hàng");
                        mSanPham.setStatus(11);
                    } else {
                        txtGianHangNSXGiaoHang.setText(DifferenceTime.getDifferenceHours(now, two) + ":"
                                + DifferenceTime.getDifferenceMinutes(now, two) + ":"
                                + DifferenceTime.getDifferenceSeconds(now, two));
                    }
                }
            }

        }
    }
}
