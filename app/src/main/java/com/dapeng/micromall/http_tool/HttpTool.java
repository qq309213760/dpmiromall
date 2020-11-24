package com.dapeng.micromall.http_tool;

import android.content.Context;

import com.dapeng.micromall.ui.http.DapengSDKTool;
import com.dapeng.micromall.ui.http.MicromallCallback;

public class HttpTool {

    public static  void  loginOut(Context context, MicromallCallback callback){
        DapengSDKTool.getInstance().loginOut(context,callback);
    }
}
