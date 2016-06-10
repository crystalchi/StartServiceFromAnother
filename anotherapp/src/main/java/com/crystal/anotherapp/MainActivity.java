package com.crystal.anotherapp;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStart;
    private Button mBtnStop;

    private Intent serviceIntent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnStop.setOnClickListener(this);

        //android5.0之后需显式启动第三方应用的service
        ComponentName componentName = new ComponentName("com.crystal.startservicefromanother",
                "com.crystal.startservicefromanother.service.AppService");
        serviceIntent.setComponent(componentName);
        startService(serviceIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startService(serviceIntent);
                break;
            case R.id.btn_stop:
                stopService(serviceIntent);
                break;
        }
    }
}
