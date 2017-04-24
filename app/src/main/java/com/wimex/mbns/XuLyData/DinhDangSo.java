package com.wimex.mbns.XuLyData;

/**
 * Created by admin on 3/10/2017.
 */

public class DinhDangSo {
    public static String fixNumnerToString(int number) {
        String num = "";
        String result = "";
        int turn = 1;
        num = String.valueOf(number);
        for (int i = (num.length() - 1); i >= 0; i--) {
            if ((turn % 3 == 0) && (turn != num.length())) {
                char c = num.charAt(i);
                result = "." + c + result;
            } else {
                char c = num.charAt(i);
                result = c + result;
            }
            turn++;
        }
        return result;
    }

    public static int fixStringToNumber(String number) {
        return Integer.parseInt(number.replace(".", ""));
    }

    // không tác dụng nếu biến nhận có dạng double
    public static float lamTronsothapphan(int soThuMay, float soCanLamTron) {
        float result = 0.0f;
        double tmp;
        tmp = Math.pow(10, soThuMay);
        result = (float) ((Math.round(soCanLamTron * tmp)) / tmp);
        return result;
    }
}
