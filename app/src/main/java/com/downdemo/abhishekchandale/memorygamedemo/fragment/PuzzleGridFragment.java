package com.downdemo.abhishekchandale.memorygamedemo.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.downdemo.abhishekchandale.memorygamedemo.R;
import com.downdemo.abhishekchandale.memorygamedemo.adapter.GridAdapter;
import com.downdemo.abhishekchandale.memorygamedemo.adapter.GridSpacingItemDecoration;
import com.downdemo.abhishekchandale.memorygamedemo.database.DbAccess;
import com.downdemo.abhishekchandale.memorygamedemo.model.CustomImageModel;
import com.downdemo.abhishekchandale.memorygamedemo.util.Constants;

import java.util.ArrayList;

/**
 * Created by Abhishek on 6/16/2016.
 */

public class PuzzleGridFragment extends Fragment {
    private View mGridView;
    private RecyclerView mRecyclerView;
    private Cursor mCursor;
    private DbAccess mDbAccess;
    private ArrayList<CustomImageModel> mImageModel = null;
    private TextView mTImer;
    private static final String TAG = PuzzleGridFragment.class.getSimpleName();
    private CountDownTimer mCountDownTimer;
    private GridAdapter mAdapter = null;
    private ImageView mCampareImage;
    private int spanCount = 3;
    private int spacing = 20;
    private boolean includeEdge = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mGridView = inflater.inflate(R.layout.fr_puuzle_layout, container, false);
        mTImer = (TextView) mGridView.findViewById(R.id.tv_timer);
        mCampareImage = (ImageView) mGridView.findViewById(R.id.imageview);
        mRecyclerView = (RecyclerView) mGridView.findViewById(R.id.fr_puzzle_recycler);
        return mGridView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mDbAccess = new DbAccess(getActivity());
        mImageModel = new ArrayList<CustomImageModel>();
        getImagesFromDatabase();
        if (mImageModel != null) {
            mRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
            mAdapter = new GridAdapter(mImageModel, PuzzleGridFragment.this, mCampareImage, mTImer);
            mRecyclerView.setAdapter(mAdapter);
            counDownTimer();

        } else {
            Log.d(TAG, "Images array null");
        }

    }

    //get all images from database
    private void getImagesFromDatabase() {
        try {
            mCursor = mDbAccess.getPuzzleImages();

            if (mCursor.moveToNext()) {
                for (int i = 0; i < mCursor.getCount(); i++) {
                    String imagetitle = mCursor.getString(mCursor.getColumnIndex("imagetitle"));
                    String imageurl = mCursor.getString(mCursor.getColumnIndex("imageurl"));
                    mImageModel.add(new CustomImageModel(imagetitle, imageurl));
                    mCursor.moveToNext();
                }
            } else {
                Toast.makeText(getActivity(), "databse not created...", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    //counter 15 secs

    public void counDownTimer() {
        try {
            mCountDownTimer = new CountDownTimer(Constants.COUNTERTIME, Constants.COUNTERINTERVAL) {

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {

                    mCountDownTimer.cancel();
                    if (mAdapter instanceof ICallBAck) {
                        mAdapter.getCallback();
                    }

                }
            };
            mCountDownTimer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Interface to notify adapter to flip images

    public interface ICallBAck {
        void getCallback();
    }
}
