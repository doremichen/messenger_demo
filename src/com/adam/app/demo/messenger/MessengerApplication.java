/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: MessengerApplication.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/24
 */

package com.adam.app.demo.messenger;

import android.app.Application;
import android.content.Intent;

/**
 * <h1>MessengerApplication</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/24
 */
public class MessengerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Start remote service
        Intent intent = new Intent("com.adam.app.demo.messenger.RemoteService");
        this.startService(intent);
        
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
    
    

}
