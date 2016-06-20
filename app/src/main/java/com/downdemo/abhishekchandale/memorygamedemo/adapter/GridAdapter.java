package com.downdemo.abhishekchandale.memorygamedemo.adapter;

/**
 * Created by user on 6/16/2016.
 */

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.downdemo.abhishekchandale.memorygamedemo.R;
import com.downdemo.abhishekchandale.memorygamedemo.fragment.PuzzleGridFragment;
import com.downdemo.abhishekchandale.memorygamedemo.model.CustomImageModel;
import com.downdemo.abhishekchandale.memorygamedemo.util.CommonUtil;
import com.downdemo.abhishekchandale.memorygamedemo.util.Constants;
import com.downdemo.abhishekchandale.memorygamedemo.util.Preferences;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.DataObjectHolder> implements PuzzleGridFragment.ICallBAck {

    private static String TAG = "ImageGridAdapter";
    private ArrayList<CustomImageModel> mCustomImageModel = new ArrayList<>();
    private PuzzleGridFragment mContext;
    private GridAdapter mImageGridAdapter;
    private DisplayImageOptions mOptions;
    private CountDownTimer mCountDownTimer, mTimer;
    private ArrayList<CustomImageModel> mCampateImageList;
    private ProgressDialog mDialog;
    ImageView mImageView;
    int count = 0;
    int mLoaderCount = 0;
    private ACProgressFlower dialog;
    private Ringtone mSoundEffect;
    private AlertDialog.Builder builder;
    private TextView mCounterText;

    public GridAdapter(ArrayList<CustomImageModel> customModel, PuzzleGridFragment context, ImageView imageview, TextView textView) {
        mCampateImageList = customModel;
        mCustomImageModel.addAll(customModel);
        Collections.shuffle(mCustomImageModel);
        mContext = context;
        this.mImageView = imageview;
        this.mCounterText = textView;
        mDialog = new ProgressDialog(mContext.getActivity(), R.style.CustomDialog);

        mOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.puzzle_item_image, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(imageview, this);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        final CustomImageModel customImageModel = mCustomImageModel.get(position);
        int i = 0;

        mTimer = new CountDownTimer(Constants.COUNTERTIME, Constants.COUNTERINTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                String v = String.format("%02d", millisUntilFinished / 60000);
                int va = (int) ((millisUntilFinished % 60000) / 1000);
                mCounterText.setText("" + v + ":" + String.format("%02d", va));
            }

            @Override
            public void onFinish() {
                mTimer.cancel();
                mCounterText.setVisibility(View.INVISIBLE);
                CommonUtil.flip(holder.mImageUrl, holder.mImageFlip, 90);
                dialog.dismiss();
            }
        }.start();

        try {
            if (customImageModel.getImageurl() != null) {
                ImageLoader.getInstance()
                        .displayImage(customImageModel.getImageurl(), holder.mImageUrl, mOptions, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                mDialog.setMessage("Loading Images...");
                                mDialog.show();
                                mDialog.setCancelable(false);
                                holder.mProgressBar.setProgress(0);
                                holder.mProgressBar.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                holder.mProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                mTimer.cancel();
                                Log.d(TAG, "Bitmap-" + loadedImage.toString());
                                holder.mProgressBar.setVisibility(View.GONE);
                                mLoaderCount = mLoaderCount + 1;
                                if (mLoaderCount == 8) {
                                    mDialog.dismiss();
                                    dialog = new ACProgressFlower.Builder(mContext.getActivity())
                                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                            .themeColor(Color.WHITE)
                                            .fadeColor(Color.DKGRAY).build();
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.show();
                                    mTimer.start();
                                }

                            }
                        }, new ImageLoadingProgressListener() {
                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                                holder.mProgressBar.setProgress(Math.round(100.0f * current / total));
                            }
                        });

            } else {
                //TODO:
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Exception" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mCustomImageModel.size();
    }

    @Override
    public void getCallback() {
        try {
            ImageLoader.getInstance()
                    .displayImage(mCampateImageList.get(0).getImageurl(), mImageView, mOptions, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {

                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            //   holder.mProgressBar.setVisibility(View.GONE);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle;
        ImageView mImageUrl, mImageFlip;
        ProgressBar mProgressBar;
        CountDownTimer countDownTimer;

        public DataObjectHolder(View itemView, GridAdapter ImageGridAdapter) {
            super(itemView);
            mImageGridAdapter = ImageGridAdapter;
            mImageUrl = (ImageView) itemView.findViewById(R.id.image_url);
            mImageFlip = (ImageView) itemView.findViewById(R.id.image_flip);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.image_progress);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.grid_item_layout:
                    int position = getAdapterPosition();
                    CustomImageModel customImageModel = mCustomImageModel.get(position);

                    if (customImageModel.isSelected() == true) {
                        v.setOnClickListener(null);
                    } else {
                        Log.d(TAG, "Clcklable");
                    }

                    if (customImageModel.getImageurl().equalsIgnoreCase(mCampateImageList.get(count).getImageurl())) {
                        CommonUtil.flip(mImageFlip, mImageUrl, 100);
                        customImageModel.setSelected(true);
                        count = count + 1;
                        if (count == 9) {
                            alertMessage();
                        }
                        try {
                            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            mSoundEffect = RingtoneManager.getRingtone(mContext.getActivity(), notification);
                            mSoundEffect.play();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "Count-" + count);
                        if (count >= 0 && count <= 8) {
                            ImageLoader.getInstance()
                                    .displayImage(mCampateImageList.get(count).getImageurl(), mImageView, mOptions, new SimpleImageLoadingListener() {
                                        @Override
                                        public void onLoadingStarted(String imageUri, View view) {
                                            // holder.mProgressBar.setProgress(0);
                                            //  holder.mProgressBar.setVisibility(View.VISIBLE);
                                        }

                                        @Override
                                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                            //   holder.mProgressBar.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                            //   holder.mProgressBar.setVisibility(View.GONE);
                                        }
                                    }, new ImageLoadingProgressListener() {
                                        @Override
                                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                                            //  holder.mProgressBar.setProgress(Math.round(100.0f * current / total));
                                        }
                                    });
                        }

                    } else {
                        CommonUtil.flip(mImageFlip, mImageUrl, 100);
                        mCountDownTimer = new CountDownTimer(1000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                CommonUtil.flip(mImageUrl, mImageFlip, 100);
                            }
                        }.start();

                        //Toast.makeText(mContext, "Incorrect Image", Toast.LENGTH_SHORT).show();
                    }


                    break;
            }
        }

    }

    public void alertMessage() {
        if (builder == null) {
            builder = new AlertDialog.Builder(mContext.getActivity());
            builder.setCancelable(false);
            builder.setMessage(Preferences.getPlayerName(mContext.getActivity()) + "Thanks :)");
            builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mContext.getActivity().onBackPressed();

                }
            });
            builder.show();
        }
    }

}

