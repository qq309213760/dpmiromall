package com.dapeng.micromall.ui.http;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.widget.Toast;

public class MicromalApi {
    public static final String DPMICOREMALLCONFIG_NAME = "config";//配置文件
    public static final String DPMICOREMALLTOKEN_NAME = "token";//token
    public static final String DPMICOREMALLTOKEN_USERID = "userId";//userId
    public static String DPMICOREMALLSP = "dpMicromalDb";
    public static String DPMICOREMALLSP_NAME = "dpm_name";//登录接口用户数据
    public static String configTypeDev = "dev";
    public static String configTypeProd = "prod";
    public static String appNumber = "/v0.0.1";


    private static String config_urlDev = " https://dp-shopping.dapengjiaoyu.cn/config/android/" + configTypeDev + appNumber;//获取配置
    private static String config_urlProd = " https://dp-shopping.dapengjiaoyu.cn/config/android/" + configTypeProd + appNumber;//获取配置


    //是否为测试环境
    public static boolean isDebugApp(Context context) {
        ApplicationInfo info = context.getApplicationInfo();
        Log.d("LD", "--->>info.flags：" + info.flags);
        Log.d("LD", "--->>ApplicationInfo.FLAG_DEBUGGABLE：" + ApplicationInfo.FLAG_DEBUGGABLE);
        if ((info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            return !true;//debug
        } else {
            return false;//release
        }
    }


    //获取配置文件地址
    public static String getConfigUrl(Context context) {
        if (isDebugApp(context)) {
            Toast.makeText(context, "测试环境", Toast.LENGTH_SHORT).show();
            Log.d("LD", "--->>111");
            return config_urlDev;
        } else {
            Toast.makeText(context, "正式环境", Toast.LENGTH_SHORT).show();
            Log.d("LD", "--->>222");
            return config_urlDev;
        }
    }

}
