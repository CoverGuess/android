package info.acidflow.coverguess.services.connections;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.eventbus.events.DatabaseAccessServiceDisconnectedEvent;
import info.acidflow.coverguess.eventbus.events.DatabaseAccessServiceReadyEvent;
import info.acidflow.coverguess.services.DatabaseAccessService;

/**
 * Created by paul on 05/07/14.
 */
public class DatabaseAccessServiceConnection implements ServiceConnection {
    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        Log.i("CoverGuess", "DatabaseAccessServiceConnection connected");
        CoverGuess.getEventBus().postSticky(new DatabaseAccessServiceReadyEvent(((DatabaseAccessService.DatabaseAccessBinder) iBinder).getService()));
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        CoverGuess.getEventBus().postSticky(new DatabaseAccessServiceDisconnectedEvent());
    }
}
