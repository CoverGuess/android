package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.FileNotFoundException;

import info.acidflow.coverguess.R;
import info.acidflow.coverguess.exceptions.FilterNotExistException;
import info.acidflow.coverguess.processing.controller.ImageProcessingController;
import info.acidflow.coverguess.processing.filters.Filter;

/**
 * Created by acidflow on 14/01/14.
 */
public class GuessCoverFragment extends Fragment implements View.OnClickListener, ImageProcessingController.Callback{

    private static final String ARG_IMG_PATH = "IMG_PATH";

    private View mView;
    private ImageView mCoverImageView;
    private SeekBar mPixelizeFactorSeekBar;
    private ImageProcessingController mImageProcessingController;
    private Handler mHandler;
    private int mStep = 0;

    public static GuessCoverFragment newInstance(String imgPath){
        Bundle args = new Bundle();
        args.putString(ARG_IMG_PATH, imgPath);
        GuessCoverFragment fragment = new GuessCoverFragment();
        fragment.setArguments(args);
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            parseArguments(getArguments());
        } catch (FileNotFoundException e) {
            return;
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView == null){
            super.onCreateView(inflater,container,savedInstanceState);
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
            mImageProcessingController = new ImageProcessingController(args.getString(ARG_IMG_PATH));
            mImageProcessingController.setListener(this);
        }

    }

    private void startImageProcessing(boolean initializeSeekBar){
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mStep < mImageProcessingController.getDivisionFactors().size()){
                    try {
                        mImageProcessingController.applyFilter(Filter.PIXELLIZE, mStep);
                    } catch (FilterNotExistException e) {
                        return;
                    }
                    ++mStep;
                    mHandler.postDelayed(this, mStep * 1000);
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
        mCoverImageView.postInvalidate();
    }
}
