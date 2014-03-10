        package info.acidflow.coverguess.activities;

        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v7.app.ActionBarActivity;

        import java.util.Stack;

        import info.acidflow.coverguess.ui.fragments.AbstractCoverGuessUIFragment;

        /**
 * Created by paul on 09/02/14.
 */
public abstract class AbstractCoverGuessActivity extends ActionBarActivity {

    private static Stack<String> sFragmentClassStack;

    static{
        sFragmentClassStack = new Stack<String>();
    }

    protected void switchContentFragment(int placeHolderId, AbstractCoverGuessUIFragment fragment, boolean addToBackStack, boolean clearBackStack){
        FragmentManager fm = getSupportFragmentManager();
        if(clearBackStack){
            fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            sFragmentClassStack.clear();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(placeHolderId, fragment, fragment.getFragmentTag());
        sFragmentClassStack.push(fragment.getFragmentTag());
        if(addToBackStack){
            ft.addToBackStack(fragment.getFragmentTag());
        }
        ft.commit();
    }

    public abstract void switchContentFragment(AbstractCoverGuessUIFragment fragment, boolean addToBackStack, boolean clearBackStack);

    public static Stack<String> getFragmentStackClass(){
        return sFragmentClassStack;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        while(sFragmentClassStack.size() > getSupportFragmentManager().getBackStackEntryCount()){
            Fragment toRemove = getSupportFragmentManager().findFragmentByTag(sFragmentClassStack.pop());
            if(toRemove != null){
                getSupportFragmentManager().beginTransaction().remove(toRemove)
                        .commitAllowingStateLoss();
            }
        }
    }
}
