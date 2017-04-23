package com.bftv.knothing.firsttv;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import fragment.TimeCountFragment;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TimeCountActivity extends FragmentActivity implements TimeCountFragment.OnFragmentInteractionListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_count);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container, TimeCountFragment.newInstance(null));
//        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("KKK","fragment 识别了");
    }
}
