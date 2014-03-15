package info.acidflow.coverguess.ui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import info.acidflow.coverguess.R;

/**
 * Created by paul on 15/03/14.
 */
public class GuessCoverLettersAdapter extends ArrayAdapter<String> {


    public GuessCoverLettersAdapter(Context context, List<String> objects) {
        super(context, R.layout.adapter_guess_cover_letter, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(getContext(), R.layout.adapter_guess_cover_letter, null);
        }
        convertView.setTag(R.id.tag_adapter_guess_cover_letter, getItem(position));
        ((TextView) convertView.findViewById(R.id.letter)).setText(getItem(position));
        return convertView;
    }
}
