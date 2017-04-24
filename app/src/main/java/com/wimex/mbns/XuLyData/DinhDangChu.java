package com.wimex.mbns.XuLyData;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by admin on 4/5/2017.
 */

public class DinhDangChu {
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }
}
