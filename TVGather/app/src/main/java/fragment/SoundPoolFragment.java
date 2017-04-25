package fragment;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;

import utils.SoundPoolUtils;

/**
 * Created by MaZhihua on 2017/4/25.
 * 声音池测试
 */
public class SoundPoolFragment extends Fragment implements View.OnClickListener{


    private Button btnInit,btnPlay,btnStop,btnRelease;

    private SoundPoolUtils mSpInstance;

    public static SoundPoolFragment newInstance() {
        SoundPoolFragment fragment = new SoundPoolFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_soundpool,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnInit = (Button) view.findViewById(R.id.btnInit);
        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnRelease = (Button) view.findViewById(R.id.btnRelease);
        btnPlay.setOnClickListener(this);
        btnInit.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnRelease.setOnClickListener(this);

        mSpInstance = SoundPoolUtils.getInstance(getActivity().getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnInit:
                mSpInstance.initSoundPool();
                break;

            case R.id.btnPlay:
                mSpInstance.play();
                break;

            case R.id.btnStop:
                mSpInstance.stop();
                break;

            case R.id.btnRelease:
                mSpInstance.release();
                break;

        }
    }

}
