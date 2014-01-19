package info.acidflow.coverguess.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.datamodel.DataType;
import info.acidflow.coverguess.network.controller.ImageDownloaderController;
import info.acidflow.coverguess.ui.fragments.GuessCoverFragment;
import info.acidflow.coverguess.ui.fragments.NavigationDrawerFragment;

public class HomeActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, ImageDownloaderController.Listener {

    static{
        System.loadLibrary("coverguessfilter");
    }

    private Fragment mCoverGuessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initFragments(savedInstanceState);
        String[] test = new String[]{
            "http://ecx.images-amazon.com/images/I/31UZQMqbb-L.jpg",
            "http://ecx.images-amazon.com/images/I/410r4X-xVCL.jpg",
            "http://ecx.images-amazon.com/images/I/51c9%2BsQdUuL.jpg",
            "http://ecx.images-amazon.com/images/I/618pvPqV-1L.jpg",
            "http://ecx.images-amazon.com/images/I/51tr3o4kd9L.jpg"
        };
        ImageDownloaderController.getInstance().setListener(this);
        for(int i = 0; i < test.length; i++){
            ImageDownloaderController.getInstance().downloadImage(test[i], DataType.COVER, Integer.toString(i));
        }

    }

    private void initFragments(Bundle savedInstanceState){
        if(savedInstanceState == null){
//            mCoverGuessFragment = GuessCoverFragment.newInstance("/sdcard/1.jpg");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mCoverGuessFragment).commit();
        }else{
            mCoverGuessFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean restored) {
        if(!restored){
            mCoverGuessFragment = GuessCoverFragment.newInstance(Integer.toString(position));
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mCoverGuessFragment).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageDownloaderController.getInstance().removeListener();
    }

    @Override
    public void onDownloadSuccess(String file) {
        try{
            Toast.makeText(CoverGuess.getContext(), "Download complete " + file, Toast.LENGTH_SHORT).show();
        }catch(Exception e){}
    }

    @Override
    public void onDownloadError(Exception e) {

    }
}
