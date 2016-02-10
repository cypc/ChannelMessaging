package cyprien.clerc.channelmessaging;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class ChannelListActivity extends AppCompatActivity {

	private static final String CHANNEL_MESSAGING_PREFERENCES = "ChannelMessagingPreferences";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channel_list);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Channel List");
		setSupportActionBar(toolbar);
		//getSupportActionBar().setTitle("test");

		SharedPreferences settings = getSharedPreferences(CHANNEL_MESSAGING_PREFERENCES, 0);
		String token = settings.getString("accessToken", "");

		Toast.makeText(getApplicationContext(), token, Toast.LENGTH_LONG).show();

	}

}
