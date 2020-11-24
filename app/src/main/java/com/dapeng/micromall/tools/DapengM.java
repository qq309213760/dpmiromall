package com.dapeng.micromall.tools;

import android.content.Context;
import android.util.Log;

import com.dapeng.micromall.ui.http.HttpType;
import com.dapeng.micromall.ui.http.MicromallCallback;
import com.dapeng.micromall.ui.http.ResponseData;
import com.dapeng.micromall.ui.user.ConfigBean;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdkx5.YouZanSDKX5Adapter;

public class DapengM {

    //YouzanSDK初始化
    public static void initMicromall(Context context, String clienId, String appkey, MicromallCallback callback, ConfigBean configBean) {
        YouzanSDK.isDebug(true);
        // YouZanSDKX5Adapter adapter = new YouZanSDKX5Adapter();
        YouzanSDK.init(context, clienId, appkey, new YouZanSDKX5Adapter() {
            @Override
            public void initWeb() {
                super.initWeb();
                if (callback != null) {
                    try {
                        callback.onSuccess(new ResponseData(false, HttpType.CODEZAN, "ok", configBean));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Log.d("LD", "初始成功了");
            }
        });
        //YouzanSDK.init(this, "6a7e1688f4e6100845", "60cf6fc9c8564907baf3207469292a9e", new YouZanSDKX5Adapter());
    }


}
