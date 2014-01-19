import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class CoverGuessDaoGenerator{
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(2, "info.acidflow.coverguess.datamodel");
        addAlbum(schema);
        new DaoGenerator().generateAll(schema, "./CoverGuess/src-gen");
    }

    private static void addAlbum(Schema schema) {
        Entity note = schema.addEntity("Album");
        note.addIdProperty();
        note.addIntProperty("album_id").unique().notNull();
        note.addStringProperty("album_title").notNull();
        note.addStringProperty("album_artist").notNull();
        note.addStringProperty("album_cover_url").notNull();
        note.addDateProperty("album_last_updated");
    }
}