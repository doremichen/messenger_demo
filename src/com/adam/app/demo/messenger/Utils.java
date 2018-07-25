/**
 * ===========================================================================
 * Copyright Adam Sample code
 * All Rights Reserved
 * ===========================================================================
 * 
 * File Name: Utils.java
 * Brief: 
 * 
 * Author: AdamChen
 * Create Date: 2018/7/24
 */

package com.adam.app.demo.messenger;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * <h1>Utils</h1>
 * 
 * @autor AdamChen
 * @since 2018/7/24
 */
public final class Utils {

    private static final String TAG = "MessengerDemo";

    // Prevent to instance
    private Utils() {
    }

    /**
     * 
     * <h1>Info</h1> Log info
     * 
     * @param obj
     * @param str
     * @return void
     * 
     */
    public static void Info(Object obj, String str) {
        Log.i(TAG, obj.getClass().getSimpleName() + ": " + str);
    }

    /**
     * 
     * <h1>Info</h1> Log info
     * 
     * @param clazz
     * @param str
     * @return void
     * 
     */
    public static void Info(Class<?> clazz, String str) {
        Log.i(TAG, clazz.getSimpleName() + ": " + str);
    }

    /**
     * 
     * <h1>ShowToast</h1> show toast ui
     *
     * @param context
     * @param str
     * @return void
     *
     */
    public static void ShowToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
