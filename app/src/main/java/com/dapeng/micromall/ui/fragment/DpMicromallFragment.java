/*
 * Copyright (C) 2017 youzanyun.com, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dapeng.micromall.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dapeng.micromall.R;
import com.dapeng.micromall.ui.http.DapengSDKTool;
import com.dapeng.micromall.ui.http.HttpType;
import com.dapeng.micromall.ui.http.MicromallCallback;
import com.dapeng.micromall.ui.http.ResponseData;


public class DpMicromallFragment extends Fragment {

    YouzanFragment mFragment;
    String userId = "";//用户ID
    MicromallCallback callback;
    ProgressBar bar;
    ImageView errorImage;

    public DpMicromallFragment(String userId, MicromallCallback callback) {
        this.userId = userId;
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dp_micromall, null);
        return view;
    }

    @Override
    public void onViewCreated(View views, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(views, savedInstanceState);
        Log.d("LD", "打印ID" + userId);
        bar = views.findViewById(R.id.progress);
        bar.setVisibility(View.VISIBLE);

        errorImage = views.findViewById(R.id.errorImage);
        errorImage.setVisibility(View.GONE);
        initSDK();
    }

    public void refreshUserId(@Nullable String userId) {
        if (TextUtils.isEmpty(userId)) {
            Log.e("LD", "请传入userId");
            return;
        }
        this.userId = userId;
        DapengSDKTool.getInstance().refreshLogin(userId);
    }

    void initSDK() {
        DapengSDKTool.getInstance().init(getActivity(), userId, new MicromallCallback() {
            @Override
            public void onSuccess(ResponseData data) {
                if (data != null) {
                    switch (data.getTag()) {
                        case HttpType.CODE4:
                            Log.v("LD", "准备Fragment");
                            bar.setVisibility(View.GONE);
                            errorImage.setVisibility(View.GONE);
                            initConpnet();
                            if (data.isRefresh()) {
                                Log.v("LD", "======================重新认证=REFRESH_AUTH");
                                if (mFragment != null) {
                                    mFragment.refreshAuthEvent(getActivity());
                                }
                            } else {
                                Log.v("LD", "=============无需重新认证==========REFRESH_AUTH");
                            }
                            break;

                    }
                }
            }

            @Override
            public void onError(ResponseData object) {
                bar.setVisibility(View.GONE);
                if (object.getTag().equals(HttpType.CODE_MAINTAIN)) {
                    //维护/接口异常
                    errorImageShow();
                } else {
                    callback.onError(object);
                }
            }
        });
    }

    /**
     * 请求网络图片
     *
     * @param
     */
    private void errorImageShow() {
        errorImage.setVisibility(View.VISIBLE);
        //errorImage.setIm(getResources().getColor(Color.WHITE));
    }


    void initConpnet() {
        mFragment = new YouzanFragment(new MicromallCallback() {
            @Override
            public void onSuccess(ResponseData data) throws Exception {
                if (data != null) {
                    switch (data.getTag()) {
                        case HttpType.CODE4:
                            //登录
                            initConpnet();
                            break;

                    }
                }
            }

            @Override
            public void onError(ResponseData object) {
                callback.onError(object);

            }
        });

        Bundle bd = new Bundle();
        bd.putString("userId", userId);
        mFragment.setArguments(bd);

        getActivity().getSupportFragmentManager()
                 .beginTransaction()
                 .replace(R.id.flContainer, mFragment)
                 .commitAllowingStateLoss();


    }


    public boolean onBackPressed() {
        return !mFragment.onBackPressed() ? DapengSDKTool.getInstance().loginOut(getActivity(), null) : mFragment.onBackPressed();
    }


}
