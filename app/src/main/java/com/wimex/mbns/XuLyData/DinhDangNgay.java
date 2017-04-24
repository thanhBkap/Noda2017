package com.wimex.mbns.XuLyData;

/**
 * Created by admin on 4/6/2017.
 */

public class DinhDangNgay {
    public static String convertDate(String date){
        String mangDate[];
        mangDate=date.split("-");
        String fixefDate=""+mangDate[2]+" - "+mangDate[1]+" - "+mangDate[0];
        return fixefDate;
    }
}
