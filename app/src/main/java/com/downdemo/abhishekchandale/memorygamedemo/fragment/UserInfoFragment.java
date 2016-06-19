package com.downdemo.abhishekchandale.memorygamedemo.fragment;

/**
 * Created by user on 6/16/2016.
 */

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.downdemo.abhishekchandale.memorygamedemo.R;
import com.downdemo.abhishekchandale.memorygamedemo.api.APIManager;
import com.downdemo.abhishekchandale.memorygamedemo.api.RetrofitAPIService;
import com.downdemo.abhishekchandale.memorygamedemo.database.DbAccess;
import com.downdemo.abhishekchandale.memorygamedemo.model.ImageModel;
import com.downdemo.abhishekchandale.memorygamedemo.util.ConnectionDetector;
import com.downdemo.abhishekchandale.memorygamedemo.util.Constants;
import com.downdemo.abhishekchandale.memorygamedemo.util.Preferences;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Abhishek on 6/16/2016.
 */

public class UserInfoFragment extends Fragment {
    private View mUserView;
    private EditText mPlayerName;
    private Button mButton;
    private ProgressDialog mDialog;
    private DbAccess mDbAccess;
    private FragmentTransaction mFragmentTransaction;
    private static final String TAG = PuzzleGridFragment.class.getSimpleName();

    public UserInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mUserView = inflater.inflate(R.layout.fr_user_info, container, false);
        mPlayerName = (EditText) mUserView.findViewById(R.id.txt_player_name);
        mButton = (Button) mUserView.findViewById(R.id.email_sign_in_button);
        return mUserView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionDetector.getInstance().checkDataConnection(getActivity())) {
                    if (mPlayerName.getText().toString().length() > 0) {
                        mDbAccess = new DbAccess(getActivity());
                        if (mDialog == null) {
                            mDialog = new ProgressDialog(getActivity());
                            mDialog.setMessage("Please wait..");
                            mDialog.setCancelable(false);
                            mDialog.show();
                        }

                        getImagesFlickerApi();

                    } else {
                        Toast.makeText(getActivity(), "Please enter your name", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please check your internet connection..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // get all images from public api FLicker:

    public void getImagesFlickerApi() {
        try {

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
                        for (int i = 0; i < imageModel.getItems().size(); i++) {
                            mDbAccess.addPuzzleImages(imageModel.getItems().get(i).getTitle(),
                                    imageModel.getItems().get(i).getMedia().getM());
                        }
                        mDialog.dismiss();
                        mFragmentTransaction = getFragmentManager().beginTransaction();
                        PuzzleGridFragment puzzleGridFragment = new PuzzleGridFragment();
                        if (puzzleGridFragment != null) {
                            mFragmentTransaction.replace(R.id.frame_main, puzzleGridFragment, PuzzleGridFragment.class.getSimpleName());
                            mFragmentTransaction.addToBackStack(PuzzleGridFragment.class.getSimpleName());
                            mFragmentTransaction.commit();
                            //
                            if (mPlayerName != null) {
                                Preferences.setPlayerName(getActivity(), mPlayerName.getText().toString());
                            }
                            Preferences.setUserLoggedIn(getActivity(), true);
                        } else {
                            Log.d("", "Fragment null");
                        }
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
