package cyprien.clerc.channelmessaging.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;

import cyprien.clerc.channelmessaging.R;

/**
 * Created by cclerc on 08/03/2016.
 */

public class ChannelMainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		ChannelListFragment channelList = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.channel_List_fragment);
		ChannelFragment channel = (ChannelFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_channel);
		if(channel == null || !channel.isInLayout()){
			Intent i = new Intent(getApplicationContext(),ChannelDetailActivity.class);
			i.putExtra("channelId", id);
			startActivity(i);
		} else {
			//channel.fillTextView(channelList.listItems[position]);
		}
	}
}