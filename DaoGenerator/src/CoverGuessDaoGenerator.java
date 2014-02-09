import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CoverGuessDaoGenerator{
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "info.acidflow.coverguess.datamodel");
        addAlbum(schema);
        addCategory(schema);
        new DaoGenerator().generateAll(schema, "./CoverGuess/src-gen");
    }

    private static void addAlbum(Schema schema) {
        Entity note = schema.addEntity("Album");
        note.addIdProperty();
        note.addStringProperty("album_title").notNull();
        note.addStringProperty("album_artist").notNull();
        note.addStringProperty("album_cover_url").notNull();
        note.addLongProperty("album_last_updated");
    }

    private static void addCategory(Schema schema){
        Entity cat = schema.addEntity("Category");
        cat.addIdProperty();
        cat.addStringProperty("category_name").notNull();
    }
}