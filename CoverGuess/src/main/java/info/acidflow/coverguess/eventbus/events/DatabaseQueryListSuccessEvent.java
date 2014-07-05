package info.acidflow.coverguess.eventbus.events;

import com.activeandroid.Model;

import java.util.List;

/**
 * Created by paul on 05/07/14.
 */
public class DatabaseQueryListSuccessEvent<T extends Model> {

    List<T> mQueryResults;
    public DatabaseQueryListSuccessEvent(List<T> results ){
        super();
        mQueryResults = results;
    }

    public List<T> getQueryResults(){
        return mQueryResults;
    }
}
