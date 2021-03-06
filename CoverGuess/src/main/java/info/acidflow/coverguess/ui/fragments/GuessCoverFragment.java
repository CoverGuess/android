package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.controllers.QuizzController;
import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.answer.CorrectAnswer;
import info.acidflow.coverguess.datamodel.answer.UserAnswer;
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
    private Stack<View> mLastActionView = new Stack<View>();
    private Album mCurrentAlbum;
    private CorrectAnswer mCorrectAnswer;
    private UserAnswer mUserAnswer;

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
        mCurrentAlbum = QuizzController.getInstance().getCurrentAlbum();
        try{
            mImageProcessingController = new ImageProcessingController(Constants.CONFIGURATION.DOWNLOADED_COVER_DIRECTORY + File.separator + mCurrentAlbum.getAlbumId());
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

            mUserGuessView = (TextView) mView.findViewById(R.id.album_name);
            mUserAnswer = new UserAnswer( mCurrentAlbum.getTitle().toLowerCase() );
            mCorrectAnswer = new CorrectAnswer( mCurrentAlbum.getTitle().toLowerCase() );

            mUserGuessView.setText( mUserAnswer.getAnswerString() );

            mLetterGridView = (GridView) mView.findViewById(R.id.letters_gridview);
            List<String> albumTitle = mUserAnswer.getCharactersToGuess();
            Collections.shuffle(albumTitle);
            mLetterGridView.setAdapter(new GuessCoverLettersAdapter(getActivity(), albumTitle));
            mLetterGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String letter = (String) view.getTag(R.id.tag_adapter_guess_cover_letter);
                    mLastActionView.push(view);
                    view.setVisibility(View.INVISIBLE);
                    addLetterToUserGuess(letter);
                }
            });
            initializeClickListeners();
        }else{
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
        return mView;
    }

    private void initializeClickListeners(){
        mView.findViewById(R.id.undo_last_action).setOnClickListener(this);
        mView.findViewById(R.id.clear_all).setOnClickListener(this);
    }

    private void addLetterToUserGuess( String letter ){
        mUserAnswer.setCharAt(mUserAnswer.getNextPosition(), letter.charAt(0) );
        mUserGuessView.setText( mUserAnswer.getAnswerString() );
        if( mCorrectAnswer.equals(mUserAnswer) ){
            gg();
        }
    }

    private void removeLetterFromUserGuess(){
        mUserAnswer.removeCharAt( mUserAnswer.getPreviousPosition() );
        mUserGuessView.setText( mUserAnswer.getAnswerString() );
    }

    private void undoLastAction(){
        if(mUserAnswer.getNextPosition() >= 0 && mLastActionView.size() > 0) {
            removeLetterFromUserGuess();
            mLastActionView.pop().setVisibility(View.VISIBLE);
        }
    }

    private void clearAll(){
        do{
            mUserAnswer.removeCharAt( mUserAnswer.getPreviousPosition() );
        }while ( mUserAnswer.getNextPosition() > mUserAnswer.getPreviousPosition() );
        mUserGuessView.setText( mUserAnswer.getAnswerString() );

        while(mLastActionView.size() > 0){
            mLastActionView.pop().setVisibility(View.VISIBLE);
        }
    }

    private void gg(){
        mHandler.removeCallbacks(mImageProcessingTask);
        Toast.makeText(CoverGuess.getContext(), "GG", Toast.LENGTH_LONG).show();
        if(QuizzController.getInstance().incrementQuizzPosition()) {
            ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(GuessCoverFragment.newInstance(), false, false);
        }
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
        switch (view.getId()){
            case R.id.undo_last_action:
                undoLastAction();
                break;
            case R.id.clear_all:
                clearAll();
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
                mHandler.postDelayed(this, mStep * 1000);
            }else{
                if(QuizzController.getInstance().incrementQuizzPosition()) {
                    ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(GuessCoverFragment.newInstance(), false, false);
                }
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
