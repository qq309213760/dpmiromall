package com.dapeng.micromall.ui.http;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.dapeng.micromall.ui.user.ConfigBean;
import com.dapeng.micromall.ui.user.SharedPreferencesTool;
import com.dapeng.micromall.ui.user.UserData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.Response;

import org.json.JSONException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserLoginRequst {


    //用户登录获取cookie-key和cookie-value
    public static void userLogin(Context context, @Nullable String userId, final MicromallCallback callback, boolean isRefresh) throws JSONException {
        if (TextUtils.isEmpty(userId)) {
            Log.e("LD", "没有用户ID");
            callback.onError(new ResponseData(true, HttpType.ERROR_USERID, "用户ID是空的"));
            return;
        }
        String temUrl = SharedPreferencesTool.getConfigData(context).getShopApiUrl();
        String tokenData = SharedPreferencesTool.getTokenData(context).getData();
        Log.v("LD", "准备 登录了 用户ID:" + userId);
        String url = temUrl + "/v1/yz/login";


        HttpHeaders headers = new HttpHeaders();
        headers.put("OpenAuth", tokenData);


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json1 = new JSONObject();
        json1.put("userId", userId);
        RequestBody requestBody = RequestBody.create(JSON, json1.toString());

        OkGo.<String>post(url).tag(context).headers(headers).upRequestBody(requestBody)
                 .execute(new StringCallback() {
                     @Override
                     public void onSuccess(Response<String> response) {
                         if (null != response && !TextUtils.isEmpty(response.body())) {
                             try {
                                 if (callback != null) {
                                     UserData userData = com.alibaba.fastjson.JSON.parseObject(response.body(), UserData.class);
                                     Log.d("LD", "CookieKey：" + userData.getCookieKey());
                                     SharedPreferencesTool.saveUserData(context, response.body());//保存用户数据

                                     callback.onSuccess(new ResponseData(false, HttpType.CODE4, userData,isRefresh));
                                 }
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }
                     }

                     @Override
                     public void onError(Response<String> response) {
                         super.onError(response);
                         if (callback != null) {
                             callback.onError(new ResponseData(false, HttpType.CODE_MAINTAIN, "用户登陆接口异常"));
                         }
                     }
                 });
    }

    //用户登出
    public static void userLoginOut(Context context, @Nullable String userId, final MicromallCallback callback) {
        String temUrl = SharedPreferencesTool.getConfigData(context).getShopApiUrl();
        String url = temUrl + "/v1/yz/logout";
        String tokenData = SharedPreferencesTool.getTokenData(context).getData();
        HttpHeaders headers = new HttpHeaders();
        headers.put("OpenAuth", tokenData);
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json1 = new JSONObject();
        json1.put("userId", userId);
        RequestBody body = RequestBody.create(JSON, json1.toString());

        OkGo.<String>post(url).tag(context).headers(headers).upRequestBody(body)
                 .execute(new StringCallback() {
                     @Override
                     public void onSuccess(Response<String> response) {
                         UserData userData = com.alibaba.fastjson.JSON.parseObject(response.body(), UserData.class);
                         try {
                             callback.onSuccess(new ResponseData(false, HttpType.CODE5, userData));
                             DapengSDKTool.getInstance().loginOut(context, null);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onError(Response<String> response) {
                         super.onError(response);
                         callback.onError(new ResponseData(false, HttpType.ERROR_USERID_OUT, "用户登出异常"));
                     }
                 });
    }


    //获取配置
    public static void getConfig(Context context, MicromallCallback callback) {
        HttpHeaders headers = new HttpHeaders("Accept", "application/json");
        OkGo.<String>get(MicromalApi.getConfigUrl(context)).headers(headers)
                 .tag(context)
                 .execute(new StringCallback() {
                     @Override
                     public void onSuccess(Response<String> response) {
                         try {
                             if (response != null && !TextUtils.isEmpty(response.body())) {
                                 if (callback != null) {
                                     Log.d("LD", "获取配置文件成功");
                                     callback.onSuccess(new ResponseData(false, HttpType.CODE1, response.body()));
                                 }
                             } else {
                                 Log.e("LD", "配置接口返回数据异常");
                             }
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onError(Response<String> response) {
                         super.onError(response);
                         Log.d("LD", "获取配置文件失败:" + response.body());
                         if (callback != null)
                             callback.onError(new ResponseData(false, HttpType.CODE1, response.body()));
                     }
                 });
    }


    //获取Token
    public static void getToken(Context context, @Nullable ConfigBean configBean, final MicromallCallback callback) throws JSONException {
        if (configBean == null) {
            return;
        }
        if (configBean.getAuthUrl().isEmpty()) {
            return;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        String url = configBean.getAuthUrl();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject json1 = new JSONObject();
        json1.put("appKey", configBean.getAppKey());
        json1.put("appSecret", configBean.getAppSecret());

        Log.v("LD", "===>" + configBean.getAppKey() + "===" + configBean.getAppSecret());

        RequestBody requestBody = RequestBody.create(JSON, json1.toString());
        OkGo.<String>post(url).tag(context).upRequestBody(requestBody)
                 .headers(headers)
                 .execute(new StringCallback() {
                     @Override
                     public void onSuccess(Response<String> response) {
                         // Log.w("LD", "密文:\n" + response.body());
                         try {
                             if (response != null && !TextUtils.isEmpty(response.body())) {
                                 SharedPreferencesTool.saveTokenData(context, response.body());
                                 callback.onSuccess(new ResponseData(false, HttpType.CODE3, response.body()));
                             }
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onError(Response<String> response) {
                         super.onError(response);
                         Log.d("LD", "获取失败:" + response.body());
                         callback.onError(new ResponseData(false, HttpType.CODE3, response.body()));
                     }
                 });

        /*OAuth2Client.Builder builder = new OAuth2Client.Builder(configBean.getClientId(), configBean.getSecret(), url);
        final OAuth2Client client = builder.build();

        client.requestAccessToken(new OAuthResponseCallback() {
            @Override
            public void onResponse(OAuthResponse response) {
                if (response.isSuccessful()) {
                    // response.getAccessToken();
                    Log.d("LD", "11111111111111111111：" + response.getAccessToken());
                    try {
                        callback.onSuccess( new ResponseData(tag,response));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // response.getOAuthError();
                    callback.onError(response);
                    Log.d("LD", "2222222222222222222：" + url);
                }
            }
        });*/

    }

}
