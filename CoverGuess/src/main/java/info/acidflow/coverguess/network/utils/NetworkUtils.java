package info.acidflow.coverguess.network.utils;

import android.net.Uri;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities methods to access network or do some network calls
 *
 * Created by acidflow on 19/01/14.
 */
public class NetworkUtils {

    public static final int HTTP_OK = 200;

    /**
     * An interface describing the callback to execute when an HTTP call is done
     */
    public interface HttpCallback{
        public void onSuccess(HttpResponse response);
        public void onError(Exception e);
    }

    /**
     * Execute asynchronously an HTTP call using POST method
     * @param url the url to call
     * @param requestArgs the parameters of the POST call
     * @param callback a Callback to execute when the call has finished or failed
     */
    public static void postDataAsync(final Uri url, final List<NameValuePair> requestArgs, final HttpCallback callback){
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                postData(url, requestArgs, callback);
                return null;
            }
        }.execute();
    }

    /**
     * Execute synchronously an HTTP call using POST method
     * @param url the url to call
     * @param requestArgs the parameters of the POST call
     * @param callback a Callback to execute when the call has finished or failed
     */
    public static void postData(Uri url, List<NameValuePair> requestArgs, HttpCallback callback) {
        // Create a new HttpClient and Post Header
        Exception raisedException = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url.toString());
        try {
            // Add your data
            HttpEntity httpEntity;
            if(requestArgs != null){
                httpEntity = new UrlEncodedFormEntity(requestArgs);
            }else{
                List<NameValuePair> emptyList = new ArrayList<NameValuePair>();
                emptyList.add(new BasicNameValuePair("", ""));
                httpEntity = new UrlEncodedFormEntity(emptyList);
            }
            httppost.setEntity(httpEntity);
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            if(callback != null){
                if(response.getStatusLine().getStatusCode() == HTTP_OK){
                    callback.onSuccess(response);
                }else{
                    callback.onError(null);
                }
            }
            return;
        } catch (ClientProtocolException e) {
            raisedException = e;
        } catch (IOException e) {
            raisedException = e;
        }
        if(callback != null){
            callback.onError(raisedException);
        }
    }
}
