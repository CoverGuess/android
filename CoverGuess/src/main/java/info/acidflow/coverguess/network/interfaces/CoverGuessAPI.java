package info.acidflow.coverguess.network.interfaces;

import java.util.List;

import info.acidflow.coverguess.datamodel.Album;
import retrofit.http.Field;
import retrofit.http.POST;

/**
 * Created by paul on 21/06/14.
 */
public interface CoverGuessAPI {

    @POST("/get_updates")
    public List<Album> getUpdates( @Field("timestamp") long timestamp );
}
