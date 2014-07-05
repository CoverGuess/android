package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.List;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.Category;
import info.acidflow.coverguess.datamodel.DataType;
import info.acidflow.coverguess.eventbus.events.DatabaseAccessServiceReadyEvent;
import info.acidflow.coverguess.eventbus.events.DatabaseQueryListSuccessEvent;
import info.acidflow.coverguess.network.controller.ImageDownloaderController;
import info.acidflow.coverguess.controllers.QuizzController;
import info.acidflow.coverguess.utils.factory.ArrayAdapterAddAllFactory;

/**
 * Created by paul on 09/02/14.
 */
public class LoadingCoverGuessQuizzFragment extends AbstractCoverGuessUIFragment implements ImageDownloaderController.Listener {

    public static final String FRAGMENT_TAG = LoadingCoverGuessQuizzFragment.class.getName();

    private static final String ARG_CAT_ID = "catID";
    private View mView = null;
    private List<Album> mAlbumsQuizz = null;
    private int mDownloadSuccess;
    private boolean mIsDownloadStarted = false;
    private TextView mTextViewLoadingSuccess;

    public static AbstractCoverGuessUIFragment newInstance(String catID){
        Bundle args = new Bundle();
        args.putString(ARG_CAT_ID, catID);
        LoadingCoverGuessQuizzFragment loadingCoverGuessQuizzFragment = new LoadingCoverGuessQuizzFragment();
        loadingCoverGuessQuizzFragment.setArguments(args);
        return loadingCoverGuessQuizzFragment;
    }

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_loading_cover_guess_quizz, null);
        mTextViewLoadingSuccess = ( (TextView) mView.findViewById( R.id.textView ) );
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        CoverGuess.getEventBus().registerSticky(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        CoverGuess.getEventBus().unregister(this);
    }

    private void downloadImagesForQuizz(){
        if(!mIsDownloadStarted){
            mDownloadSuccess = 0;
            mIsDownloadStarted = true;
            ImageDownloaderController.getInstance().setListener(this);
            for(Album a : mAlbumsQuizz){
                ImageDownloaderController.getInstance().downloadImage(
                        a.getCoverUrl(), DataType.COVER, String.valueOf( a.getAlbumId() ) );
            }
        }
    }

    @Override
    public void onDownloadSuccess(String fileName) {
        mDownloadSuccess++;
        mTextViewLoadingSuccess.setText(getString(R.string.loading_download_success, mDownloadSuccess, mAlbumsQuizz.size()));
        if(mDownloadSuccess == mAlbumsQuizz.size()){
            startQuizz();
        }
    }

    @Override
    public void onDownloadError(Exception e) {

    }

    private void startQuizz(){
        QuizzController.getInstance().initialize(mAlbumsQuizz);
        ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(GuessCoverFragment.newInstance(), false, false);
        ImageDownloaderController.getInstance().removeListener();
    }

    public void onEvent( DatabaseAccessServiceReadyEvent event ){
        if( !mIsDownloadStarted ) {
            CoverGuess.getDatabaseService().getNewQuizz(5);
        }
    }

    public void onEventMainThread( DatabaseQueryListSuccessEvent<Album> event ){
        mAlbumsQuizz = event.getQueryResults();
        mTextViewLoadingSuccess.setText(getString(R.string.loading_download_success, mDownloadSuccess, mAlbumsQuizz.size()));
        downloadImagesForQuizz();
    }

}
