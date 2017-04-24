package com.wimex.mbns.Model;

import java.util.ArrayList;

/**
 * Created by admin on 2/23/2017.
 */

public class Auth {
    //check trạng thái đăng nhập, no là chưa, yes là có
    public static String signInStatus = "no";
    public static String tenDangNhap = "no";
    public static String khachHangId = "no";
    public static String loaiKhachHangId = "no";
    public static String domain="http://mbns.esy.es";
    public static ArrayList <SanPham> gioHang = new ArrayList<>();
    public  static String checked ="0";
    public static ArrayList<NhaSanXuat> sapXepNSXTheoTenList = new ArrayList<>();
    public static double giaTriGioHang =0.0f;
    public static int checkToken=0;
    public static ThongTinKhachHang thongTinKhachHang =new ThongTinKhachHang();
    public static void updateGiaTriGioHang(){
        Auth.giaTriGioHang=0.0f;
        for (SanPham sanPham:Auth.gioHang){
            Auth.giaTriGioHang+=(int)(sanPham.getPrice()*Float.parseFloat(sanPham.getDonViTieuChuan()));
        }
    }
    public static int test =0;

}
