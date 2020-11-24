package com.dapeng.micromall.ui.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dapeng.micromall.ui.http.MicromalApi;

public class SharedPreferencesTool {


    //存入用户商城数据
    static public void saveUserId(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        sp.edit().putString(MicromalApi.DPMICOREMALLTOKEN_USERID, value).commit();
    }

    //存入用户商城数据
    static public void saveUserData(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        sp.edit().putString(MicromalApi.DPMICOREMALLSP_NAME, value).commit();
    }

    //存入配置文件
    static public void saveConfigData(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        sp.edit().putString(MicromalApi.DPMICOREMALLCONFIG_NAME, value).commit();
    }

    //存入Token
    static public void saveTokenData(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        sp.edit().putString(MicromalApi.DPMICOREMALLTOKEN_NAME, value).commit();
    }

    //获取用户数据（cookiKey+Vule）
    public static UserData getMmUser(Context context) {
        UserData userData = null;
        if (context == null) {
            Log.e("LD", "无法 获取用户数据");
            return userData;
        }
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        if (sp.contains(MicromalApi.DPMICOREMALLSP_NAME)) {
            String userString = sp.getString(MicromalApi.DPMICOREMALLSP_NAME, null);
            if (!TextUtils.isEmpty(userString)) {
                userData = JSON.parseObject(userString, UserData.class);

            }
        }
        return userData;
    }


    public static String getUserId(Context context) {
        String userID = null;
        if (context == null) {
            Log.e("LD", "无法 获取用户  UserId");
            return userID;
        }
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        if (sp.contains(MicromalApi.DPMICOREMALLTOKEN_USERID)) {
            userID = sp.getString(MicromalApi.DPMICOREMALLTOKEN_USERID, null);
        }
        return userID;
    }

    //获取配置文件
    public static ConfigBean getConfigData(Context context) {
        ConfigBean userData = null;
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        if (sp.contains(MicromalApi.DPMICOREMALLCONFIG_NAME)) {
            String userString = sp.getString(MicromalApi.DPMICOREMALLCONFIG_NAME, null);
            if (!TextUtils.isEmpty(userString)) {
                userData = JSON.parseObject(userString, ConfigBean.class);

            }
        }
        return userData;
    }

    //获取TOKEN
    public static TokenBean getTokenData(Context context) {
        TokenBean token = null;
        SharedPreferences sp = context.getSharedPreferences(MicromalApi.DPMICOREMALLSP, 0);
        if (sp.contains(MicromalApi.DPMICOREMALLTOKEN_NAME)) {
            String userString = sp.getString(MicromalApi.DPMICOREMALLTOKEN_NAME, null);
            if (!TextUtils.isEmpty(userString)) {
                token = JSON.parseObject(userString, TokenBean.class);
            }
        }
        return token;
    }


}
