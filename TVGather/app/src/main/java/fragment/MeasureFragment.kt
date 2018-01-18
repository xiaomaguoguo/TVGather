package fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bftv.knothing.firsttv.R

/**
 * Created by MaZhihua on 2017/12/4.
 */
class MeasureFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.measure_layout,container,false);
    }


    companion object {

        fun newInstance(): MeasureFragment {
            val args = Bundle()
            val fragment = MeasureFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
