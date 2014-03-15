package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.controllers.QuizzController;
import info.acidflow.coverguess.exceptions.FilterNotExistException;
import info.acidflow.coverguess.processing.controller.ImageProcessingController;
import info.acidflow.coverguess.processing.filters.Filter;
import info.acidflow.coverguess.processing.text.TextProcessor;
import info.acidflow.coverguess.ui.adapters.GuessCoverLettersAdapter;
import info.acidflow.coverguess.utils.Constants;

/**
 * Created by acidflow on 14/01/14.
 */
public class GuessCoverFragment extends AbstractCoverGuessUIFragment implements View.OnClickListener, ImageProcessingController.Callback, Animator.AnimatorListener {
    public static final String FRAGMENT_TAG = GuessCoverFragment.class.getName();
    private static final String ARG_IMG_PATH = "IMG_PATH";

    private View mView;
    private ImageView mCoverImageView;
    private ImageProcessingController mImageProcessingController;
    private Handler mHandler;
    private ImageProcessingRunnable mImageProcessingTask = new ImageProcessingRunnable();
    private int mStep = 0;
    private boolean isRestored = false;
    private GridView mLetterGridView;
    private TextView mUserGuessView;

    public static AbstractCoverGuessUIFragment newInstance(){
        Bundle args = new Bundle();
        GuessCoverFragment fragment = new GuessCoverFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public String getFragmentTag(){
        return FRAGMENT_TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            mImageProcessingController = new ImageProcessingController(Constants.CONFIGURATION.DOWNLOADED_COVER_DIRECTORY + File.separator + QuizzController.getInstance().getCurrentAlbum().getAlbum_id());
            mImageProcessingController.setListener(this);
            HashMap<Character, Integer> letterDistributionTitle =
                    TextProcessor.getLettersDistribution(QuizzController.getInstance().getCurrentAlbum().getAlbum_title());

            HashMap<Character, Integer> letterDistributionArtist =
                    TextProcessor.getLettersDistribution(QuizzController.getInstance().getCurrentAlbum().getAlbum_artist());
        }catch (FileNotFoundException e ){
            throw new RuntimeException(e);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment_cover_guess, null);
            mCoverImageView = (ImageView) mView.findViewById(R.id.pixelizedCoverHolder);
            mCoverImageView.setImageBitmap(mImageProcessingController.getResultBitmap());
            mUserGuessView = (TextView) mView.findViewById(R.id.album_name);
            mLetterGridView = (GridView) mView.findViewById(R.id.letters_gridview);
            mLetterGridView.setAdapter(new GuessCoverLettersAdapter(getActivity(), TextProcessor.getLettersList(QuizzController.getInstance().getCurrentAlbum().getAlbum_title())));
            mLetterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String letter = (String) view.getTag(R.id.tag_adapter_guess_cover_letter);
                    view.setVisibility(View.INVISIBLE);
                    addLetterToUserGuess(letter);
                }
            });
        }else{
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        return mView;
    }

    private void addLetterToUserGuess(String letter){
        mUserGuessView.append(letter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isRestored){
            startImageProcessing();
        }
    }

    private void parseArguments(Bundle args) throws FileNotFoundException {
        if(args == null){
            return;
        }
    }

    private void startImageProcessing(){
        mHandler = new Handler();
        mHandler.postDelayed(mImageProcessingTask, 0);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onProcessingDone() {

    }

    @Override
    public void onAnimationStart(Animator animation) {


    }

    @Override
    public void onAnimationEnd(Animator animation) {
        try {
            if(mStep < mImageProcessingController.getDivisionFactors().size()){
                mImageProcessingController.applyFilter(Filter.PIXELLIZE, mStep);
            }
        } catch (FilterNotExistException e) {
            e.printStackTrace();
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mCoverImageView, "rotationY", -90, 0).setDuration(800);
        objectAnimator.start();
        mCoverImageView.postInvalidate();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    private void animateImageViewDuringProcessing() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mCoverImageView, "rotationY", 0, 90).setDuration(800);
        objectAnimator.addListener(GuessCoverFragment.this);
        objectAnimator.start();
    }

    class ImageProcessingRunnable implements Runnable{
        @Override
        public void run() {
            if(mStep < mImageProcessingController.getDivisionFactors().size()){
                animateImageViewDuringProcessing();
                mStep++;
                mHandler.postDelayed(this, mStep * 500);
            }else{
                QuizzController.getInstance().incrementQuizzPosition();
                ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(GuessCoverFragment.newInstance(), false, false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mImageProcessingTask);
        isRestored = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        isRestored = true;
    }
}
