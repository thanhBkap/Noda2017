package com.wimex.mbns.Model;

import java.io.Serializable;

/**
 * Created by admin on 1/19/2017.
 */

public class SanPham implements Serializable {
    String anh;
    int id;
    String ten;
    float price;
    float soLuong;
    String provinceid;
    DiaChi diaChi;
    String date;
    int status;
    String remainTime;
    long time;
    String dateGiaoHang;
    String donViTieuChuan;
    float soLuongConLai;


    public SanPham() {
        this.soLuong = 1.0f;
        this.date = "2000-1-1";
    }

    public SanPham(String anh, int id, String ten, float price, float soLuong, String provinceid, DiaChi diaChi, String date, int status, String remainTime, long time, String dateGiaoHang, String donViTieuChuan, float soLuongConLai) {
        this.anh = anh;
        this.id = id;
        this.ten = ten;
        this.price = price;
        this.soLuong = soLuong;
        this.provinceid = provinceid;
        this.diaChi = diaChi;
        this.date = date;
        this.status = status;
        this.remainTime = remainTime;
        this.time = time;
        this.dateGiaoHang = dateGiaoHang;
        this.donViTieuChuan = donViTieuChuan;
        this.soLuongConLai = soLuongConLai;
    }

    public String getDonViTieuChuan() {
        return donViTieuChuan;
    }

    public void setDonViTieuChuan(String donViTieuChuan) {
        this.donViTieuChuan = donViTieuChuan;
    }

    public float getSoLuongConLai() {
        return soLuongConLai;
    }

    public void setSoLuongConLai(float soLuongConLai) {
        this.soLuongConLai = soLuongConLai;
    }

    public String getDateGiaoHang() {
        return dateGiaoHang;
    }

    public void setDateGiaoHang(String dateGiaoHang) {
        this.dateGiaoHang = dateGiaoHang;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(String remainTime) {
        this.remainTime = remainTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DiaChi getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(DiaChi diaChi) {
        this.diaChi = diaChi;
    }


    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public float getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(float soLuong) {
        this.soLuong = soLuong;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
