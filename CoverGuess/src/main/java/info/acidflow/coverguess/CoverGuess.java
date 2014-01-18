package info.acidflow.coverguess;

import android.app.Application;
import android.content.Context;

import info.acidflow.coverguess.utils.Constants;

/**
 * Created by acidflow on 18/01/14.
 */
public class CoverGuess extends Application {

    private static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();
        Constants.CONFIGURATION.initialize(getApplicationContext());
    }

    /**
     * Easy access to the application context
     * @return the application context
     */
    public static Context getContext(){
        return mApplicationContext;
    }

}
