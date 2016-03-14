package cyprien.clerc.channelmessaging.channellist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cyprien.clerc.channelmessaging.R;

/**
 * Created by cclerc on 01/03/2016.
 */
public class ChannelAdapter extends BaseAdapter {

	private List<ChannelInfo> channels;

	private Context mContext;

	public ChannelAdapter(Context context, List<ChannelInfo> channels) {
		this.channels = channels;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return this.channels.size();
	}

	@Override
	public ChannelInfo getItem(int position) {
		return this.channels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

		TextView textChannelName = (TextView) rowView.findViewById(R.id.textChannelName);
		TextView textUserCount = (TextView) rowView.findViewById(R.id.textUserCount);

		textChannelName.setText(this.channels.get(position).name);
		textUserCount.setText("Nombre d'utilisateur connectés : " + this.channels.get(position).connectedusers);

		return rowView;
	}
}
