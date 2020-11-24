package com.dapeng.micromall.ui.user;

import java.io.Serializable;

public class TokenBean implements Serializable {

    /**
     * code : 2000
     * msg : OK
     * data : {"accessToken":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNjA1MzI0OTEwLCJ1c2VySWQiOiJ2bHBoZG5rNnd4OGtlaWI0cXU5aHNqMTYwNTIzODUxMDcwOCIsImlhdCI6MTYwNTIzODUxMH0.1MQgTfI9cp3cE_193nKl70XXm7FW7efISnxmy-BVdyw","systemCode":"DAPENG_PAYMENT","systemName":"鹏支付系統","systemUrl":"http://poa-test.dapengjiaoyu.cn/gateway/payment","isAdmin":false}
     * success : true
     */

    private int code;
    private String msg;
    private String data;
    private boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * accessToken : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBUFAiLCJpc3MiOiJTZXJ2aWNlIiwiZXhwIjoxNjA1MzI0OTEwLCJ1c2VySWQiOiJ2bHBoZG5rNnd4OGtlaWI0cXU5aHNqMTYwNTIzODUxMDcwOCIsImlhdCI6MTYwNTIzODUxMH0.1MQgTfI9cp3cE_193nKl70XXm7FW7efISnxmy-BVdyw
         * systemCode : DAPENG_PAYMENT
         * systemName : 鹏支付系統
         * systemUrl : http://poa-test.dapengjiaoyu.cn/gateway/payment
         * isAdmin : false
         */

        private String accessToken;
        private String systemCode;
        private String systemName;
        private String systemUrl;
        private boolean isAdmin;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public String getSystemName() {
            return systemName;
        }

        public void setSystemName(String systemName) {
            this.systemName = systemName;
        }

        public String getSystemUrl() {
            return systemUrl;
        }

        public void setSystemUrl(String systemUrl) {
            this.systemUrl = systemUrl;
        }

        public boolean isIsAdmin() {
            return isAdmin;
        }

        public void setIsAdmin(boolean isAdmin) {
            this.isAdmin = isAdmin;
        }
    }
}
