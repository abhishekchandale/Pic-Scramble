package com.downdemo.abhishekchandale.memorygamedemo.apiservice;

import android.app.ProgressDialog;

import com.downdemo.abhishekchandale.memorygamedemo.database.DbAccess;

/**
 * Created by user on 6/15/2016.
 */

public class ImageFlickerApi {
    public static final String TAG = ImageFlickerApi.class.getSimpleName();

    private static ImageFlickerApi mImageFlickerApi = null;
    private DbAccess mDbAccess;
    private ProgressDialog mDialog;

    public static ImageFlickerApi getInstance() {
        if (mImageFlickerApi == null) {
            synchronized (ImageFlickerApi.class) {
                if (mImageFlickerApi == null) {
                    mImageFlickerApi = new ImageFlickerApi();
                    return mImageFlickerApi;
                }
            }
        }
        return mImageFlickerApi;
    }


}
