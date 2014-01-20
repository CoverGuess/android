package info.acidflow.coverguess.utils;

import java.io.File;

/**
 * An utility class for files
 *
 * Created by acidflow on 20/01/14.
 */
public class FilesUtils {

    public static boolean fileExists(String path){
        File f = new File(path);
        return f.exists();
    }
}
