package info.acidflow.coverguess.eventbus.events;

import android.provider.ContactsContract;

import de.greenrobot.event.EventBus;
import info.acidflow.coverguess.services.DatabaseAccessService;

/**
 * Created by paul on 05/07/14.
 */
public class DatabaseAccessServiceReadyEvent {
    private DatabaseAccessService mService;

    public DatabaseAccessServiceReadyEvent( DatabaseAccessService service ){
        super();
        mService = service;
    }

    public DatabaseAccessService getService(){
        return mService;
    }
}
