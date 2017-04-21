package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bftv.knothing.firsttv.R;

import java.util.ArrayList;

/**
 * Created by KNothing on 2017/4/15.
 * 美食适配器
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

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_detail, parent,false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
//        holder.btn.setText(datas.get(position));
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

        public MyHolder(View itemView) {
            super(itemView);
//            btn = (Button) itemView.findViewById(R.id.content_text);
        }
    }
}
