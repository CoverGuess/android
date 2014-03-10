package info.acidflow.coverguess.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import info.acidflow.coverguess.R;
import info.acidflow.coverguess.ui.fragments.AbstractCoverGuessUIFragment;
import info.acidflow.coverguess.ui.fragments.CategoryChooserFragment;
import info.acidflow.coverguess.ui.fragments.NavigationDrawerFragment;

public class HomeActivity extends AbstractCoverGuessActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    static{
        System.loadLibrary("coverguessfilter");
    }

    private Fragment mCoverGuessFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new CategoryChooserFragment())
                    .commit();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, boolean restored) {
//        if(!restored){
//            mCoverGuessFragment = GuessCoverFragment.newInstance(Integer.toString(position));
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, mCoverGuessFragment).commit();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void switchContentFragment(AbstractCoverGuessUIFragment fragment, boolean addToBackStack, boolean clearBackStack) {
        super.switchContentFragment(R.id.container, fragment, addToBackStack, clearBackStack);
    }
}
