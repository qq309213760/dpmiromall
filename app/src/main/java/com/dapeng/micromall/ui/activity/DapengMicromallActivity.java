package com.dapeng.micromall.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dapeng.micromall.R;
import com.dapeng.micromall.ui.fragment.DpMicromallFragment;
import com.dapeng.micromall.ui.http.HttpType;
import com.dapeng.micromall.ui.http.MicromallCallback;
import com.dapeng.micromall.ui.http.ResponseData;

public class DapengMicromallActivity extends AppCompatActivity {
    private DpMicromallFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dapeng_micromall);
        initConpnet();
    }

    //00cd6131
    void initConpnet() {
        mFragment = new DpMicromallFragment("", new MicromallCallback() {
            @Override
            public void onSuccess(ResponseData data) {
            }

            @Override
            public void onError(ResponseData object) {
                if (object.getTag().equals(HttpType.ERROR_USERID)) {
                    //用户未登陆，打开登陆页面
                    Log.w("LD", "=========>>>未登陆");
                    startActivityForResult(new Intent(DapengMicromallActivity.this,LogintActivity.class),100);
                }
            }
        });
        getSupportFragmentManager()
                 .beginTransaction()
                 .replace(R.id.flContainer, mFragment)
                 .commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        //监听返回键,返回webview上一页
        if (mFragment == null || !mFragment.onBackPressed()) {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (mFragment != null) {
                mFragment.refreshUserId("00cd6131");
            }
        }
    }
}