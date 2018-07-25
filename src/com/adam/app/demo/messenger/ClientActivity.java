/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: ClientActivity.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/24
 */

package com.adam.app.demo.messenger;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;

public class ClientActivity extends Activity {

    private Messenger mServiceProxy;

    // Show service state
    private TextView mShow;

    // Flag for indication service bounded status
    boolean mIsBound;

    // The handler of the client
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
            case RemoteService.EXECUTE_COMMAND:
                String result = (String) msg.getData().get(
                        RemoteService.CONTENT);
                mShow.setText("The result: " + result);
                break;
            default:
                super.handleMessage(msg);
            }

        }

    };

    // The Client messenger
    private Messenger mClient = new Messenger(this.mHandler);

    // The connection is the communication channel between the service and
    // client
    private ServiceConnection mConnect = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            // Get the service binder proxy
            mServiceProxy = new Messenger(service);

            // Register call back
            Message msg = Message
                    .obtain(null, RemoteService.REGISTER_CALL_BACK);
            msg.replyTo = mClient;
            try {
                mServiceProxy.send(msg);
            } catch (RemoteException e) {
                Utils.ShowToast(getApplicationContext(),
                        "Remote service has crashed.");
            }

            // Show service state
            mShow.setText("Attached...");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

            mServiceProxy = null;
            // Show service state
            mShow.setText("Disconnected...");
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShow = (TextView) this.findViewById(R.id.show_result);
    }

    /**
     * 
     * <h1>onBindSvr</h1>
     * 
     * @param v
     * @return void
     * 
     */
    public void onBindSvr(View v) {

        // Bind service
        Intent intent = new Intent("com.adam.app.demo.messenger.RemoteService");
        this.bindService(intent, mConnect, Service.BIND_AUTO_CREATE);

        mIsBound = true;
        mShow.setText("Binding...");
    }

    /**
     * 
     * <h1>onInvokeSvr</h1>
     * 
     * @param v
     * @return void
     * 
     */
    public void onInvokeSvr(View v) {

        mShow.setText("Invoke service");
        if (mServiceProxy != null) {
            Message msg = Message.obtain(null, RemoteService.EXECUTE_COMMAND);
            msg.replyTo = this.mClient;
            try {
                mServiceProxy.send(msg);
            } catch (RemoteException e) {
                Utils.ShowToast(getApplicationContext(),
                        "Remote service has crashed.");
            }
        } else {
            Utils.ShowToast(getApplicationContext(), "service is not bound");
        }

    }

    /**
     * 
     * <h1>onUnbindSvr</h1>
     * 
     * @param v
     * @return void
     * 
     */
    public void onUnbindSvr(View v) {

        if (mIsBound) {

            if (mServiceProxy != null) {
                
             // Unregister call back
                Message msg = Message.obtain(null,
                        RemoteService.UNREGISTER_CALL_BACK);
                msg.replyTo = mClient;
                try {
                    mServiceProxy.send(msg);
                } catch (RemoteException e) {
                    Utils.ShowToast(getApplicationContext(),
                            "Remote service has crashed.");
                }
            }

            this.unbindService(mConnect);
        }

        mIsBound = false;
        mShow.setText("UnBinding...");
    }
}
