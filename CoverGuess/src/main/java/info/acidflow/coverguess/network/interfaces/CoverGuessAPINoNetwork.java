package info.acidflow.coverguess.network.interfaces;

import java.util.List;

import info.acidflow.coverguess.datamodel.api.AlbumAPI;
import retrofit.Callback;
import retrofit.http.Field;

/**
 * Created by paul on 06/07/14.
 */
public class CoverGuessAPINoNetwork implements CoverGuessAPI {

    @Override
    public void getUpdates(@Field("timestamp") long timestamp, Callback<List<AlbumAPI>> cb) {

    }
}
