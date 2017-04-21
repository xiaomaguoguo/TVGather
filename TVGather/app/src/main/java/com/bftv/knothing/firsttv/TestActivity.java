package com.bftv.knothing.firsttv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by KNothing on 2017/4/15.
 */

public class TestActivity extends FragmentActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

//        findViewById(R.id.button6).setOnClickListener(this);
//        findViewById(R.id.button7).setOnClickListener(this);
//        findViewById(R.id.button8).setOnClickListener(this);
//        findViewById(R.id.button9).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

//            case R.id.button6:
//                break;
//
//            case R.id.button7:
//                break;
//
//            case R.id.button8:
//                break;
//
//            case R.id.button9:
//                break;


        }
    }
}
