/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: RemoteService.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/24
 */

package com.adam.app.demo.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.ArrayList;

/**
 * <h1>RemoteService</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/24
 */
public class RemoteService extends Service {

    // Bundle key
    static final String CONTENT = "content";
    // Message type
    static final int REGISTER_CALL_BACK = 1;
    static final int UNREGISTER_CALL_BACK = 2;
    static final int EXECUTE_COMMAND = 3;

    // Record the client Messenger
    ArrayList<Messenger> mClients = new ArrayList<Messenger>();

    // The handler of the remote service
    private Handler mHanlder = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case REGISTER_CALL_BACK:
                mClients.add(msg.replyTo);
                break;
            case UNREGISTER_CALL_BACK:
                mClients.remove(msg.replyTo);
                break;
            case EXECUTE_COMMAND:
                int i = 0;

                while (i < mClients.size()) {

                    String ret = "This is Service message";
                    Bundle bundle = new Bundle();
                    bundle.putString(CONTENT, ret);

                    Message message = Message.obtain();
                    message.what = EXECUTE_COMMAND;
                    message.setData(bundle);

                    // Send message to client
                    try {
                        mClients.get(i).send(message);
                    } catch (RemoteException e) {
                        // The client is dead. Remove it from list
                        mClients.remove(i);
                    }

                    i++;

                }
                break;
            default:
                super.handleMessage(msg);
            }
        }

    };

    // The messenger of the remote service
    private Messenger mMessenger = new Messenger(this.mHanlder);

    @Override
    public IBinder onBind(Intent intent) {
        Utils.ShowToast(this.getApplicationContext(),
                "Remote service is bounded");
        // Return the native binder
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Utils.ShowToast(this.getApplicationContext(),
                "Remote service is unbounded");
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.ShowToast(this.getApplicationContext(),
                "Remote service is created");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /**
         * if this service's process is killed while it is started then it will
         * be scheduled for a restart and the last delivered Intent re-delivered
         * to it again via onStartCommand
         */
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
