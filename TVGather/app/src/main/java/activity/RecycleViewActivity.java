package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;
import adapter.RecycleViewAdapter;

import java.util.ArrayList;

/**
 * Created by KNothing on 2017/4/23.
 */
public class RecycleViewActivity extends Activity implements View.OnClickListener {

    private Button btn1,btn2,btn3,add,remove,btnCenter;

    RecyclerView mRecycleView;

    RecycleViewAdapter mAdapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycleview);
        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        add = (Button) findViewById(R.id.button4);
        remove = (Button) findViewById(R.id.button5);
        btnCenter = (Button) findViewById(R.id.btnCenter);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        add.setOnClickListener(this);
        remove.setOnClickListener(this);
        btnCenter.setOnClickListener(this);

        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(llm);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<String> datas = new ArrayList<>(100);
        for(int i=0;i<100;i++){
            datas.add(String.valueOf(i));
        }
        mAdapter = new RecycleViewAdapter(datas);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

        RecyclerView.LayoutManager lp = null;

        switch (v.getId()){
            case R.id.button:
                lp = new LinearLayoutManager(this);
                break;

            case R.id.button2:
                lp = new GridLayoutManager(this,4);
                break;

            case R.id.button3:
                lp = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                break;

            case R.id.button4:
                mAdapter.addItem("添加",2);
                break;

            case R.id.button5:
                mAdapter.removeItem(2);
                break;

            case R.id.btnCenter: // item居中
                Intent center = new Intent(this,RecycleViewCenterActivity.class);
                startActivity(center);
                break;
        }

        mRecycleView.setLayoutManager(lp);
    }
}
