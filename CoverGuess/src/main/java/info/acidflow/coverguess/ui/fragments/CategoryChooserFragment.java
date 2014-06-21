package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.List;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.datamodel.Category;
import info.acidflow.coverguess.datamodel.api.AlbumAPI;
import info.acidflow.coverguess.ui.adapters.CategoryListAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by paul on 09/02/14.
 */
public class CategoryChooserFragment extends AbstractCoverGuessUIFragment {

    public static final String FRAGMENT_TAG = CategoryChooserFragment.class.getName();
    private View mView = null;

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_category_chooser, null);

        List<Category> categories = new Select()
                .distinct().from( Category.class )
                .orderBy( "category_name ASC" )
                .execute();

        ((GridView) mView).setAdapter(new CategoryListAdapter(getActivity(), R.layout.adapter_category_item, categories));
        ((GridView) mView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String categoryId = String.valueOf(view.getTag(R.id.tag_adapter_category_item_dbid));
                AbstractCoverGuessUIFragment f = LoadingCoverGuessQuizzFragment.newInstance(categoryId);
                ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(f, true, false);
            }
        });
        return mView;
    }
}
