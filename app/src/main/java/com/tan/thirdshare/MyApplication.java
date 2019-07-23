package com.tan.thirdshare;

import android.app.Application;
import android.content.Context;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

/**
 * Created by Administrator on 2019/6/26/026.
 */

public class MyApplication extends Application{

    public static boolean openUMeng = false;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        //初始化友盟
      //  UMShareAPI.init(this,"5d30094c570df3e1850002ab");
        UMConfigure.init(this,"5d30094c570df3e1850002ab"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        UMConfigure.setLogEnabled(true);
//        PlatformConfig.setWeixin("wx5779ac01abad41ac", "e8f6c8097564dba92bf2ffce9b643bde");
//        PlatformConfig.setSinaWeibo("453985678","0c9cc03c6e6c93d315f0004d3b66263d","https://api.weibo.com/oauth2/default.html");
        PlatformConfig.setQQZone("1109585887","4t5w5jDvSqkNg7GY");
    }

    public static Context getContext(){
        return context;
    }

}
