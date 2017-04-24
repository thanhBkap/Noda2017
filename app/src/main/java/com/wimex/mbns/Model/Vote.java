package com.wimex.mbns.Model;

/**
 * Created by admin on 3/15/2017.
 */

public class Vote {
    String id;
    String nsxId;
    String nguoiMusId;
    String point;

    public Vote() {
    }

    public Vote(String id, String nsxId, String nguoiMusId, String point) {
        this.id = id;
        this.nsxId = nsxId;
        this.nguoiMusId = nguoiMusId;
        this.point = point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNsxId() {
        return nsxId;
    }

    public void setNsxId(String nsxId) {
        this.nsxId = nsxId;
    }

    public String getNguoiMusId() {
        return nguoiMusId;
    }

    public void setNguoiMusId(String nguoiMusId) {
        this.nguoiMusId = nguoiMusId;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
