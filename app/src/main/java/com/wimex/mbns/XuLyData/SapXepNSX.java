package com.wimex.mbns.XuLyData;

import com.wimex.mbns.Model.NhaSanXuat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by admin on 4/1/2017.
 */

public class SapXepNSX {

    public static ArrayList<NhaSanXuat> sosanh(ArrayList<NhaSanXuat> listNSX,int type) {
        switch (type) {
            case 1:
                Collections.sort(listNSX, new Comparator<NhaSanXuat>() {
                    @Override
                    public int compare(NhaSanXuat o1, NhaSanXuat o2) {
                        if (o1.getName().compareToIgnoreCase(o2.getName()) > 0) {
                            return 1;
                        } else if (o1.getName().compareToIgnoreCase(o2.getName()) == 0) {
                            return 0;
                        } else {
                            return -1;
                        }
                    }
                });
                return listNSX;

            case 2:
                Collections.sort(listNSX, new Comparator<NhaSanXuat>() {
                    @Override
                    public int compare(NhaSanXuat o1, NhaSanXuat o2) {
                        if (Float.parseFloat(o1.getVote()) > Float.parseFloat(o2.getVote())) {
                            return -1;
                        } else if (Float.parseFloat(o1.getVote()) < Float.parseFloat(o2.getVote())) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
                return listNSX;
            case 3:
                Collections.sort(listNSX, new Comparator<NhaSanXuat>() {
                    @Override
                    public int compare(NhaSanXuat o1, NhaSanXuat o2) {
                        if (Integer.parseInt(o1.getSoSanPham()) > Integer.parseInt(o2.getSoSanPham())) {
                            return -1;
                        } else if (Integer.parseInt(o1.getSoSanPham()) == Integer.parseInt(o2.getSoSanPham())) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }
                });
                return listNSX;

            default:
                return listNSX;
        }
    }

}
