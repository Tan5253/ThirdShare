package com.tan.thirdshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tan.libcommon.util.ToastUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btn_share;
    Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //每次都要授权
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(MainActivity.this).setShareConfig(config);

        btn_share=findViewById(R.id.btn_share);
        btn_login=findViewById(R.id.btn_login);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ToastUtil.shortToastCenter(MainActivity.this,">>>>>>>>>");
                UMWeb web = new UMWeb("http://www.baidu.com");
                web.setTitle("测试");//标题
               // web.setThumb(new UMImage(MainActivity.this, "http://youxi.techangkeji.com/image/20190716/8cece49c6231bb39b42740835bafa7b5.png"));  //缩略图
                web.setDescription("这是一个测试数据");//描述
                new ShareAction(MainActivity.this)
                        .withMedia(web)
                        .setPlatform(SHARE_MEDIA.QQ)//传入平台
                        .setCallback(shareListener)//回调监听器
                        .share();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, authListener);
            }
        });


    }

    private UMShareListener shareListener=new UMShareListener(){
        /**
         * @descrption 分享开始的回调
         * @param share_media 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }
        /**
         * @descrption 分享成功的回调
         * @param share_media 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.shortToastCenter(MainActivity.this,"分享成功");
        }
        /**
         * @descrption 分享失败的回调
         * @param share_media 平台类型
         */
        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            //Toast.makeText(context,"失败"+throwable.getMessage(),Toast.LENGTH_LONG).show();
            if (throwable.getMessage().contains("2008")){
                if (share_media==SHARE_MEDIA.WEIXIN){
                    ToastUtil.shortToastCenter(MainActivity.this,"您未安装微信客户端");
                }else if (share_media==SHARE_MEDIA.QQ){
                    ToastUtil.shortToastCenter(MainActivity.this,"您未安装QQ客户端");
                }
            }
        }
        /**
         * @descrption 分享取消的回调
         * @param share_media 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtil.shortToastCenter(MainActivity.this,"分享取消");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(MainActivity.this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();

    }

   private UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(MainActivity.this, "成功了"+data.get("name"), Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(MainActivity.this, "失败：" + t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "授权取消了", Toast.LENGTH_LONG).show();
        }
    };
}
