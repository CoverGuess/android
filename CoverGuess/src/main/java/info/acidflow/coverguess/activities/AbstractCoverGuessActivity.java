package info.acidflow.coverguess.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by paul on 09/02/14.
 */
public abstract class AbstractCoverGuessActivity extends ActionBarActivity {

    protected void switchContentFragment(int placeHolderId, Fragment fragment, boolean addToBackStack, boolean clearBackStack){
        FragmentManager fm = getSupportFragmentManager();
        if(clearBackStack){
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(placeHolderId, fragment);
        if(addToBackStack){
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public abstract void switchContentFragment(Fragment fragment, boolean addToBackStack, boolean clearBackStack);

}
