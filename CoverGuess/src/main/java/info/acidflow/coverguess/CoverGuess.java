package info.acidflow.coverguess;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;

import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.Category;
import info.acidflow.coverguess.utils.Constants;

/**
 * Override the Application class to manage object lifecycle
 * 
 * Created by acidflow on 18/01/14.
 */
public class CoverGuess extends com.activeandroid.app.Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        Constants.CONFIGURATION.initialize(getApplicationContext());
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

    /**
     * Easy access to the application context
     * @return the application context
     */
    public static Context getContext(){
        return mApplicationContext;
    }

}
