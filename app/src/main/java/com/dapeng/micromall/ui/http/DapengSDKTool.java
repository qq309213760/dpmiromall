package com.dapeng.micromall.ui.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dapeng.micromall.tools.AESUtils;
import com.dapeng.micromall.tools.DapengM;
import com.dapeng.micromall.ui.user.ConfigBean;
import com.dapeng.micromall.ui.user.SharedPreferencesTool;
import com.youzan.androidsdk.YouzanSDK;

import org.apache.commons.codec.binary.Hex;
import org.json.JSONException;


/**
 * 获取
 * 1、配置文件
 * 2、解密
 * 3、token
 */
public class DapengSDKTool implements MicromallCallback {

    static MicromallCallback callback;
    static Context context;
    static String userId;

    //类加载时就初始化
    private static final DapengSDKTool instance = new DapengSDKTool();

    private DapengSDKTool() {
    }

    public static DapengSDKTool getInstance() {
        return instance;
    }


    //YouzanSDK登出
    public static boolean loginOut(Context context, MicromallCallback callback) {
        Log.d("LD", "登出了");
        String userId = SharedPreferencesTool.getUserId(context);
        if (!TextUtils.isEmpty(userId)) {
            DapengSDKTool.getInstance().login_Out(context, userId, callback);
        }
        YouzanSDK.userLogout(context);
        return !true;
    }


    //请求配置文件
    public void init(Context context, String userId, MicromallCallback callback) {
        DapengSDKTool.context = context;
        DapengSDKTool.userId = userId;
        DapengSDKTool.callback = callback;
        Log.v("LD", "callback:" + callback.toString());
        if (!TextUtils.isEmpty(userId)) {
            SharedPreferencesTool.saveUserId(context, userId);
        }
        UserLoginRequst.getConfig(context, DapengSDKTool.this);
    }

    //解密
    private void onTbAes(Context context, String aseStr) throws Exception {
        //密文解密
        byte[] bb = Hex.decodeHex(aseStr);
        byte[] key = Hex.decodeHex(AESUtils.getStr());
        byte[] vlues = AESUtils.decrypt(key, bb);
        String aesString = new String(vlues, "utf-8");
        Log.d("LD", "解密:" + aesString);

        if (callback != null) {
            callback.onSuccess(new ResponseData(false, HttpType.CODE2, aesString));
        }
        if (TextUtils.isEmpty(aesString)) {
            Log.e("LD", "配置文件数据异常");
            return;
        }
        ConfigBean configBean = JSON.parseObject(aesString, ConfigBean.class);
        if (!configBean.isEnable()) {
            if (callback != null) {
                callback.onError(new ResponseData(false, HttpType.CODE_MAINTAIN, "维护中"));
            }
        } else {
            if (configBean != null && !TextUtils.isEmpty(configBean.getYouzan().getClientId()) && !TextUtils.isEmpty(configBean.getYouzan().getAppKey())) {
                Log.w("LD", "初始YouzanSDK init:" + configBean.getYouzan().getClientId() + " " + configBean.getYouzan().getAppKey());
                DapengM.initMicromall(context, configBean.getYouzan().getClientId(), configBean.getYouzan().getAppKey(), this, configBean);
            } else {
                Log.e("LD", "配置文件  获取异常   无法初始化 YouzanSDK");
                return;
            }
            Log.d("LD", "获取Token接口");
            SharedPreferencesTool.saveConfigData(context, aesString);
        }

    }

    //获取token
    private void getToken(Context context, ConfigBean userData) throws Exception {
        UserLoginRequst.getToken(context, userData, DapengSDKTool.this);
    }

    @Override
    public void onSuccess(ResponseData data) throws Exception {
        if (data != null) {
            switch (data.tag) {
                case HttpType.CODE1:
                    // Log.w("LD", "CODE1  获取到密文");
                    onTbAes(context, (String) data.getData());
                    break;
                case HttpType.CODE2:
                    break;
                case HttpType.CODE3:
                    if (data != null && data.getData() != null) {
                        //登录接口
                        if (!TextUtils.isEmpty(DapengSDKTool.userId)) {
                            UserLoginRequst.userLogin(context, DapengSDKTool.userId, this, false);
                        } else {
                            //无用户ID时
                            Log.d("LD", "没有登陆 callback:" + callback.toString());
                            if (callback != null) {
                                callback.onError(new ResponseData(false, HttpType.ERROR_USERID, "用户未登陆"));
                            }
                        }

                    } else {
                        Log.e("LD", "获取TOKEN失败了");
                    }
                    break;
                case HttpType.CODE4:
                    Log.v("LD", "---------------------登陆成功");
                    ConfigBean configBean = SharedPreferencesTool.getConfigData(context);
                    if (configBean == null) {
                        Log.e("LD", "无法登录，配置文件是空的");
                        return;
                    }
                    Log.v("LD", "获取 配置文件 准备 初始页面");
                    if (callback != null) {
                        callback.onSuccess(data);
                    }
                    break;
                case HttpType.CODEZAN:
                    //有赞初始化成功
                    Log.v("LD", "-----------------有赞初始化成功");
                    ConfigBean bean = data.getConfigBean();
                    getToken(context, bean);
                    break;
                case HttpType.CODE5:
                    break;
            }
        } else {
            onError(new ResponseData(false, HttpType.ERROR_OHTER, "微商城异常"));
        }
    }


    /**
     * 刷新登陆
     *
     * @param userId
     */
    public void refreshLogin(String userId) {
        DapengSDKTool.userId = userId;
        Log.d("LD", "callback:" + callback.toString());
        if (!TextUtils.isEmpty(userId)) {
            SharedPreferencesTool.saveUserId(context, userId);
        }
        try {
            UserLoginRequst.userLogin(context, DapengSDKTool.userId, this, true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(ResponseData object) {
        Log.d(DapengSDKTool.class.getSimpleName(), "微商城异常");
    }


    //登出接口
    public void login_Out(Context context, String userId, MicromallCallback callback) {
        UserLoginRequst.userLoginOut(context, userId, callback);
    }
}
