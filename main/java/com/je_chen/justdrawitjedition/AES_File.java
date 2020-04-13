/*
 * Copyright (c) 2018. JE-Chen
 */

package com.je_chen.justdrawitjedition;


import android.util.Base64;
import java.nio.charset.Charset;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES_File {


    // 加密
    public static String encrypt256(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            return null;
        }
        // 判斷是否為 256bit 　Key
        if (sKey.length() != 32) {
            return null;
        }
        //用UTF-8編碼取得金鑰字串 轉 byte[]後的值
        byte[] raw = sKey.getBytes(Charset.defaultCharset());
        //創建金鑰 AES256
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

        Cipher cipher = Cipher.getInstance("AES");//算法//模式//補碼方式

        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);//加密模式//傳入金鑰
        //回傳加密完畢的內容
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(Charset.defaultCharset()));
        //使用Base64轉碼，並起到2次加密的效果。
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    // 解密
    public static String decrypt256(String sSrc, String sKey) throws Exception {
        try {
            if (sKey == null) {
                return null;
            }

            if (sKey.length() != 32) {
                return null;
            }

            byte[] raw = sKey.getBytes(Charset.defaultCharset());

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // "AES/ECB/PKCS5Padding"解密不能用於Android 4.3以上：
            //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, skeySpec);

            byte[] encrypted1 = Base64.decode(sSrc.getBytes(Charset.defaultCharset()), Base64.DEFAULT);

            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, Charset.defaultCharset());
                return originalString;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.toString());
            return null;
        }
    }
}
