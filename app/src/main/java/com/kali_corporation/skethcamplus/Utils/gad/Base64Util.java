package com.kali_corporation.skethcamplus.Utils.gad;

import android.util.Base64;

public class Base64Util {
    public static String endata(String deData){
        String en = Base64.encodeToString(deData.getBytes(), Base64.DEFAULT);
        return en.replaceAll("\n","");
    }

    public static String dedata(String enData){
        return new String(Base64.decode(enData.getBytes(), Base64.DEFAULT));
    }
}
