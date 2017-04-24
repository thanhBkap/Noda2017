package com.wimex.mbns.Model;

import com.wimex.mbns.XuLyData.DinhDangSo;

/**
 * Created by admin on 3/22/2017.
 */

public class GiaoDich {
    String ten;
    String date;
    String donGia;
    String soLuong;

    public GiaoDich(String ten, String date, String donGia, String soLuong) {
        this.ten = ten;
        this.date = date;
        this.donGia = donGia;
        this.soLuong = soLuong;
    }

    public GiaoDich() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDonGia() {
        return donGia;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String thanhTien(){
        return DinhDangSo.fixNumnerToString(Math.round(Integer.parseInt(this.donGia)*Float.parseFloat(this.soLuong)));
    }
}
