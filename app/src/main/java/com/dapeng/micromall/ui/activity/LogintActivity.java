package com.dapeng.micromall.ui.activity;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.dapeng.micromall.R;

public class LogintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logint);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LogintActivity.this.finish();
                setResult(100);
            }
        }, 3000);
    }
}