package fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;

import java.util.ArrayList;

import adapter.TimeCountViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimeCountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimeCountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeCountFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecycleView ;

    private TimeCountViewAdapter mAdapter;

    private ArrayList<TimeTaskBean> datas;

    public TimeCountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TimeCountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeCountFragment newInstance(Bundle args) {
        TimeCountFragment fragment = new TimeCountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recycleview_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycleView = (RecyclerView) view.findViewById(R.id.recycleView);
        Button btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItem(2);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecycleView.setLayoutManager(llm);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        datas = new ArrayList<>(50);
        for(int i=0;i<50;i++){
            TimeTaskBean ttb = new TimeTaskBean();
            ttb.time = "5000";
            datas.add(ttb);
        }
        mAdapter = new TimeCountViewAdapter(datas);
        mRecycleView.setAdapter(mAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            if(mListener != null){
                mListener.onFragmentInteraction(null);
            }
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateItem(int position){
//        datas.remove(position);
//        TimeTaskBean ttb = new TimeTaskBean();
//        ttb.time = "10000";
//        ttb.isPauseAnim = true;
//        datas.add(position,ttb);
//        datas.get(position).isPauseAnim = true;
//        datas.get(position).time = "100000";
        datas.get(position).time = "462574";
        mAdapter.notifyItemChanged(position);
    }

    public class TimeTaskBean {
        public String time;
        public boolean isPauseAnim;
    }
}
