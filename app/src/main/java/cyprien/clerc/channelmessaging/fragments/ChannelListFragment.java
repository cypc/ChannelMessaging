package cyprien.clerc.channelmessaging.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import cyprien.clerc.channelmessaging.R;
import cyprien.clerc.channelmessaging.channellist.ChannelAdapter;
import cyprien.clerc.channelmessaging.channellist.GetChannelsResponse;
import cyprien.clerc.channelmessaging.webservice.NetworkResultProvider;
import cyprien.clerc.channelmessaging.webservice.onWsRequestListener;

/**
 * Created by cclerc on 07/03/2016.
 */

public class ChannelListFragment extends Fragment implements onWsRequestListener {

	private static final String CHANNEL_MESSAGING_PREFERENCES = "ChannelMessagingPreferences";
	private ListView lvMyListView;
	private View v;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.v = inflater.inflate(R.layout.fragment_channel_list, container);
		//lvFragment = (ListView)v.findViewById(R.id.lvFragment);

		// token
		SharedPreferences settings = getActivity().getSharedPreferences(CHANNEL_MESSAGING_PREFERENCES, 0);
		String token = settings.getString("accessToken", "");

		List<NameValuePair> params = new ArrayList<NameValuePair>(1);
		params.add(new BasicNameValuePair("accesstoken", token));

		// request channel list
		NetworkResultProvider np = new NetworkResultProvider("http://www.raphaelbischof.fr/messaging/?function=getchannels", params, 0);
		np.setOnNewWsRequestListener(this);
		np.execute();

		return v;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//lvFragment.setAdapter(new ArrayAdapter(getActivity(),
		//android.R.layout.simple_list_item_1, listItems));
		//lvFragment.setOnItemClickListener((MainActivity)getActivity());
	}

	@Override
	public void onError(String error, int requestCode) {

	}

	@Override
	public void onCompleted(String response, int requestCode) {
		Gson gson = new Gson();
		GetChannelsResponse channelsResponse = gson.fromJson(response, GetChannelsResponse.class);

		//list view
		lvMyListView = (ListView) this.v.findViewById(R.id.myListView);
		//lvFragment = (ListView)v.findViewById(R.id.lvFragment);
		lvMyListView.setAdapter(new ChannelAdapter(getActivity().getApplicationContext(), channelsResponse.getChannels()));
		//lvMyListView.setOnItemClickListener(this);
	}

}


