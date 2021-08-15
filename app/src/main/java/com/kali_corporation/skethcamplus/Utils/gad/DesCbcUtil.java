package com.kali_corporation.skethcamplus.Utils.gad;

import android.util.Base64;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesCbcUtil {
    public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public static String encodeValue(String key, String data){
        try {
            return encode(key, data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encode(String key, byte[] data) throws Exception {
        try {
//            byte[] ivbyte = {1, 2, 3, 4, 5, 6, 7, 8};
            byte[] ivbyte = "01020304".getBytes();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
// key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            IvParameterSpec iv = new IvParameterSpec(ivbyte);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            byte[] bytes = cipher.doFinal(data);
            String res = Base64.encodeToString(bytes, Base64.DEFAULT);
            return res.replaceAll("\n","");


        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static byte[] decode(String key, byte[] data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
// key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            byte[] ivbyte = "01020304".getBytes();
            IvParameterSpec iv = new IvParameterSpec(ivbyte);
            AlgorithmParameterSpec paramSpec = iv;
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public static String decodeValue(String key, String data) {
        byte[] datas;
        String value = null;
        try {

            datas = decode(key, Base64.decode(data.getBytes(), Base64.DEFAULT));

            value = new String(datas);
        } catch (Exception e) {
            value = "";
        }
        return value;
    }
}
