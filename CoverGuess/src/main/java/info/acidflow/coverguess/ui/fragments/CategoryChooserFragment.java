package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import de.greenrobot.dao.query.LazyList;
import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.datamodel.Category;
import info.acidflow.coverguess.ui.adapters.CategoryListAdapter;

/**
 * Created by paul on 09/02/14.
 */
public class CategoryChooserFragment extends Fragment{

    private View mView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_category_chooser, null);
        LazyList<Category> categories = CoverGuess.getDaoMaster().newSession().getCategoryDao().queryBuilder().listLazy();
        ((GridView) mView).setAdapter(new CategoryListAdapter(getActivity(), R.layout.adapter_category_item, categories));
        ((GridView) mView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String categoryId = String.valueOf(view.getTag(R.id.adapter_category_item_tag_dbid));
                Fragment f = LoadingCoverGuessQuizzFragment.newInstance(categoryId);
                ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(f, true, false);
            }
        });
        return mView;
    }
}