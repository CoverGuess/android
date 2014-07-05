package info.acidflow.coverguess.exceptions;

import android.util.Log;

/**
 * Created by paul on 05/07/14.
 */
public abstract class AbstractCoverGuessException extends Exception {
    public static final String COVER_GUESS_EXCEPTION_TAG = "CoverGuessException";

    public AbstractCoverGuessException() {
        super();
    }

    public AbstractCoverGuessException(String detailMessage) {
        super(detailMessage);
    }

    public AbstractCoverGuessException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public AbstractCoverGuessException(Throwable throwable) {
        super(throwable);
    }

    public void handleException(){
        Log.e( COVER_GUESS_EXCEPTION_TAG, getMessage(), this );
    }
}
