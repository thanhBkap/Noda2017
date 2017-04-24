package com.wimex.mbns.Model;

/**
 * Created by admin on 2/24/2017.
 */

public class Ward {
    String wardId;
    String districtId;
    String type;
    String name;

    public Ward() {
    }

    public Ward(String wardId, String districtId, String type, String name) {
        this.wardId = wardId;
        this.districtId = districtId;
        this.type = type;
        this.name = name;
    }

    public String getWardId() {
        return wardId;
    }

    public void setWardId(String wardId) {
        this.wardId = wardId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
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
