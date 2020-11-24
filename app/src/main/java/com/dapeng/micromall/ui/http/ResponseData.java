package com.dapeng.micromall.ui.http;

import com.dapeng.micromall.ui.user.ConfigBean;

public class ResponseData {
    boolean type;
    String tag;
    Object data;
    ConfigBean configBean;
    boolean isRefresh;

    public ResponseData(boolean type, String tag, Object data) {
        this.type = type;
        this.tag = tag;
        this.data = data;
    }

    public ResponseData(boolean type, String tag, Object data, ConfigBean configBean) {
        this.type = type;
        this.tag = tag;
        this.data = data;
        this.configBean = configBean;
    }

    public ResponseData(boolean type, String tag, Object data, boolean isRefresh) {
        this.type = type;
        this.tag = tag;
        this.data = data;
        this.isRefresh = isRefresh;
    }



    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public ConfigBean getConfigBean() {
        return configBean;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
