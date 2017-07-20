package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bftv.knothing.firsttv.R;

import java.util.List;
import java.util.Map;

public class DownLoadAdapter extends BaseAdapter{
	 
	private LayoutInflater mInflater;
	private List<Map<String, String>> data;

	public DownLoadAdapter(Context context,List<Map<String, String>> data) {
		mInflater = LayoutInflater.from(context);
		this.data=data;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Map<String, String> bean=data.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder(); 
			holder.resouceName=(TextView) convertView.findViewById(R.id.tv_resouce_name);
			holder.startDownload=(Button) convertView.findViewById(R.id.btn_start);
			holder.pauseDownload=(Button) convertView.findViewById(R.id.btn_pause);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		} 
	 	holder.resouceName.setText(bean.get("name")); 
		return convertView;
	}

	private class ViewHolder {
        TextView resouceName;
        Button startDownload;
        Button pauseDownload;

    }
	
}
