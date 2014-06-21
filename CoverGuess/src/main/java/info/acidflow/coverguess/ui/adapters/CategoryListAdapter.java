package info.acidflow.coverguess.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import info.acidflow.coverguess.R;
import info.acidflow.coverguess.datamodel.Category;

/**
 * Created by paul on 09/02/14.
 */
public class CategoryListAdapter extends ArrayAdapter<Category> {

    private LayoutInflater mLayoutInflater;
    private List<Category> mCategories;

    public CategoryListAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCategories = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Category category = mCategories.get(position);
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.adapter_category_item, null);
        }
        convertView.setTag(R.id.tag_adapter_category_item_dbid, category.getId());
        ((TextView) convertView.findViewById(R.id.adapter_category_item_name)).setText(category.getName());
        return convertView;
    }
}
