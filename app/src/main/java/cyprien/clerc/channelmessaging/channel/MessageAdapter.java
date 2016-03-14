package cyprien.clerc.channelmessaging.channel;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import cyprien.clerc.channelmessaging.R;
import cyprien.clerc.channelmessaging.channellist.ChannelInfo;
import cyprien.clerc.channelmessaging.transformation.CircleTransformation;

/**
 * Created by cclerc on 01/03/2016.
 */
public class MessageAdapter extends BaseAdapter {

	private List<MessageInfo> messages;

	private Context mContext;

	public MessageAdapter(Context context, List<MessageInfo> messages) {
		this.messages = messages;
		this.mContext = context;
		//Toast.makeText(this.mContext, "" + messages, Toast.LENGTH_LONG).show();
	}

	@Override
	public int getCount() {
		return this.messages.size();
	}

	@Override
	public Object getItem(int position) {
		return this.messages.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowViewMessage = inflater.inflate(R.layout.rowlayoutmessage, parent, false);

		TextView textUser = (TextView) rowViewMessage.findViewById(R.id.textUser);
		TextView textMessage = (TextView) rowViewMessage.findViewById(R.id.textMessage);
		TextView textDate = (TextView) rowViewMessage.findViewById(R.id.textDate);
		ImageView pp = (ImageView) rowViewMessage.findViewById(R.id.profilePicture);



		textUser.setText(this.messages.get(position).username + " : ");
		textMessage.setText(this.messages.get(position).message);
		textDate.setText(this.messages.get(position).date);
		String url = this.messages.get(position).imageUrl;

		Picasso.with(mContext).load(url).transform(new CircleTransformation()).into(pp);


//		try {
//			InputStream is = (InputStream) new URL(url).getContent();
//			Drawable d = Drawable.createFromStream(is, "src name");
//			pp.setImageDrawable(d);
//		} catch (IOException e) {
//			//e.printStackTrace();
//			Toast.makeText(mContext, "fail", Toast.LENGTH_LONG).show();
//		}


		return rowViewMessage;
	}
}
