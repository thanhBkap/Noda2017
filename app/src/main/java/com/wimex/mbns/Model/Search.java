package com.wimex.mbns.Model;

/**
 * Created by admin on 4/18/2017.
 */

public class Search {
    String id;
    String tenSanPham;

    public Search() {
    }

    public Search(String id, String tenSanPham) {
        this.id = id;
        this.tenSanPham = tenSanPham;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
}
