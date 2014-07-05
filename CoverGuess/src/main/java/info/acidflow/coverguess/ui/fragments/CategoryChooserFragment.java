package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.activities.AbstractCoverGuessActivity;
import info.acidflow.coverguess.datamodel.Category;
import info.acidflow.coverguess.eventbus.events.DatabaseAccessServiceReadyEvent;
import info.acidflow.coverguess.eventbus.events.DatabaseQueryListSuccessEvent;
import info.acidflow.coverguess.ui.adapters.CategoryListAdapter;
import info.acidflow.coverguess.utils.factory.ArrayAdapterAddAllFactory;

/**
 * Created by paul on 09/02/14.
 */
public class CategoryChooserFragment extends AbstractCoverGuessUIFragment {

    public static final String FRAGMENT_TAG = CategoryChooserFragment.class.getName();
    private View mView = null;
    private ProgressBar mLoader = null;
    private GridView mGridView = null;
    private ArrayAdapter<Category> mGridViewAdapter;

    @Override
    public String getFragmentTag() {
        return FRAGMENT_TAG;
    }

    @Override
    public void onStart() {
        super.onStart();
        CoverGuess.getEventBus().registerSticky( this );
    }

    @Override
    public void onPause() {
        super.onPause();
        CoverGuess.getEventBus().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_category_chooser, null);
        mLoader = (ProgressBar) mView.findViewById( R.id.progressBar );
        mGridView = (GridView) mView.findViewById( R.id.fragment_chooser_categories_grid );
        mGridViewAdapter = new CategoryListAdapter( CoverGuess.getContext(), R.layout.adapter_category_item, new ArrayList<Category>() );
        mGridView.setAdapter( mGridViewAdapter );
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String categoryId = String.valueOf(view.getTag(R.id.tag_adapter_category_item_dbid));
                AbstractCoverGuessUIFragment f = LoadingCoverGuessQuizzFragment.newInstance(categoryId);
                ((AbstractCoverGuessActivity) getActivity()).switchContentFragment(f, true, false);
            }
        });
        showLoader();

        return mView;
    }

    private void showLoader(){
        mLoader.setVisibility(View.VISIBLE);
        mGridView.setVisibility(View.GONE);
    }

    private void showGridview(){
        mLoader.setVisibility(View.GONE);
        mGridView.setVisibility(View.VISIBLE);
    }

    public void onEvent( DatabaseAccessServiceReadyEvent event ){
        CoverGuess.getDatabaseService().getCategories(true, "category_name ASC");
    }

    public void onEventMainThread( DatabaseQueryListSuccessEvent<Category> event ){
        ArrayAdapterAddAllFactory.clearAndAddAll( mGridViewAdapter, event.getQueryResults() );
        showGridview();
    }
}
