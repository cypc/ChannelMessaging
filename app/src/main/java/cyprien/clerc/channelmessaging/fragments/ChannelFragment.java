package cyprien.clerc.channelmessaging.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cyprien.clerc.channelmessaging.R;
import cyprien.clerc.channelmessaging.channel.GetMessagesResponse;
import cyprien.clerc.channelmessaging.channel.MessageAdapter;
import cyprien.clerc.channelmessaging.webservice.NetworkResultProvider;
import cyprien.clerc.channelmessaging.webservice.onWsRequestListener;

/**
 * Created by cclerc on 08/03/2016.
 */

public class ChannelFragment extends Fragment implements onWsRequestListener, AdapterView.OnItemClickListener {

	private static final int REQUEST_GET_MESSAGES = 0;
	private static final int REQUEST_SEND_MESSAGE = 1;
	private static final String CHANNEL_MESSAGING_PREFERENCES = "ChannelMessagingPreferences";

	private View v;
	private ListView messageList;
	private String token;
	public long channelId;

	private BroadcastReceiver receiver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup	container, Bundle savedInstanceState) {
		this.v = inflater.inflate(R.layout.fragment_channel, container);

		this.receiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				displayMessages();
			}
		};

		// token
		SharedPreferences settings = getActivity().getSharedPreferences(CHANNEL_MESSAGING_PREFERENCES, 0);
		this.token = settings.getString("accessToken", "");

		return v;
	}

	public void displayMessages() {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("accesstoken", token));
		params.add(new BasicNameValuePair("channelid", "" + channelId));

		NetworkResultProvider np = new NetworkResultProvider("http://www.raphaelbischof.fr/messaging/?function=getmessages", params, REQUEST_GET_MESSAGES);
		np.setOnNewWsRequestListener((onWsRequestListener) this);
		np.execute();
	}

	@Override
	public void onError(String error, int requestCode) {

	}

	@Override
	public void onCompleted(String response, int requestCode) {

		Gson gson =
				new Gson();

		if (requestCode == REQUEST_GET_MESSAGES) {
			GetMessagesResponse messagesResponse = gson.fromJson(response, GetMessagesResponse.class);

			//list view
			messageList = (ListView) this.v.findViewById(R.id.messageList);
			messageList.setAdapter(new MessageAdapter(getActivity().getApplicationContext(), messagesResponse.getMessages()));
			messageList.setOnItemClickListener(this);
		}

		if (requestCode == REQUEST_SEND_MESSAGE) {
			Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
			//displayMessages();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

	}

	@Override
	public void onResume() {
		super.onResume();

		IntentFilter filter = new IntentFilter("com.google.android.c2dm.intent.RECEIVE");

		getActivity().getApplicationContext().registerReceiver(this.receiver, filter);

	}
}