package com.downdemo.abhishekchandale.memorygamedemo;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.downdemo.abhishekchandale.memorygamedemo.fragment.PuzzleGridFragment;
import com.downdemo.abhishekchandale.memorygamedemo.fragment.UserInfoFragment;
import com.downdemo.abhishekchandale.memorygamedemo.util.Preferences;

public class MainActivity extends AppCompatActivity {
    private FrameLayout mFrameLayout;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Pic Scramble");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_settings) {

                }
                return false;
            }
        });

        callFragment();
    }

    public void callFragment() {
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (Preferences.getUserLoggedIn(getApplicationContext()) == true) {
            PuzzleGridFragment puzzleGridFragment = new PuzzleGridFragment();
            if (puzzleGridFragment != null) {
                mFragmentTransaction.replace(R.id.frame_main, puzzleGridFragment, PuzzleGridFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(PuzzleGridFragment.class.getSimpleName());
                mFragmentTransaction.commit();
            } else {
                Log.d("", "Fragment null");
            }
        } else {
            UserInfoFragment userInfoFragment = new UserInfoFragment();
            if (userInfoFragment != null) {
                mFragmentTransaction.replace(R.id.frame_main, userInfoFragment, UserInfoFragment.class.getSimpleName());
                mFragmentTransaction.addToBackStack(UserInfoFragment.class.getSimpleName());
                mFragmentTransaction.commit();
            } else {
                Log.d("", "Fragment null");
            }
        }

    }


}
