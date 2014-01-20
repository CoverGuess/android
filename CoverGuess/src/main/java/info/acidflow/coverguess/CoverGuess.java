package info.acidflow.coverguess;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.acidflow.coverguess.datamodel.DaoMaster;
import info.acidflow.coverguess.utils.Constants;

/**
 * Override the Application class to manage object lifecycle
 * 
 * Created by acidflow on 18/01/14.
 */
public class CoverGuess extends Application {

    private static Context mApplicationContext;
    private static DaoMaster sDaoMaster;
    private static SQLiteOpenHelper sDatabaseHelper;
    private static SQLiteDatabase sDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        Constants.CONFIGURATION.initialize(getApplicationContext());
        initializeDatabase();
    }

    /**
     * initialize the database and the DaoMaster used to query the database
     */
    private void initializeDatabase(){
        sDatabaseHelper = new DaoMaster.DevOpenHelper(this, Constants.DATABASE.NAME, null);
        sDatabase = sDatabaseHelper.getWritableDatabase();
        sDaoMaster = new DaoMaster(sDatabase);
    }

    /**
     * Easy access to the application context
     * @return the application context
     */
    public static Context getContext(){
        return mApplicationContext;
    }

    /**
     * get the DaoMaster instance
     * @return the DaoMaster instance
     */
    public static DaoMaster getDaoMaster(){
        return sDaoMaster;
    }
}
