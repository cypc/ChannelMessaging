package cyprien.clerc.channelmessaging.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

public class ChannelDetailActivity extends ActionBarActivity implements View.OnClickListener, onWsRequestListener, AdapterView.OnItemClickListener {

	private static final String CHANNEL_MESSAGING_PREFERENCES = "ChannelMessagingPreferences";
	private String token;
	private long channelId;
	private static final int REQUEST_GET_MESSAGES = 0;
	private static final int REQUEST_SEND_MESSAGE = 1;

	private EditText textMessage;
	private Button buttonEnvoyer;

	private ListView messageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel);
		//String texteAAfficher = getIntent().getStringExtra("monTextAAfficher");
		ChannelFragment channelFragment = (ChannelFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_channel);
		//channelFragment.fillTextView(texteAAfficher);

		textMessage = (EditText) findViewById(R.id.textMessage);
		buttonEnvoyer = (Button) findViewById(R.id.buttonEnvoyer);
		buttonEnvoyer.setOnClickListener(this);
		this.channelId = getIntent().getLongExtra("channelId", 0);

		// token
		SharedPreferences settings = getSharedPreferences(CHANNEL_MESSAGING_PREFERENCES, 0);
		this.token = settings.getString("accessToken", "");

		displayMessages();
	}

	private void displayMessages () {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("accesstoken", token));
		params.add(new BasicNameValuePair("channelid", "" + this.channelId));

		NetworkResultProvider np = new NetworkResultProvider("http://www.raphaelbischof.fr/messaging/?function=getmessages", params, REQUEST_GET_MESSAGES);
		np.setOnNewWsRequestListener(this);
		np.execute();
	}

	@Override
	public void onClick(View v) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(3);
		params.add(new BasicNameValuePair("accesstoken", this.token));
		params.add(new BasicNameValuePair("channelid", "" + this.channelId));
		params.add(new BasicNameValuePair("message", textMessage.getText().toString()));

		NetworkResultProvider np = new NetworkResultProvider("http://www.raphaelbischof.fr/messaging/?function=sendmessage", params, REQUEST_SEND_MESSAGE);
		np.setOnNewWsRequestListener(this);
		np.execute();
	}

	@Override
	public void onError(String error, int requestCode) {

	}

	@Override
	public void onCompleted(String response, int requestCode) {

		Gson gson = new Gson();

		if (requestCode == REQUEST_GET_MESSAGES) {
			GetMessagesResponse messagesResponse = gson.fromJson(response, GetMessagesResponse.class);

			//Toast.makeText(getApplicationContext(), messagesResponse.getMessages(), Toast.LENGTH_LONG).show();
			//list view
			messageList = (ListView) findViewById(R.id.messageList);
			messageList.setAdapter(new MessageAdapter(getApplicationContext(), messagesResponse.getMessages()));
			messageList.setOnItemClickListener(this);
		}

		if (requestCode == REQUEST_SEND_MESSAGE) {
			Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
			displayMessages();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

}
