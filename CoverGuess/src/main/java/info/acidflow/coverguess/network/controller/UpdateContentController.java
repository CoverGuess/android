package info.acidflow.coverguess.network.controller;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.AlbumDao;
import info.acidflow.coverguess.network.utils.NetworkUtils;
import info.acidflow.coverguess.utils.Constants;

/**
 * A controller class for updating the local database from the server
 * This class is a singleton
 *
 * Created by acidflow on 19/01/14.
 */
public class UpdateContentController {

    private static final String LOG_TAG = UpdateContentController.class.getSimpleName();
    private static UpdateContentController mInstance;

    private UpdateContentController(){
        super();
    }

    public static UpdateContentController getInstance(){
        if(mInstance == null){
            mInstance = new UpdateContentController();
        }
        return mInstance;
    }

    public void getUpdates(){
        NetworkUtils.postDataAsync(Constants.HTTP.GET_UPDATES, null, new NetworkUtils.HttpCallback() {
            @Override
            public void onSuccess(HttpResponse response) {
                try{
                    String responseBody = EntityUtils.toString(response.getEntity());
                    Log.i(LOG_TAG, responseBody);
                    parseUpdateResponse(responseBody);
                }catch(IOException e) {
                    Log.i(LOG_TAG, "Exception when getting content" + e.toString());
                }
            }

            @Override
            public void onError(Exception e) {
                if(e != null){
                    Log.e(LOG_TAG, e.toString());
                }else{
                    Log.e(LOG_TAG, "HTTP ERROR WITHOUT RAISED EXCEPTION");
                }
            }
        });
    }

    private void parseUpdateResponse(String response){
        try {
            AlbumDao albumDao = CoverGuess.getDaoMaster().newSession().getAlbumDao();
            JSONArray array = new JSONArray(response);
            for(int i = 0; i < array.length(); i++){
                JSONObject album = array.getJSONObject(i);
                Album a = new Album();
                a.setId(album.getLong("id"));
                a.setAlbum_artist(album.getString("album_artist"));
                a.setAlbum_title(album.getString("album_name"));
                a.setAlbum_last_updated(album.getLong("last_update"));
                a.setAlbum_cover_url(album.getString("image_url"));
                albumDao.insertOrReplace(a);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
