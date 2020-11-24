package com.dapeng.micromall.ui.user;

//用户信息
public class UserData extends BaseData{


 static    boolean isLogin;

    static public boolean isLogin() {
        return isLogin;
    }

    static public void setLogin(boolean login) {
        isLogin = login;
    }

    /**
     * cookieKey : 805baa22d54543caa50300da29f978f1
     * cookieValue : fa9751f71aa3493195171abb77c3d7dd
     * yzOpenId : c45f7fb48a984e8885bcf4f216761eed
     * dpOpenId : ea233601879044fe90c247a8daa07dde
     */



    private String cookieKey;
    private String cookieValue;
    private String yzOpenId;
    private String dpOpenId;

    public String getCookieKey() {
        return cookieKey;
    }

    public void setCookieKey(String cookieKey) {
        this.cookieKey = cookieKey;
    }

    public String getCookieValue() {
        return cookieValue;
    }

    public void setCookieValue(String cookieValue) {
        this.cookieValue = cookieValue;
    }

    public String getYzOpenId() {
        return yzOpenId;
    }

    public void setYzOpenId(String yzOpenId) {
        this.yzOpenId = yzOpenId;
    }

    public String getDpOpenId() {
        return dpOpenId;
    }

    public void setDpOpenId(String dpOpenId) {
        this.dpOpenId = dpOpenId;
    }
}
