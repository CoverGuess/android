package info.acidflow.coverguess.network;

import android.net.ConnectivityManager;

import info.acidflow.coverguess.network.interfaces.CoverGuessAPI;
import info.acidflow.coverguess.network.interfaces.CoverGuessAPINoNetwork;
import info.acidflow.coverguess.utils.Constants;
import retrofit.RestAdapter;

/**
 * Created by paul on 06/07/14.
 */
public class NetworkFactory  {

    private static int sCurrentNetworkType = -1;
    private static boolean sIsNetworkAvailable = false;

    private NetworkFactory(){
        super();
    }

    public static void setNetworkAvailable( boolean b ){
        sIsNetworkAvailable = b;
    }

    public static void setCurrentNetworkType( int networkType ){
        sCurrentNetworkType = networkType;
    }

    public static CoverGuessAPI getCoverguessAPI(){
        if( !sIsNetworkAvailable ){
            return new CoverGuessAPINoNetwork();
        }else{
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint( Constants.HTTP.API_ROOT_URL.toString() )
                    .build();
            return restAdapter.create( CoverGuessAPI.class );
        }
    }

}
