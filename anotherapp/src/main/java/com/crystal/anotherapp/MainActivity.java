package com.crystal.anotherapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.crystal.startservicefromanother.IAppServiceRemoteBinder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnStart;
    private Button mBtnStop;
    private Button mBtnBind;
    private Button mBtnUnBind;
    private EditText mEtMsg;
    private Button mBtnPass;

    private Intent serviceIntent = new Intent();
    private IAppServiceRemoteBinder appServiceRemoteBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStart.setOnClickListener(this);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnStop.setOnClickListener(this);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mBtnBind.setOnClickListener(this);
        mBtnUnBind = (Button) findViewById(R.id.btn_unbind);
        mBtnUnBind.setOnClickListener(this);
        mEtMsg = (EditText) findViewById(R.id.et_msg);
        mBtnPass = (Button) findViewById(R.id.btn_pass);
        mBtnPass.setOnClickListener(this);

        //android5.0之后需显式启动第三方应用的service
        ComponentName componentName = new ComponentName("com.crystal.startservicefromanother",
                "com.crystal.startservicefromanother.service.AppService");
        serviceIntent.setComponent(componentName);
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
            case R.id.btn_bind:
                bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_unbind:
                unbindService(serviceConnection);
                break;
            case R.id.btn_pass:
                try {
                    appServiceRemoteBinder.passMsg(mEtMsg.getText().toString()); //pass the message.
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            appServiceRemoteBinder = IAppServiceRemoteBinder.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
