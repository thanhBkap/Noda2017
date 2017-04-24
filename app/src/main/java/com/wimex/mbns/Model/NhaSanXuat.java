package com.wimex.mbns.Model;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by admin on 3/15/2017.
 */

public class NhaSanXuat implements Comparable{
    String id;
    String anh;
    String name;
    ArrayList<String> danhmuc;
    String soSanPham;
    String vote;

    public NhaSanXuat() {
        danhmuc=new ArrayList<>();
        vote = "0.0";
        soSanPham="0";
    }

    public NhaSanXuat(String id, String anh, String name, ArrayList<String> danhmuc, String soSanPham, String vote) {
        this.id = id;
        this.anh = anh;
        this.name = name;
        this.danhmuc = danhmuc;
        this.soSanPham = soSanPham;
        this.vote = vote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(ArrayList<String> danhmuc) {
        this.danhmuc = danhmuc;
    }

    public String getSoSanPham() {
        return soSanPham;
    }

    public void setSoSanPham(String soSanPham) {
        this.soSanPham = soSanPham;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
