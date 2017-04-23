package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bftv.knothing.firsttv.R;

import java.util.ArrayList;

import adapter.CateRecycleViewAdapter;

/**
 * Created by KNothing on 2017/4/15.
 */
public class CateFragment extends Fragment {

    private RecyclerView mRecycleView ;

    private CateRecycleViewAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.cate_fragment,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(llm);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        ArrayList<String> datas = new ArrayList<>(100);
        for(int i=0;i<100;i++){
            datas.add(String.valueOf(i));
        }
        mAdapter = new CateRecycleViewAdapter(datas);
        mRecycleView.setAdapter(mAdapter);

    }
}
