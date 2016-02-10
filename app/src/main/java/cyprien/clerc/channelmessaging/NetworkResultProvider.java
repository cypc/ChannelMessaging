package cyprien.clerc.channelmessaging;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clerccy on 08/02/2016.
 */
public class NetworkResultProvider extends AsyncTask<String,Integer, String> {
	private ArrayList<onWsRequestListener> listeners = new ArrayList<onWsRequestListener>();
	private List<NameValuePair> nameValuePairs;
	String url = "";
	boolean errorOccurred = false;

	public NetworkResultProvider(String url, List<NameValuePair> nameValuePairs) {
		this.nameValuePairs = nameValuePairs;
		this.url = url;
	}

	@Override
	protected String doInBackground(String... arg0)  {

		String content = null;


		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		} catch (UnsupportedEncodingException e) {
			errorOccurred = true;
		}

		// Execute HTTP Post Request
		HttpResponse response = null;
		try {
			response = httpclient.execute(httppost);
			content = EntityUtils.toString(response.getEntity());
		} catch (IOException e) {
			errorOccurred = true;
		}

		return content;
	}

	@Override
	protected void onPostExecute(String s) {
		super.onPostExecute(s);
		if (errorOccurred)
			newWsRequestError(s);
		else
			newWsRequestCompleted(s);
	}

	public void setOnNewWsRequestListener(onWsRequestListener listener)
	{
		this.listeners.add(listener);
	}

	private void newWsRequestCompleted(String response){
		for(onWsRequestListener oneListener : listeners){
			oneListener.onCompleted(response);
		}
	}

	private void newWsRequestError(String error){
		for(onWsRequestListener oneListener : listeners){
			oneListener.onError(error);
		}
	}
}
