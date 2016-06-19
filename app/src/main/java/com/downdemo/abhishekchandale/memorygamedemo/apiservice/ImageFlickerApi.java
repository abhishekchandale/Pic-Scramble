package com.downdemo.abhishekchandale.memorygamedemo.apiservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.downdemo.abhishekchandale.memorygamedemo.R;
import com.downdemo.abhishekchandale.memorygamedemo.api.APIManager;
import com.downdemo.abhishekchandale.memorygamedemo.api.RetrofitAPIService;
import com.downdemo.abhishekchandale.memorygamedemo.database.DbAccess;
import com.downdemo.abhishekchandale.memorygamedemo.fragment.PuzzleGridFragment;
import com.downdemo.abhishekchandale.memorygamedemo.model.ImageModel;
import com.downdemo.abhishekchandale.memorygamedemo.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void getImagesFlickerApi(final Context context) {
        try {
            if (mDialog == null) {
                mDialog = new ProgressDialog(context.getApplicationContext());
                mDialog.setMessage("Please be patience.. Initialising in process");
                mDialog.setCancelable(false);
                mDialog.show();
            }
            RetrofitAPIService retrofitAPIService = APIManager.getRetrofitServiceeInstanse();
            final Call<ImageModel> getImages = retrofitAPIService.getImageFromFlicker(Constants.ID, Constants.LANG, Constants.FORMAT, Constants.JSONCALBACK);
            getImages.enqueue(new Callback<ImageModel>() {
                @Override
                public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                    Log.d(TAG, "response" + response.code());
                    if (response.code() == 200) {
                        Log.d("bulkOrderUpdate", response.body().toString());
                        ImageModel imageModel = response.body();
                        imageModel.getLink();
                        imageModel.getDescription();
                        imageModel.getItems().get(0).getTitle();
                        imageModel.getItems().get(0).getMedia().getM();
                        mDbAccess = new DbAccess(context);
                        for (int i = 0; i < imageModel.getItems().size(); i++) {
                            mDbAccess.addPuzzleImages(imageModel.getItems().get(i).getTitle(),
                                    imageModel.getItems().get(i).getMedia().getM());
                        }
                        mDialog.dismiss();

                    } else {
                        Log.d(TAG, "code" + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
