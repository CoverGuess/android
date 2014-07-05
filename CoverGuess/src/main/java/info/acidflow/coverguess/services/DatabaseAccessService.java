package info.acidflow.coverguess.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.activeandroid.query.Select;

import java.util.List;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.datamodel.Category;
import info.acidflow.coverguess.eventbus.events.DatabaseQueryListSuccessEvent;

/**
 * Created by paul on 05/07/14.
 */
public class DatabaseAccessService extends Service {

    public class DatabaseAccessBinder extends Binder {
        public DatabaseAccessService getService() {
            return DatabaseAccessService.this;
        }
    }

    private final DatabaseAccessBinder mDatabaseAccessBinder = new DatabaseAccessBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mDatabaseAccessBinder;
    }

    public void getCategories(final boolean distinct, final String orderBy ){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Select select = new Select();
                if( distinct ){
                    select.distinct();
                }
                List< Category > categories = select.from(Category.class).orderBy( orderBy ).execute();
                CoverGuess.getEventBus().post(new DatabaseQueryListSuccessEvent<Category>( categories ) );
            }
        }).start();
    }
}
