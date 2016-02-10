package cyprien.clerc.channelmessaging;

/**
 * Created by clerccy on 08/02/2016.
 */
public interface onWsRequestListener {

	public void onError(String error);
	public void onCompleted(String response);
}
