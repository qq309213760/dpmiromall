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


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dapeng.micromall.R;
import com.dapeng.micromall.ui.http.HttpType;
import com.dapeng.micromall.ui.http.MicromallCallback;
import com.dapeng.micromall.ui.http.ResponseData;
import com.dapeng.micromall.ui.user.SharedPreferencesTool;
import com.dapeng.micromall.ui.user.UserData;
import com.youzan.androidsdk.YouzanSDK;
import com.youzan.androidsdk.YouzanToken;
import com.youzan.androidsdk.event.AbsAddToCartEvent;
import com.youzan.androidsdk.event.AbsAddUpEvent;
import com.youzan.androidsdk.event.AbsAuthEvent;
import com.youzan.androidsdk.event.AbsBuyNowEvent;
import com.youzan.androidsdk.event.AbsChooserEvent;
import com.youzan.androidsdk.event.AbsPaymentFinishedEvent;
import com.youzan.androidsdk.event.AbsStateEvent;
import com.youzan.androidsdk.model.goods.GoodsOfCartModel;
import com.youzan.androidsdk.model.goods.GoodsOfSettleModel;
import com.youzan.androidsdk.model.trade.TradePayFinishedModel;
import com.youzan.androidsdkx5.YouzanBrowser;

import java.util.List;


/**
 * 这里使用{@link WebViewFragment}对{@link android.webkit.WebView}生命周期有更好的管控.
 */
public class YouzanFragment extends WebViewFragment implements SwipeRefreshLayout.OnRefreshListener {
    private YouzanBrowser mView;
    private SwipeRefreshLayout mRefreshLayout;
    //private Toolbar mToolbar;
    private static final int CODE_REQUEST_LOGIN = 0x1000;
    String userId = "";//用户ID
    MicromallCallback callback;

    public YouzanFragment(MicromallCallback callback) {
        this.callback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View views, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(views, savedInstanceState);
        mView = getWebView();
        if (mView == null) {
            Toast.makeText(getActivity(), "WebView是空的", Toast.LENGTH_SHORT).show();
        }
        if (getArguments().containsKey("userId")) {
            userId = getArguments().getString("userId");
        }
        Log.v("LD", "==================用户ID:" + userId + "====YouzanSDK.isReady():" + YouzanSDK.isReady());
        if (mView == null) {
            Log.v("LD", "==================mView 是空的   准备重新获取");
            mView = views.findViewById(R.id.view);
        }
        setupViews(views);
        setupYouzan();
        initConpnet();
    }


    private void initConpnet() {
        String url;
        if (SharedPreferencesTool.getConfigData(getActivity()) != null && SharedPreferencesTool.getConfigData(getActivity()).getYouzan() != null
                 && !TextUtils.isEmpty(SharedPreferencesTool.getConfigData(getActivity()).getYouzan().getHomePageUrl())) {
            url = SharedPreferencesTool.getConfigData(getActivity()).getYouzan().getHomePageUrl();
        } else {
            Log.e("LD", "商城URL是空的");
            return;
        }
        Log.e("LD", "当前请求的URL:" + url);
        mView.loadUrl(url);
        mView.needLoading(true);
        mView.setLoadingImage(R.drawable.loading);
    }

    private void setupViews(View contentView) {
        //WebView
        //mToolbar = (Toolbar) contentView.findViewById(R.id.toolbar);
        mRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.swipe);
        //分享按钮
        //mToolbar.setTitle(R.string.loading_page);
        // mToolbar.inflateMenu(R.menu.menu_youzan_share);
        //刷新
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED);
        mRefreshLayout.setEnabled(false);
    }

    boolean show = false;


    public void refreshAuthEvent(Context context) {
        Log.v("LD", "重新  认证---------------》》》");
        if (SharedPreferencesTool.getMmUser(context) != null) {
            //重新登录
            Log.v("LD", "认证---------------》》》");
            YouzanToken token = new YouzanToken();
            UserData user = SharedPreferencesTool.getMmUser(context);
            if (user != null && !TextUtils.isEmpty(user.getCookieValue()) && !TextUtils.isEmpty(user.getCookieKey())) {
                token.setCookieKey(user.getCookieKey());
                token.setCookieValue(user.getCookieValue());
                YouzanSDK.sync(getActivity(), token);
                mView.sync(token);
            } else {
                Log.d("LD", "认证异常  key/value 是空的");
                return;
            }
        }
    }

    private void setupYouzan() {
        Log.w("LD", "初始有赞 监听");
        if (mView == null) {
            Log.w("LD", "mView  ===============是空的");
        }

        mView.subscribe(new AbsAuthEvent() {
            @Override
            public void call(Context context, boolean needLogin) {
                Log.w("LD", "AbsAuthEvent   需要认证");
                if (SharedPreferencesTool.getMmUser(getActivity()) != null) {
                    //重新登录
                    Log.v("LD", "开始  认证---------------》》》");
                    /*YouzanToken token = new YouzanToken();
                    UserData user = SharedPreferencesTool.getMmUser(getActivity());
                    if (user != null && !TextUtils.isEmpty(user.getCookieValue()) && !TextUtils.isEmpty(user.getCookieKey())) {
                        token.setCookieKey(user.getCookieKey());
                        token.setCookieValue(user.getCookieValue());
                        YouzanSDK.sync(getActivity(), token);
                        mView.sync(token);
                    } else {
                        Log.d("LD", "认证异常  key/value 是空的");
                        return;
                    }*/
                    refreshAuthEvent(getActivity());
                } else {
                    if (SharedPreferencesTool.getMmUser(context) == null || TextUtils.isEmpty(SharedPreferencesTool.getMmUser(context).getCookieKey())) {
                        Log.w("LD", "未登陆");
                        if (mView != null && !show) {
                            callback.onError(new ResponseData(false, HttpType.ERROR_USERID, "未登陆"));
                        }
                    }
                }
            }
        });

        //文件选择事件, 回调表示: 发起文件选择. (如果app内使用的是系统默认的文件选择器, 该事件可以直接删除)
        mView.subscribe(new AbsChooserEvent() {
            @Override
            public void call(Context context, Intent intent, int requestCode) throws ActivityNotFoundException {
                startActivityForResult(intent, requestCode);
            }
        });
        //页面状态事件, 回调表示: 页面加载完成
        mView.subscribe(new AbsStateEvent() {
            @Override
            public void call(Context context) {
                Log.d("LD", "---------------------页面加载完成：：" + mView.getTitle());
                if (mView.getTitle().contains("主页")) {
                    show = true;
                } else {
                    show = false;
                }
                //mToolbar.setTitle(mView.getTitle());
                //停止刷新
                //mRefreshLayout.setRefreshing(false);
               // mRefreshLayout.setEnabled(true);
            }
        });
        mView.subscribe(new AbsPaymentFinishedEvent() {
            @Override
            public void call(Context context, TradePayFinishedModel tradePayFinishedModel) {
                Log.w("LD", "---------------------subscribe");
            }
        });
        mView.subscribe(new AbsAddToCartEvent() {
            @Override
            public void call(Context context, GoodsOfCartModel goods) {
                Log.w("LD", "添加到购物车---------------------subscribe");
            }


        });
        mView.subscribe(new AbsAddUpEvent() {

            @Override
            public void call(Context context, List<GoodsOfSettleModel> datas) {
                Log.w("LD", "结算---------------------subscribe");
            }
        });

        //购买
        mView.subscribe(new AbsBuyNowEvent() {
            @Override
            public void call(Context context, GoodsOfCartModel data) {
                if (SharedPreferencesTool.getMmUser(context) == null || TextUtils.isEmpty(SharedPreferencesTool.getMmUser(context).getCookieKey())) {
                    Log.w("LD", "未登陆");
                    callback.onError(new ResponseData(false, HttpType.ERROR_USERID, "未登陆"));
                } else {
                    Log.w("LD", "g购买---------------------subscribe");
                }
            }
        });

        mView.subscribe(new AbsPaymentFinishedEvent() {
            @Override
            public void call(Context context, TradePayFinishedModel data) {
                Log.w("LD", "支付完成回到结果页，结果页收到支付成功回调---------------------subscribe");
            }
        });
    }


    @Override
    protected int getWebViewId() {
        //YouzanBrowser在布局文件中的id
        return R.id.view;
    }


    @Override
    protected int getLayoutId() {
        //布局文件
        return R.layout.fragment_micromall;
    }

    @Override
    public boolean onBackPressed() {
        //页面回退
        return getWebView().pageGoBack();
    }

    @Override
    public void onRefresh() {
        //重新加载页面
        mView.reload();
    }


}
