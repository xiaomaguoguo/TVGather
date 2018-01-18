package adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;

import java.util.ArrayList;

import utils.FocusUtils;

/**
 * Created by KNothing on 2017/4/14.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyHolder> {

    private ArrayList<String> datas = null;

    public RecycleViewAdapter(ArrayList<String> datas){
        this.datas = datas;
    }

    public void addItem(String item,int position){
        datas.add(position,item);
        notifyItemInserted(position);
    }

    public void removeItem(int position){
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public void changeItem(int position){
        notifyItemChanged(position);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = View.inflate(parent.getContext(),R.layout.item_contenet,null);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contenet, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.btn.setText(datas.get(position));
        Log.i("KKK","onBindViewHolder");
        holder.btn.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                FocusUtils.onFocusChangeStill(v,hasFocus);
                float scaleValue = hasFocus?  2.0f : 1.0f;
                v.animate().scaleX(scaleValue).scaleY(scaleValue).setDuration(150).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas == null || datas.isEmpty() ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    public static class MyHolder extends RecyclerView.ViewHolder{

        public Button btn;

        public MyHolder(View itemView) {
            super(itemView);
            btn = (Button) itemView.findViewById(R.id.content_text);
        }
    }
}
