package com.kali_corporation.skethcamplus.Utils;

import android.text.TextUtils;

import java.util.ArrayList;

public class StringUtils {
    public static boolean isEmptyStr(boolean useReg,String value){
        if (TextUtils.isEmpty(value) ) {
            return true;
        }

        if (isNullStr(value)){
            return true;
        }

        if (useReg) {
            if (isNotAvaliableStr(value)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isNullStr(String value){
        if (value.equals("null") || value.equals("Null")){
            return true;
        }
        return false;
    }

    private static boolean isNotAvaliableStr(String value){
        if (value.equals("\"\"")){
            return true;
        }
        if (value.equals("")){
            return true;
        }
        return false;
    }
    public static String compare(String old,String now){
        if (!old.equals(now)) {
            return now;
        }
        return "";
    }

    public static String enKeys(int i){//icon,subicon,name,v
        String[] keys = new String[]{"231dbae0","390bb7e76fb79e","241fb8eb","3c"};
        return keys[i];
    }

    public static String deKeys(int index){

        ArrayList arrayList = new ArrayList();
        arrayList.add("small_act");
        arrayList.add("&small_pd");
        arrayList.add("&small_ack");
        arrayList.add("&small_info");
        arrayList.add("&small_name");
        return (String)arrayList.get(index);
    }
}
