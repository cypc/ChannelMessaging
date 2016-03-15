package cyprien.clerc.channelmessaging.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import cyprien.clerc.channelmessaging.R;
import cyprien.clerc.channelmessaging.webservice.onWsRequestListener;

/**
 * Created by cclerc on 08/03/2016.
 */

public class ChannelMainActivity extends AppCompatActivity implements View.OnClickListener, onWsRequestListener, AdapterView.OnItemClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
	}

	@Override
	public void onClick(View view) {

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Toast.makeText(getApplicationContext(), "plouf", Toast.LENGTH_LONG).show();

		ChannelListFragment channelList = (ChannelListFragment)getSupportFragmentManager().findFragmentById(R.id.channel_List_fragment);
		ChannelFragment channel = (ChannelFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_channel);
		if(channel == null || !channel.isInLayout()){
			Intent i = new Intent(getApplicationContext(),ChannelDetailActivity.class);
			i.putExtra("channelId",id+1);
			startActivity(i);
		} else {
			Intent i = new Intent(getApplicationContext(),ChannelFragment.class);
			i.putExtra("channelId",id+1);
			startActivity(i);
		}
	}

	@Override
	public void onError(String error, int requestCode) {

	}

	@Override
	public void onCompleted(String response, int requestCode) {

	}
}
