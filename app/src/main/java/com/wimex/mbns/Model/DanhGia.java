package com.wimex.mbns.Model;

/**
 * Created by admin on 3/29/2017.
 */

public class DanhGia {
    String anh;
    String tenSanPham;
    String tenNSX;
    String point;
String id;
    public DanhGia() {

    }

    public DanhGia(String anh, String tenSanPham, String tenNSX, String point, String id) {
        this.anh = anh;
        this.tenSanPham = tenSanPham;
        this.tenNSX = tenNSX;
        this.point = point;
        this.id = id;
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

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getTenNSX() {
        return tenNSX;
    }

    public void setTenNSX(String tenNSX) {
        this.tenNSX = tenNSX;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
