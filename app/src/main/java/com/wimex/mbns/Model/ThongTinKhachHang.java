package com.wimex.mbns.Model;

/**
 * Created by admin on 3/8/2017.
 */

public class ThongTinKhachHang {
    String tenKhachHang;
    String email;
    String soDienThoai;

    public ThongTinKhachHang() {
    }

    public ThongTinKhachHang(String tenKhachHang, String email, String soDienThoai) {
        this.tenKhachHang = tenKhachHang;
        this.email = email;
        this.soDienThoai = soDienThoai;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
