package cyprien.clerc.channelmessaging.channellist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cyprien.clerc.channelmessaging.channel.ChannelActivity;
import cyprien.clerc.channelmessaging.webservice.NetworkResultProvider;
import cyprien.clerc.channelmessaging.R;
import cyprien.clerc.channelmessaging.webservice.onWsRequestListener;

public class ChannelListActivity extends AppCompatActivity implements View.OnClickListener, onWsRequestListener, AdapterView.OnItemClickListener {

	private static final String CHANNEL_MESSAGING_PREFERENCES = "ChannelMessagingPreferences";
	private ListView lvMyListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_list);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Channel List");
		setSupportActionBar(toolbar);

		// token
		SharedPreferences settings = getSharedPreferences(CHANNEL_MESSAGING_PREFERENCES, 0);
		String token = settings.getString("accessToken", "");

		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("accesstoken", token));

		// request channel list
		NetworkResultProvider np = new NetworkResultProvider("http://www.raphaelbischof.fr/messaging/?function=getchannels", params, 0);
		np.setOnNewWsRequestListener(this);
		np.execute();

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onError(String error, int requestCode) {

	}

	@Override
	public void onCompleted(String response, int requestCode) {
		Gson gson = new Gson();
		GetChannelsResponse channelsResponse = gson.fromJson(response, GetChannelsResponse.class);

		//list view
		lvMyListView = (ListView) findViewById(R.id.myListView);
		lvMyListView.setAdapter(new ChannelAdapter(getApplicationContext(), channelsResponse.getChannels()));
		lvMyListView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		//channel list start
		Intent intent = new Intent(this, ChannelActivity.class);
		intent.putExtra("channelId", id+1);
		startActivity(intent);

	}
}
