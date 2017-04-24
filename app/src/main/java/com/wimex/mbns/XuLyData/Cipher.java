package com.wimex.mbns.XuLyData;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2/17/2017.
 */

public class Cipher {
    public static String encryptMD5(String str){
        String encryptedStr="";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] encryptedArr=messageDigest.digest(str.getBytes());
            BigInteger bigInteger = new BigInteger(encryptedArr);
            encryptedStr = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptedStr;
    }

    public Cipher() {
    }
}
