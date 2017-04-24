package com.wimex.mbns.Model;

/**
 * Created by admin on 1/20/2017.
 */

public class DonHang {
    String tenSanPham;
    String tenNhaSanXuat;
    String ngay;
    int hinhAnh;
    int trangThaiHinhAnh;
    String trangThaiChu;
    int soLuong;
    float giaTriDonHang;
    float giaMotSanPham;


    public DonHang(String tenSanPham,
                   String tenNhaSanXuat,
                   String ngay,
                   int hinhAnh,
                   int trangThaiHinhAnh,
                   String trangThaiChu,
                   int soLuong,
                   float giaTriDonHang,
                   float giaMotSanPham) {
        this.tenSanPham = tenSanPham;
        this.tenNhaSanXuat = tenNhaSanXuat;
        this.ngay = ngay;
        this.hinhAnh = hinhAnh;
        this.trangThaiHinhAnh = trangThaiHinhAnh;
        this.trangThaiChu = trangThaiChu;
        this.soLuong = soLuong;
        this.giaTriDonHang = giaTriDonHang;
        this.giaMotSanPham = giaMotSanPham;
    }

    public DonHang() {
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getTenNhaSanXuat() {
        return tenNhaSanXuat;
    }

    public void setTenNhaSanXuat(String tenNhaSanXuat) {
        this.tenNhaSanXuat = tenNhaSanXuat;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(int hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTrangThaiChu() {
        return trangThaiChu;
    }

    public void setTrangThaiChu(String trangThaiChu) {
        this.trangThaiChu = trangThaiChu;
    }

    public int getTrangThaiHinhAnh() {
        return trangThaiHinhAnh;
    }

    public void setTrangThaiHinhAnh(int trangThaiHinhAnh) {
        this.trangThaiHinhAnh = trangThaiHinhAnh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getGiaTriDonHang() {
        return giaTriDonHang;
    }

    public void setGiaTriDonHang(float giaTriDonHang) {
        this.giaTriDonHang = giaTriDonHang;
    }

    public float getGiaMotSanPham() {
        return giaMotSanPham;
    }

    public void setGiaMotSanPham(float giaMotSanPham) {
        this.giaMotSanPham = giaMotSanPham;
    }
}
