package com.wimex.mbns.Model;

/**
 * Created by admin on 2/14/2017.
 */

public class TaiKhoan {
    private String tenDangNhap;
    private String matKhau;
    private String khachHangId;
    private String loaiKhachHangId;
    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public TaiKhoan(String tenDangNhap, String matKhau, String khachHangId, String loaiKhachHangId) {
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.khachHangId = khachHangId;
        this.loaiKhachHangId = loaiKhachHangId;
    }

    public String getLoaiKhachHangId() {
        return loaiKhachHangId;
    }

    public void setLoaiKhachHangId(String loaiKhachHangId) {
        this.loaiKhachHangId = loaiKhachHangId;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(String khachHangId) {
        this.khachHangId = khachHangId;
    }



    public TaiKhoan() {

    }
}
