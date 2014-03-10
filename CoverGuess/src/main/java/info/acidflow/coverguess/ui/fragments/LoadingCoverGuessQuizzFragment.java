package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.DataType;
import info.acidflow.coverguess.network.controller.ImageDownloaderController;
import info.acidflow.coverguess.controllers.QuizzController;

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
        mAlbumsQuizz = CoverGuess.getDaoMaster().newSession().getAlbumDao().queryBuilder()
                .orderRaw("RANDOM()").limit(5).list();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_loading_cover_guess_quizz, null);
//
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("CAT ARG = ");
//        stringBuilder.append(getArguments().getString(ARG_CAT_ID));
//        stringBuilder.append("\n");
//        for(Album a : mAlbumsQuizz){
//            stringBuilder.append(a.getAlbum_artist());
//            stringBuilder.append("-");
//            stringBuilder.append(a.getAlbum_title());
//            stringBuilder.append("\n");
//        }
        ((TextView) mView.findViewById(R.id.textView)).setText(getString(R.string.loading_download_success, mDownloadSuccess, mAlbumsQuizz.size()));

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        downloadImagesForQuizz();
    }

    private void downloadImagesForQuizz(){
        if(!mIsDownloadStarted){
            mDownloadSuccess = 0;
            mIsDownloadStarted = true;
            ImageDownloaderController.getInstance().setListener(this);
            for(Album a : mAlbumsQuizz){
                ImageDownloaderController.getInstance().downloadImage(
                        a.getAlbum_cover_url(), DataType.COVER, String.valueOf(a.getAlbum_id()));
            }
        }
    }

    @Override
    public void onDownloadSuccess(String fileName) {
        mDownloadSuccess++;
        ((TextView) mView.findViewById(R.id.textView)).setText(getString(R.string.loading_download_success, mDownloadSuccess, mAlbumsQuizz.size()));
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

}
