package cyprien.clerc.channelmessaging.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cyprien.clerc.channelmessaging.R;

/**
 * Created by cclerc on 08/03/2016.
 */

public class ChannelFragment extends Fragment {
	//private TextView tvExample;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup	container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_channel, container);
		//tvExample = (TextView)v.findViewById(R.id.textView);
		return v;
	}
	public void fillTextView(String listItem) {
		//tvExample.setText(listItem);
	}
}