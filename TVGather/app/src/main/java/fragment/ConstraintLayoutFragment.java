package fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bftv.knothing.firsttv.R;

/**
 * 约束布局测试
 */
public class ConstraintLayoutFragment extends Fragment {

    public static ConstraintLayoutFragment newInstance() {
        ConstraintLayoutFragment fragment = new ConstraintLayoutFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.constraint_layout, container, false);
    }

}
