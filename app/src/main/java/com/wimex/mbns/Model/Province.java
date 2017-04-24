package com.wimex.mbns.Model;

/**
 * Created by admin on 2/24/2017.
 */

public class Province {
    String provinceId;
    String type;
    String name;

    public Province(String provinceId, String type, String name) {
        this.provinceId = provinceId;
        this.type = type;
        this.name = name;
    }

    public Province() {
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
