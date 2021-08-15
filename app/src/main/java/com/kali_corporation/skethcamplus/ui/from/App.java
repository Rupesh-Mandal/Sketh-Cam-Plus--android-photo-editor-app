package com.kali_corporation.skethcamplus.ui.from;

import android.app.Application;

import com.facebook.applinks.AppLinkData;
import com.kali_corporation.skethcamplus.Utils.hel.sp.SPUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AppLinkData.fetchDeferredAppLinkData(getApplicationContext(),
                new AppLinkData.CompletionHandler() {
                    @Override
                    public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
//                        Log.e("TAG", "onDeferredAppLinkDataFetched: ");
                        if (appLinkData!=null){
                            String host = appLinkData.getTargetUri().getHost();
                            if(host!=null) {
//                                Log.e("TAG", "onDeferredAppLinkDataFetched: "+host);
                                SPUtils.getInstance(getApplicationContext()).saveTemp("ZnJvbWds",host);
                            }
                        }
                    }
                }
        );

//        SPUtils.getInstance(getApplicationContext()).saveTemp("ZnJvbWds","ookkoo");
    }
}
