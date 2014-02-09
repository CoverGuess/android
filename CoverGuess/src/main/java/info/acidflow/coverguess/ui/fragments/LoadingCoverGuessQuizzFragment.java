package info.acidflow.coverguess.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.greenrobot.dao.query.WhereCondition;
import info.acidflow.coverguess.CoverGuess;
import info.acidflow.coverguess.R;
import info.acidflow.coverguess.datamodel.Album;
import info.acidflow.coverguess.datamodel.AlbumDao;

/**
 * Created by paul on 09/02/14.
 */
public class LoadingCoverGuessQuizzFragment extends Fragment {

    private static final String ARG_CAT_ID = "catID";
    private View mView = null;
    private List<Album> mAlbumsQuizz = null;

    public static LoadingCoverGuessQuizzFragment newInstance(String catID){
        Bundle args = new Bundle();
        args.putString(ARG_CAT_ID, catID);
        LoadingCoverGuessQuizzFragment loadingCoverGuessQuizzFragment = new LoadingCoverGuessQuizzFragment();
        loadingCoverGuessQuizzFragment.setArguments(args);
        return loadingCoverGuessQuizzFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlbumsQuizz = CoverGuess.getDaoMaster().newSession().getAlbumDao().queryBuilder()
                .orderRaw("RANDOM()").limit(5).list();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_loading_cover_guess_quizz, null);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CAT ARG = ");
        stringBuilder.append(getArguments().getString(ARG_CAT_ID));
        stringBuilder.append("\n");
        for(Album a : mAlbumsQuizz){
            stringBuilder.append(a.getAlbum_artist());
            stringBuilder.append("-");
            stringBuilder.append(a.getAlbum_title());
            stringBuilder.append("\n");
        }
        ((TextView) mView.findViewById(R.id.textView)).setText(stringBuilder.toString());
        return mView;
    }
}
