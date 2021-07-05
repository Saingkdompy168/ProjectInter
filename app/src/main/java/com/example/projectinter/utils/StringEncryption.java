package com.example.projectinter.utils;

import android.os.Build;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringEncryption {
    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            md.update(text.getBytes(StandardCharsets.ISO_8859_1), 0, text.length());
        }
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

//    public static String SHA1(String text) throws NoSuchAlgorithmException {
//        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
//        byte[] result = mDigest.digest(text.getBytes());
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < result.length; i++) {
//            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
//        }
//        return sb.toString();
//    }
}

