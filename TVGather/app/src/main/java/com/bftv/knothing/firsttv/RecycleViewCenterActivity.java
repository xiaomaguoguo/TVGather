package com.bftv.knothing.firsttv;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by KNothing on 2017/4/21.
 */

public class RecycleViewCenterActivity extends Activity {

    RecyclerView mRecycleView;

    RecycleViewAdapter mAdapter = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reeycleview_center);

        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(llm);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<String> datas = new ArrayList<>(100);
        for(int i=0;i<2;i++){
            datas.add(String.valueOf(i));
        }
        mAdapter = new RecycleViewAdapter(datas);
        mRecycleView.setAdapter(mAdapter);

        ctrlCenterLogic(2);

    }


    private void ctrlCenterLogic(final int centerCount) {

        mRecycleView.post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams lp = mRecycleView.getLayoutParams();
                int width = mRecycleView.getLayoutManager().getChildAt(0).getWidth();
                lp.width = width * centerCount ;
                mRecycleView.setLayoutParams(lp);
            }
        });
    }


}
