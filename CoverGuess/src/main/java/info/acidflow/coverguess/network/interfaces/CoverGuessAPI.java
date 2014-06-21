package info.acidflow.coverguess.network.interfaces;

import java.util.List;

import info.acidflow.coverguess.datamodel.api.AlbumAPI;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by paul on 21/06/14.
 */
public interface CoverGuessAPI {

    @FormUrlEncoded
    @POST("/get_updates")
    public void getUpdates( @Field("timestamp") long timestamp, Callback<List<AlbumAPI>> cb );
}
