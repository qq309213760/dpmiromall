package com.dapeng.micromall.ui.user;

public class ConfigBean {

    YouZanData youzan;
    /**
     * authUrl : http://passport.dapeng.lan
     * clientId : 9a51ae6c
     * secret : TnCogJt8XF
     */

    private String authUrl;
    private String clientId;
    private String appKey;
    private String appSecret;
    private String shopApiUrl;
    private String secret;
    private String shopHost;


    //enable:false 系统维护
    private boolean enable;
    //enabl ==false时显示
    private String errorImage;


    public String getErrorImage() {
        return errorImage;
    }

    public void setErrorImage(String errorImage) {
        this.errorImage = errorImage;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getShopApiUrl() {
        return shopApiUrl;
    }

    public void setShopApiUrl(String shopApiUrl) {
        this.shopApiUrl = shopApiUrl;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public YouZanData getYouzan() {
        return youzan;
    }

    public void setYouzan(YouZanData youzan) {
        this.youzan = youzan;
    }

    public String getShopHost() {
        return shopHost;
    }

    public void setShopHost(String shopHost) {
        this.shopHost = shopHost;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
