package com.wimex.mbns.Model;

/**
 * Created by admin on 3/22/2017.
 */

public class DanhMuc {
    String id;
    String ten;
    String anh;
    String vidu;
    String soluong;

    public DanhMuc(String id, String ten, String anh, String vidu, String soluong) {
        this.id = id;
        this.ten = ten;
        this.anh = anh;
        this.vidu = vidu;
        this.soluong = soluong;
    }

    public DanhMuc() {
        soluong="0";
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getVidu() {
        return vidu;
    }

    public void setVidu(String vidu) {
        this.vidu = vidu;
    }
}
