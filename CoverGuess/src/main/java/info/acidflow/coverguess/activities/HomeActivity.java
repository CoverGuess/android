package info.acidflow.coverguess.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import info.acidflow.coverguess.R;
import info.acidflow.coverguess.ui.fragments.GuessCoverFragment;
import info.acidflow.coverguess.ui.fragments.NavigationDrawerFragment;

public class HomeActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks{

    static{
        System.loadLibrary("coverguessfilter");
    }

    private Fragment mCoverGuessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initFragments(savedInstanceState);
    }

    private void initFragments(Bundle savedInstanceState){
        if(savedInstanceState == null){
            mCoverGuessFragment = GuessCoverFragment.newInstance("/sdcard/1.jpg");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mCoverGuessFragment).commit();
        }else{
            mCoverGuessFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean restored) {
        if(!restored){
            mCoverGuessFragment = GuessCoverFragment.newInstance("/sdcard/"+(position+1)+".jpg");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, mCoverGuessFragment).commit();
        }
    }
}
