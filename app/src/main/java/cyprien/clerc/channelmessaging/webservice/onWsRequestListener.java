package cyprien.clerc.channelmessaging.webservice;

/**
 * Created by clerccy on 08/02/2016.
 */
public interface onWsRequestListener {

	public void onError(String error, int requestCode);

	public void onCompleted(String response, int requestCode);
}
