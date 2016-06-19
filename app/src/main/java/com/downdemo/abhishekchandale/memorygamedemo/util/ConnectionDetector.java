package com.downdemo.abhishekchandale.memorygamedemo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {

    private static ConnectionDetector mConnectionDetector = null;


    public static ConnectionDetector getInstance() {
        if (mConnectionDetector == null) {
            synchronized (ConnectionDetector.class) {
                if (mConnectionDetector == null) {
                    mConnectionDetector = new ConnectionDetector();
                    return mConnectionDetector;
                }
            }
        }
        return mConnectionDetector;
    }

    public boolean checkDataConnection(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        } catch (Exception e) {

            e.printStackTrace();

        }
        return false;
    }
}
