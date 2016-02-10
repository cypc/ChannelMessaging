package cyprien.clerc.channelmessaging;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, onWsRequestListener {

	private EditText txt_id;
	private EditText txt_password;
	private TextView label_id;
	private TextView label_password;
	private Button button_submit;

	public static final String CHANNEL_MESSAGING_PREFERENCES = "ChannelMessagingPreferences";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		txt_id = (EditText) findViewById(R.id.editText_id);
		txt_password = (EditText) findViewById(R.id.editText_password);
		label_id = (TextView) findViewById(R.id.textView_id);
		label_password = (TextView) findViewById(R.id.textView_password);
		button_submit = (Button) findViewById(R.id.button_submit);

		button_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("username", txt_id.getText().toString()));
		params.add(new BasicNameValuePair("password", txt_password.getText().toString()));

		NetworkResultProvider np = new NetworkResultProvider("http://www.raphaelbischof.fr/messaging/?function=connect", params);
		np.setOnNewWsRequestListener(this);
		np.execute();
	}

	@Override
	public void onError(String error) {
		Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCompleted(String response) {
		Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

		Gson gson = new Gson();
		ConnectResponse connectResponse = gson.fromJson(response, ConnectResponse.class);

		//Toast.makeText(getApplicationContext(), connectResponse.code, Toast.LENGTH_LONG).show();

		if (connectResponse.code.equals("200")) {
			// shared pref insert
			SharedPreferences settings = getSharedPreferences(CHANNEL_MESSAGING_PREFERENCES, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("accessToken", connectResponse.accesstoken);
			editor.commit();

			//Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();

			//channel list start
			Intent intent = new Intent(this, ChannelListActivity.class);
			startActivity(intent);
		}


	}

}