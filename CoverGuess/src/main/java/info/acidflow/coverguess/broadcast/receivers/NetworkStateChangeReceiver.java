package info.acidflow.coverguess.broadcast.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import info.acidflow.coverguess.network.NetworkFactory;

/**
 * Created by paul on 06/07/14.
 */
public class NetworkStateChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isNetworkAvailable = intent.getBooleanExtra( ConnectivityManager.EXTRA_NO_CONNECTIVITY, false );
        int netWorkType = intent.getIntExtra( ConnectivityManager.EXTRA_NETWORK_TYPE, -1 );
        NetworkFactory.setNetworkAvailable( isNetworkAvailable );
        NetworkFactory.setCurrentNetworkType( netWorkType );
    }
}
