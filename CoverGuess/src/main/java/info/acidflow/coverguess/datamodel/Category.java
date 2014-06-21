package info.acidflow.coverguess.datamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by paul on 21/06/14.
 */
@Table( name = "CATEGORIES", id = "_id" )
public class Category extends Model {

    @Column(name ="category_name", index = true, notNull = true )
    private String mName;

    public Category(){
        super();
    }

    public Category( String name ) {
        super();
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
