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
                        new Album(null, 1l, "the dark side of the moon", "pink floyd", "http://ecx.images-amazon.com/images/I/31UZQMqbb-L.jpg", 0l),
                        new Album(null, 2l, "the best of 1990-2000", "U2", "http://ecx.images-amazon.com/images/I/410r4X-xVCL.jpg", 0l),
                        new Album(null, 3l, "best of pixies wave of mutilation", "the pixies", "http://ecx.images-amazon.com/images/I/51c9%2BsQdUuL.jpg", 0l),
                        new Album(null, 4l, "toxicity", "system of a down", "http://ecx.images-amazon.com/images/I/618pvPqV-1L.jpg", 0l),
                        new Album(null, 5l, "nevermind", "nirvana", "http://ecx.images-amazon.com/images/I/51tr3o4kd9L.jpg", 0l)
                }
        );
        }catch (Exception e){}
    }
}
