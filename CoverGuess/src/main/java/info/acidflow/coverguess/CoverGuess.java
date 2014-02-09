package info.acidflow.coverguess;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.Category;
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
        initializeDebugDatabase();
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

    private static void initializeDebugDatabase(){
        try{
        sDaoMaster.newSession().getCategoryDao().insertInTx(new Category[]{
                new Category(new Long(1), "TOP100 UK"),
                new Category(new Long(2), "TOP100 FR"),
                new Category(new Long(3), "HARDSTYLE")
        });
        }catch (Exception e){};

        try{
        sDaoMaster.newSession().getAlbumDao().insertInTx(
                new Album[]{
                        new Album(1l, "T1", "A1", "URL1", 0l),
                        new Album(2l, "T2", "A2", "URL2", 0l),
                        new Album(3l, "T3", "A3", "URL3", 0l),
                        new Album(4l, "T4", "A4", "URL4", 0l),
                        new Album(5l, "T5", "A5", "URL5", 0l),
                        new Album(6l, "T6", "A6", "URL6", 0l),
                        new Album(7l, "T7", "A7", "URL7", 0l),
                        new Album(8l, "T8", "A8", "URL8", 0l),
                        new Album(9l, "T9", "A9", "URL9", 0l),
                        new Album(10l, "T10", "A10", "URL10", 0l)
                }
        );
        }catch (Exception e){}
    }
}
