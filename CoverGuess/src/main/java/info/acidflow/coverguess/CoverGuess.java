package info.acidflow.coverguess;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.activeandroid.ActiveAndroid;

import de.greenrobot.event.EventBus;
import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.Category;
import info.acidflow.coverguess.eventbus.events.DatabaseAccessServiceReadyEvent;
import info.acidflow.coverguess.network.interfaces.CoverGuessAPI;
import info.acidflow.coverguess.services.DatabaseAccessService;
import info.acidflow.coverguess.services.connections.DatabaseAccessServiceConnection;
import info.acidflow.coverguess.utils.Constants;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Override the Application class to manage object lifecycle
 * 
 * Created by acidflow on 18/01/14.
 */
public class CoverGuess extends com.activeandroid.app.Application {

    private static Context mApplicationContext;
    private static EventBus sEventBus;
    private static DatabaseAccessService mDatabaseService;
    private boolean isDatabaseServiceBound = false;
    private DatabaseAccessServiceConnection mDatabaseServiceConnection;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        sEventBus = new EventBus();
        sEventBus.registerSticky( this );
        Constants.CONFIGURATION.initialize(getApplicationContext());
        mDatabaseServiceConnection = new DatabaseAccessServiceConnection();
        bindDatabaseService();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepopulateDb();
            }
        }).start();

    }

    private void bindDatabaseService(){
        bindService(new Intent( this, DatabaseAccessService.class), mDatabaseServiceConnection, Context.BIND_AUTO_CREATE);
        isDatabaseServiceBound = true;
    }

    private void unbindDatabaseService(){
        if (isDatabaseServiceBound) {
            unbindService( mDatabaseServiceConnection );
            isDatabaseServiceBound = false;
        }
    }

    /**
     * Easy access to the application context
     * @return the application context
     */
    public static Context getContext(){
        return mApplicationContext;
    }

    public static EventBus getEventBus(){
        return sEventBus;
    }

    public static DatabaseAccessService getDatabaseService(){
        return mDatabaseService;
    }


    @Override
    public void onTerminate() {
        unbindDatabaseService();
        sEventBus.unregister(this);
        sEventBus = null;
        mApplicationContext = null;
        mDatabaseService = null;
        mDatabaseServiceConnection = null;
        super.onTerminate();

    }

    public void onEvent( DatabaseAccessServiceReadyEvent event ) {
        Log.i("CoverGuess", "Event received from the event bus");
        mDatabaseService = event.getService();
    }

    private void prepopulateDb(){
        Category cat = new Category("Testing");
        cat.save();
        Album[] albums = new Album[]{
                new Album(1l, "the dark side of the moon", "pink floyd", "http://ecx.images-amazon.com/images/I/31UZQMqbb-L.jpg"),
                new Album(2l, "the best of 1990-2000", "U2", "http://ecx.images-amazon.com/images/I/410r4X-xVCL.jpg"),
                new Album(3l, "best of pixies wave of mutilation", "the pixies", "http://ecx.images-amazon.com/images/I/51c9%2BsQdUuL.jpg"),
                new Album(4l, "toxicity", "system of a down", "http://ecx.images-amazon.com/images/I/618pvPqV-1L.jpg"),
                new Album(5l, "nevermind", "nirvana", "http://ecx.images-amazon.com/images/I/51tr3o4kd9L.jpg")
        };
        ActiveAndroid.beginTransaction();
        try {
                for (int i = 0; i < albums.length; i++) {
                    albums[i].setCategory(cat);
                    albums[i].save();
                }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally {
            ActiveAndroid.endTransaction();
        }
    }

}
