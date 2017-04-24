package com.wimex.mbns.Model;

/**
 * Created by admin on 2/22/2017.
 */

public class LoaiSanPham {
    String id;
    String tenLoaiSanPham;
    String hinhLoaiSanPham;
    String chiTiet;
    String loaiSanPhamGocId;
    String realPosition;
    String soLuong;

    public LoaiSanPham(String id, String tenLoaiSanPham, String hinhLoaiSanPham, String chiTiet, String loaiSanPhamGocId, String realPosition, String soLuong) {
        this.id = id;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.hinhLoaiSanPham = hinhLoaiSanPham;
        this.chiTiet = chiTiet;
        this.loaiSanPhamGocId = loaiSanPhamGocId;
        this.realPosition = realPosition;
        this.soLuong = soLuong;
    }

    public LoaiSanPham() {
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getRealPosition() {
        return realPosition;
    }

    public void setRealPosition(String realPosition) {
        this.realPosition = realPosition;
    }

    public String getLoaiSanPhamGocId() {
        return loaiSanPhamGocId;
    }

    public void setLoaiSanPhamGocId(String loaiSanPhamGocId) {
        this.loaiSanPhamGocId = loaiSanPhamGocId;
    }

    public String getChiTiet() {
        return chiTiet;
    }

    public void setChiTiet(String chiTiet) {
        this.chiTiet = chiTiet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getHinhLoaiSanPham() {
        return hinhLoaiSanPham;
    }

    public void setHinhLoaiSanPham(String hinhLoaiSanPham) {
        this.hinhLoaiSanPham = hinhLoaiSanPham;
    }
}
