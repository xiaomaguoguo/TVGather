package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bftv.knothing.firsttv.R;

import java.util.ArrayList;

import fragment.TimeCountFragment;
import widget.CountTimeView;

/**
 * Created by KNothing on 2017/4/23.
 * 美食适配器
 */
public class TimeCountViewAdapter extends RecyclerView.Adapter<TimeCountViewAdapter.MyHolder> {

    private ArrayList<TimeCountFragment.TimeTaskBean> datas = null;

    public TimeCountViewAdapter(ArrayList<TimeCountFragment.TimeTaskBean> datas){
        this.datas = datas;
    }

    public void addItem(TimeCountFragment.TimeTaskBean item, int position){
        datas.add(position,item);
        notifyItemInserted(position);
    }

    public void removeItem(int position){
        datas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_count, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
//        holder.btn.setText(datas.get(position));
        if(!datas.get(position).isPauseAnim){
            holder.ctv.setCountTime(Integer.valueOf(datas.get(position).time));
            holder.ctv.startCountTimeAnimation();
        }
    }

    @Override
    public int getItemCount() {
        return datas == null || datas.isEmpty() ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    static class MyHolder extends RecyclerView.ViewHolder{

//        Button btn;
        CountTimeView ctv;

        public MyHolder(View itemView) {
            super(itemView);
//            btn = (Button) itemView.findViewById(R.id.content_text);
            ctv = (CountTimeView) itemView.findViewById(R.id.countTimeProgressView);
        }
    }
}
