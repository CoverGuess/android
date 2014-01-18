package info.acidflow.coverguess.processing.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import info.acidflow.coverguess.exceptions.FilterNotExistException;
import info.acidflow.coverguess.processing.filters.Filter;
import info.acidflow.coverguess.processing.filters.NDKFilters;

/**
 * Created by acidflow on 16/01/14.
 */
public class ImageProcessingController {

    public interface Callback {
        public void onProcessingDone();
    }

    private ArrayList<Integer> mDivisionFactors;
    private int[] mExchangeBuffer;
    private String mImagePath;
    private Bitmap mResultBitmat;
    private int mImageWidth;
    private int mImageHeight;
    private Callback mListener;

    public ImageProcessingController(String pathToImage) throws FileNotFoundException {
        super();
        if(!fileExists(pathToImage)){
            throw new FileNotFoundException("Image not found @" + pathToImage);
        }
        mImagePath = pathToImage;
        initializeFromImage();
        mDivisionFactors = determineDivisionsFactor();
    }

    private ArrayList<Integer> determineDivisionsFactor(){
        ArrayList<Integer> factors = new ArrayList<Integer>();
        for(int i = 4; i < mImageWidth * mImageHeight; i *= 4){
            factors.add((Math.min(mImageWidth, mImageHeight)) / (int) (Math.sqrt((mImageWidth * mImageHeight) / i)));
        }
        return factors;
    }

    private boolean fileExists(String pathToFile){
        File f = new File(pathToFile);
        return f.exists();
    }

    private void initializeFromImage(){
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImagePath, bitmapOptions);
        mImageWidth = bitmapOptions.outWidth;
        mImageHeight = bitmapOptions.outHeight;
        if(mImageWidth <= 0 || mImageHeight <= 0){
            mImageWidth = 1;
            mImageHeight = 1;
        }
        mExchangeBuffer = new int[mImageWidth * mImageHeight];
        mResultBitmat = Bitmap.createBitmap(mImageWidth, mImageHeight, Bitmap.Config.ARGB_8888);
    }

    public Bitmap getResultBitmap(){
        return mResultBitmat;
    }

    public void setListener(Callback listener){
        mListener = listener;
    }

    public ArrayList<Integer> getDivisionFactors(){
        return mDivisionFactors;
    }

    public void applyFilter(Filter filter, int step) throws FilterNotExistException {
        switch(filter){
            case PIXELLIZE:
                NDKFilters.pixellize(mImagePath, mDivisionFactors.get(step), mImageWidth, mImageHeight, mExchangeBuffer);
                break;
            default:
                throw new FilterNotExistException();
        }
        mResultBitmat.setPixels( mExchangeBuffer, 0 , mImageWidth , 0 ,0, mImageWidth, mImageHeight );
        if(mListener != null){
            mListener.onProcessingDone();
        }
    }


}
