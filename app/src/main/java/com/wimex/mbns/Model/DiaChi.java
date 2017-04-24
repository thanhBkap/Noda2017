package com.wimex.mbns.Model;

import java.io.Serializable;

/**
 * Created by admin on 2/23/2017.
 */

public class DiaChi implements Serializable{
    String id;
    String diachi;
    String khachHangId;
    String provinceId;
    String wardId;
    String detail;
    String districtId;

    public DiaChi(String id, String diachi, String khachHangId, String provinceId, String wardId, String detail, String districtId) {
        this.id = id;
        this.diachi = diachi;
        this.khachHangId = khachHangId;
        this.provinceId = provinceId;
        this.wardId = wardId;
        this.detail = detail;
        this.districtId = districtId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getKhachHangId() {
        return khachHangId;
    }

    public void setKhachHangId(String khachHangId) {
        this.khachHangId = khachHangId;
    }

    public DiaChi() {
    }
}
