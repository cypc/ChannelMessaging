package cyprien.clerc.channelmessaging.fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import cyprien.clerc.channelmessaging.R;

/**
 * Created by cclerc on 08/03/2016.
 */

public class ChannelDetailActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_detail);
		//String texteAAfficher = getIntent().getStringExtra("monTextAAfficher");
		ChannelFragment channelFragment = (ChannelFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_channel);
		//channelFragment.fillTextView(texteAAfficher);

	}
}