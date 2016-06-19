package com.downdemo.abhishekchandale.memorygamedemo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;

import com.downdemo.abhishekchandale.memorygamedemo.model.CustomImageModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 6/15/2016.
 */

public class CommonUtil {

    public static final String API_URL = "https://api.flickr.com/";

    public static void flip(final View front, final View back, final int duration) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            AnimatorSet set = new AnimatorSet();
            set.playSequentially(
                    ObjectAnimator.ofFloat(front, "rotationY", 90).setDuration(duration / 2),
                    ObjectAnimator.ofInt(front, "visibility", View.GONE).setDuration(0),
                    ObjectAnimator.ofFloat(back, "rotationY", -90).setDuration(0),
                    ObjectAnimator.ofInt(back, "visibility", View.VISIBLE).setDuration(0),
                    ObjectAnimator.ofFloat(back, "rotationY", 0).setDuration(duration / 2));
            set.start();
        } else {
            front.animate().rotationY(90).setDuration(duration / 2)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            front.setVisibility(View.GONE);
                            back.setRotationY(-90);
                            back.setVisibility(View.VISIBLE);
                            back.animate().rotationY(0).setDuration(duration / 2).setListener(null);
                        }
                    });
        }
    }

    public static ArrayList<CustomImageModel> shuffleArray(ArrayList<CustomImageModel> ar) {
        ar = new ArrayList<CustomImageModel>();
        Random rnd = new Random();
        for (int i = ar.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            CustomImageModel a = ar.get(index);
            ar.set(index, ar.get(i));
            ar.set(i, a);
        }
        return ar;

    }



}
