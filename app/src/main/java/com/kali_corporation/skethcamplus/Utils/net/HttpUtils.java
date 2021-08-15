package com.kali_corporation.skethcamplus.Utils.net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.kali_corporation.skethcamplus.Utils.gad.DesCbcUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    private static final String TAG = HttpUtils.class.getSimpleName();

    public static String get(final String strUrl) {

        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");

            int response = conn.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream stream = conn.getInputStream();
                return dealResponseResult(stream,"azxpz0x7r3n4zs81");

            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doDelete(String urlStr,String params){
        try{
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("DELETE");

            //获得一个输出流，向服务器写入数据
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(params.getBytes());

            int response = conn.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream stream = conn.getInputStream();
                return dealResponseResult(stream,"azxpz0x7r3n4zs81");

            }else{
                Log.d(TAG,"<<<<<response="+response);
                return null;
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }



    public static String post(final String strUrl, String params) {

        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");

            //设置请求体的类型是文本类型
//            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Type","application/json");
            //设置请求体的长度
            conn.setRequestProperty("Content-Length",String.valueOf(params.getBytes().length));

            //获得一个输出流，向服务器写入数据
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(params.getBytes());

            int response = conn.getResponseCode();
            if(response == HttpURLConnection.HTTP_OK) {
                InputStream stream = conn.getInputStream();
                return dealResponseResult(stream,"azxpz0x7r3n4zs81");

            }else{
                Log.d(TAG,"<<<<<response="+response);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void put(final String strUrl, String params, Handler completionHandler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = "";
                try{
                    URL url = new URL(strUrl);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setReadTimeout(5000);
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                    //获得一个输出流，向服务器写入数据
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(params.getBytes());

                    int response = conn.getResponseCode();
                    if(response == HttpURLConnection.HTTP_OK) {
                        InputStream stream = conn.getInputStream();
                        result = dealResponseResult(stream,null);
                    }else{
                        Log.d(TAG,"<<<<<response="+response);
                    }
                    //回调主线程。
                    Message msg = completionHandler.obtainMessage();
                    msg.what = 200; //消息标识。
                    msg.obj = result; //用来提供额外对象参数。
                    completionHandler.sendMessage(msg); //发送消息。
                }catch (IOException ex) {
                    ex.printStackTrace();
                    Message msg = completionHandler.obtainMessage();
                    msg.what = 500; //消息标识。
                    msg.obj = result; //用来提供额外对象参数。
                    completionHandler.sendMessage(msg); //发送消息。
                }
            }
        }).start();
    }

    private static String dealResponseResult(InputStream stream,String key) throws IOException{
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String str = null;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        String res = buffer.toString();
        if (key==null){
            return res;
        }
        if (res.length()>0){

           return DesCbcUtil.decodeValue(key,res);
        }

        return res;
    }

}
