package com.wimex.mbns.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wimex.mbns.NguoiMua.GioHang;
import com.wimex.mbns.Model.Auth;
import com.wimex.mbns.Model.SanPham;
import com.wimex.mbns.R;
import com.wimex.mbns.XuLyData.DifferenceTime;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.TimerTask;

/**
 * Created by admin on 3/15/2017.
 */

public class GianHangNSXAdapterError extends ArrayAdapter<SanPham> {
    Context context;
    int resource;
    List<SanPham> objects;

    public GianHangNSXAdapterError(@NonNull Context context, @LayoutRes int resource, @NonNull List<SanPham> objects) {
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
            holder.txtGianHangNSXDonGia = (TextView) convertView.findViewById(R.id.txtGianHangNSXDonGia);
            holder.txtGianHangNSXTenSP = (TextView) convertView.findViewById(R.id.txtGianHangNSXTenSP);
            holder.txtGianHangNSXGiaoHang = (TextView) convertView.findViewById(R.id.txtGianHangNSXGiaoHang);
            holder.btnGianHangNSXDatHang = (Button) convertView.findViewById(R.id.btnGianHangNSXDatHang);
            holder.imgGianHangNSX = (ImageView) convertView.findViewById(R.id.imgGianHangNSX);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SanPham sanPham = objects.get(position);
        final SanPham finalSanPham = sanPham;
        holder.txtGianHangNSXDonGia.setText((int) sanPham.getPrice() + " đ");
        holder.txtGianHangNSXTenSP.setText(sanPham.getTen());
        holder.txtGianHangNSXGiaoHang.setText("00:00:00");
        // final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
        Picasso.with(context).load(sanPham.getAnh()).into(holder.imgGianHangNSX);
        final ViewHolder finalHolder = holder;

        /////////////// check còn hay hết thời gian đặt hàng

      /*  String date = sanPham.getDate();
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
        Date now = new Date(year, month, day, hour, minute, second);*/
        int i =sanPham.getStatus();
        if (position==0){
          if (i==0){
              holder.txtGianHangNSXGiaoHang.setText("Hết giờ");
              holder.btnGianHangNSXDatHang.setEnabled(false);
              holder.btnGianHangNSXDatHang.setText("GianHangNSXAdapter");

          }else{
              /*holder.btnGianHangNSXDatHang.setEnabled(true);
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
              timerTask.run();*/
          }
      }else{
          if ((sanPham.getStatus()+"").equals("0")){
              holder.txtGianHangNSXGiaoHang.setText("Hết giờ");
              holder.btnGianHangNSXDatHang.setEnabled(false);
              holder.btnGianHangNSXDatHang.setText("GianHangNSXAdapter");
              Toast.makeText(context,""+sanPham.getTen()+"--"+sanPham.getDate()+sanPham.getStatus(),Toast.LENGTH_SHORT).show();
          }else{
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
      }
       /* if ((sanPham.getStatus()+"").equals("0")){
            holder.txtGianHangNSXGiaoHang.setText("Hết giờ");
            holder.btnGianHangNSXDatHang.setEnabled(false);
            holder.btnGianHangNSXDatHang.setText("GianHangNSXAdapter");
            Toast.makeText(context,""+sanPham.getTen()+"--"+sanPham.getDate()+sanPham.getStatus(),Toast.LENGTH_SHORT).show();
        }else{
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
        }*/
        holder.txtGianHangNSXGiaoHang.setText("status : "+sanPham.getStatus()+"date : "
                +sanPham.getDate()+"status final: "+sanPham.getStatus()+"date : "+sanPham.getDate());

        /////////////////

        holder.btnGianHangNSXDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int trung = 0;
                for (SanPham sanPham1 : Auth.gioHang) {
                    if (sanPham1.getId() == finalSanPham.getId()) {
                        trung = 1;
                        break;
                    }
                }
                if (trung == 0) {
                    Auth.gioHang.add(finalSanPham);
                    Auth.updateGiaTriGioHang();
                    Intent mMoveToGioHang = new Intent(context, GioHang.class);
                    context.startActivity(mMoveToGioHang);
                } else {
                    Toast.makeText(context, "Bạn đã đặt hàng sản phẩm này", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView txtGianHangNSXTenSP;
        TextView txtGianHangNSXDonGia;
        TextView txtGianHangNSXGiaoHang;
        Button btnGianHangNSXDatHang;
        ImageView imgGianHangNSX;
    }
}
