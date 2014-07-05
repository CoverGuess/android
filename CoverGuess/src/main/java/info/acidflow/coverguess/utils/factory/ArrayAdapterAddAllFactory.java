package info.acidflow.coverguess.utils.factory;

import android.os.Build;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by paul on 05/07/14.
 */
public class ArrayAdapterAddAllFactory {

    private ArrayAdapterAddAllFactory(){};

    public static void clearAndAddAll(ArrayAdapter adapter, List elements){
        if( Build.VERSION.SDK_INT < 11 ){
            adapter.setNotifyOnChange( false );
            adapter.clear();
            for(int i = 0; i < elements.size(); ++i ){
                adapter.add( elements.get( i ) );
            }
            adapter.notifyDataSetChanged();
        }else{
            adapter.setNotifyOnChange( false );
            adapter.clear();
            adapter.addAll( elements);
            adapter.notifyDataSetChanged();
        }
    }
}
