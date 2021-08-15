package com.kali_corporation.skethcamplus.Utils.hel.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.kali_corporation.skethcamplus.Utils.StringUtils;
import com.kali_corporation.skethcamplus.Utils.gad.Base64Util;
import com.kali_corporation.skethcamplus.Utils.hel.al.RandomUtil;

public class SPUtils {
    public static final String keyA = "1912905E6740137F";
    public static final String keyB = "691AF112D41636C5";
    public static final String keyC = "E0AA493356A22D83";
    public static final String keyD = "appInfo";
    private static Context context;
    private SPUtils() {
    }

    private static class SPUtilsBuilder {
        private static final SPUtils SP_UTILS = new SPUtils();
    }

    public static SPUtils getInstance(Context con) {
        context = con;
        return SPUtilsBuilder.SP_UTILS;
    }

    public void saveAppInfo(String a, String b,String c) {
        /*
         * SharedPreferences将用户的数据存储到该包下的shared_prefs/appInfo.xml文件中，
         * 并且设置该文件的读取方式为私有，即只有该软件自身可以访问该文件
         */
        SharedPreferences appInfo = context.getSharedPreferences(keyD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = appInfo.edit();//获取SP的编辑器
        if (!StringUtils.isEmptyStr(false,a)){
            editor.putString(keyA, a);
        }
        if (!StringUtils.isEmptyStr(false,b)){
            editor.putString(keyB, b);
        }
        if (!StringUtils.isEmptyStr(false,c)){
            editor.putString(keyC, c);
        }

        //切记最后要使用commit或者apply方法将数据写入文件
        editor.apply();
    }

    public SharedPreferences getAppInfo() {
        SharedPreferences appInfo = context.getSharedPreferences(keyD, Context.MODE_PRIVATE);
        return appInfo;
    }

    public void clearAppInfo() {
//        SharedPreferences appInfo = context.getSharedPreferences(keyD, Context.MODE_PRIVATE);
//        appInfo.edit().clear().apply();
        SharedPreferences appInfo = context.getSharedPreferences(keyD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = appInfo.edit();//获取SP的编辑器

        editor.putString("231dbae0", "");
        editor.putString("241fb8eb", "");

        //切记最后要使用commit或者apply方法将数据写入文件
        editor.apply();
    }

    public void saveDefaultPic(String a, String b) {//wangzhi

        SharedPreferences appInfo = context.getSharedPreferences(keyD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = appInfo.edit();//获取SP的编辑器

        editor.putString("defaultPic", a+b);

        //切记最后要使用commit或者apply方法将数据写入文件
        editor.apply();
    }

    public void saveTemp(String key, String data) {//wangzhi

        SharedPreferences appInfo = context.getSharedPreferences(keyD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = appInfo.edit();//获取SP的编辑器

        editor.putString(key,data);

        //切记最后要使用commit或者apply方法将数据写入文件
        editor.apply();
    }

    public void saveLevel(int data) {//wangzhi

        SharedPreferences appInfo = context.getSharedPreferences(keyD, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = appInfo.edit();//获取SP的编辑器

        editor.putInt("level",data);

        //切记最后要使用commit或者apply方法将数据写入文件
        editor.apply();
    }

    //luoji ref.equal("a11") else

    public static String check(Context context){
        String re = "hello";
        int level = SPUtils.getInstance(context).getAppInfo().getInt("level",0);
        if (level == 0){
            String ref = SPUtils.getInstance(context).getAppInfo().getString(StringUtils.enKeys(3),"");
            if (ref.equals("")){
                re = re+ RandomUtil.randomInt(8);
            }else if(ref.contains("a11")){
                re = RandomUtil.randomAuStr(3)+RandomUtil.randomMixStr(5);
            }else if(ref.contains("firstopenapp")){
                re = ""+RandomUtil.randomInt(5);
            }else if(ref.contains("somethingwrong")){
                re = "test";
            } else {
                String g = SPUtils.getInstance(context).getAppInfo().getString(Base64Util.endata("fromgl"),"");
                if (!StringUtils.isEmptyStr(false,g) && g.contains(ref)){
                    re = RandomUtil.randomAuStr(4)+RandomUtil.randomMixStr(9);
                }else {
                    re = re+RandomUtil.randomInt(7);
                }
            }
        }else {
            re = RandomUtil.randomAuStr(1)+ RandomUtil.randomInt(8);
        }

        return re;
    }


}

