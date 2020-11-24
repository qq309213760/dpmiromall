package com.dapeng.micromall.ui.http;

public interface MicromallCallback {
    void onSuccess(ResponseData object) throws Exception;
    void onError(ResponseData object);
}
