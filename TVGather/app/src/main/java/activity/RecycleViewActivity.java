package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;
import adapter.RecycleViewAdapter;
import utils.PageUtils;

import java.util.ArrayList;

/**
 * Created by KNothing on 2017/4/23.
 */
public class RecycleViewActivity extends Activity implements View.OnClickListener {

    private Button btn1,btn2,btn3,add,remove,btnCenter,update,btnLinearSnap,btnPagerSnap,btnNextPage,btnPrePage;

    RecyclerView mRecycleView;

    RecycleViewAdapter mAdapter = null;

    ArrayList<String> datas;

    RecyclerView.LayoutManager lp = null;

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
        update = (Button) findViewById(R.id.update);
        btnLinearSnap = (Button) findViewById(R.id.btnLinearSnap);
        btnPagerSnap = (Button) findViewById(R.id.btnPagerSnap);
        btnNextPage = (Button) findViewById(R.id.btnNextPage);
        btnPrePage = (Button) findViewById(R.id.btnPrePage);

        update.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        add.setOnClickListener(this);
        remove.setOnClickListener(this);
        btnCenter.setOnClickListener(this);
        btnLinearSnap.setOnClickListener(this);
        btnPagerSnap.setOnClickListener(this);
        btnNextPage.setOnClickListener(this);
        btnPrePage.setOnClickListener(this);

        mRecycleView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        GridLayoutManager glm = new GridLayoutManager(this,4);
//        lp = glm;
        lp = llm;
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(llm);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        datas = new ArrayList<>(100);
        for(int i=0;i<101;i++){
            datas.add(String.valueOf(i));
        }
        mAdapter = new RecycleViewAdapter(datas);
        mRecycleView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.update: //更新某个位置
//                mAdapter.notifyItemChanged(2);
//                View view  = mRecycleView.getChildAt(2);
//                Button btn = (Button) view.findViewById(R.id.content_text);
//                RecycleViewAdapter.MyHolder  holder = (RecycleViewAdapter.MyHolder) mRecycleView.findViewHolderForAdapterPosition(2);
//                btn.setText("可以改");

                updateListItem(2);

                break;

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

            case R.id.btnLinearSnap: //LinearSnapHelper使用
                LinearSnapHelper snapHelper = new LinearSnapHelper();
                snapHelper.attachToRecyclerView(mRecycleView);
                break;

            case R.id.btnPagerSnap: //LinearSnapHelper使用
                PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                pagerSnapHelper.attachToRecyclerView(mRecycleView);
                break;

            case R.id.btnNextPage://下一页
                PageUtils.nextPage(mRecycleView);
                break;

            case R.id.btnPrePage://上一页
                PageUtils.prePage(mRecycleView);
                break;

                default:
                    break;
        }

        mRecycleView.setLayoutManager(lp);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LinearLayoutManager llm = (LinearLayoutManager) mRecycleView.getLayoutManager();
        switch (keyCode){

            case KeyEvent.KEYCODE_DPAD_LEFT://执行上一页的逻辑
                if(llm != null){
//                    View focusChild = mRecycleView.getFocusedChild();
//                    int[] outLocation = new int[2];
//                    focusChild.getLocationInWindow(outLocation);
//                    if(outLocation[0] == 0 || outLocation[0] <focusChild.getWidth()){
//                        prePage();
//                    }
                }
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT: //执行下一页的逻辑
//                View focusChild2 = mRecycleView.getFocusedChild();
//                int[] outLocation2 = new int[2];
//                if(outLocation2[0] + focusChild2.getWidth() == mRecycleView.getWidth() || outLocation2[0] <outLocation2.getWidth()){
//                    prePage();
//                }
                break;

            default:
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    private void updateListItem(int position) {
//        View view = lp.findViewByPosition(position);
//        Button btn = (Button) view.findViewById(R.id.content_text);
//        btn.setText("哈哈");
        datas.remove(2);
        datas.add(2,"可以");
        mAdapter.notifyItemChanged(position);
    }

}
