package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.File;
import java.io.FileNotFoundException;

import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.exceptions.FilterNotExistException;
import info.acidflow.coverguess.controllers.QuizzController;
import info.acidflow.coverguess.processing.controller.ImageProcessingController;
import info.acidflow.coverguess.processing.filters.Filter;
import info.acidflow.coverguess.utils.Constants;

/**
 * Created by acidflow on 14/01/14.
 */
public class GuessCoverFragment extends AbstractCoverGuessUIFragment implements View.OnClickListener, ImageProcessingController.Callback, Animator.AnimatorListener {
    public static final String FRAGMENT_TAG = GuessCoverFragment.class.getName();
    private static final String ARG_IMG_PATH = "IMG_PATH";

    private View mView;
    private ImageView mCoverImageView;
    private SeekBar mPixelizeFactorSeekBar;
    private ImageProcessingController mImageProcessingController;
    private Handler mHandler;
    private int mStep = 0;

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
            initializeClickListeners();
        }else{
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        return mView;
    }

    private void initializeClickListeners(){
        mView.findViewById(R.id.startProcessingBtn).setOnClickListener(this);
    }

    private void parseArguments(Bundle args) throws FileNotFoundException {
        if(args == null){
            return;
        }
        if(args.containsKey(ARG_IMG_PATH)){

        }

    }

    private void startImageProcessing(boolean initializeSeekBar){
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mStep < mImageProcessingController.getDivisionFactors().size()){
//                    try {
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mCoverImageView, "rotationY", 0, 90).setDuration(800);
                    objectAnimator.addListener(GuessCoverFragment.this);
                    objectAnimator.start();

//                    } catch (FilterNotExistException e) {
//                        return;
//                    }
                    ++mStep;
                    mHandler.postDelayed(this, mStep * 1000);
                }else{
                    QuizzController.getInstance().incrementQuizzPosition();
                    ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(GuessCoverFragment.newInstance(), false, false);
                }
            }
        }, 0);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.startProcessingBtn:
                startImageProcessing(true);
                break;
        }
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
}
