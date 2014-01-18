package info.acidflow.coverguess.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by acidflow on 18/01/14.
 */
public class Constants {

    public static class CONFIGURATION {
        public static String DOWNLOAD_DIRECTORY;
        private static String DOWNLOAD_DIRECTORY_ROOT = "downloads";
        public static String DOWNLOADED_IMAGE_DIRECTORY;
        private static String DOWNLOADED_IMAGE_ROOT_DIRECTORY = DOWNLOAD_DIRECTORY_ROOT + File.separator + "covers";

        public static void initialize(Context context){
            File downloadsRoot = context.getExternalFilesDir(DOWNLOAD_DIRECTORY_ROOT);
            if(!downloadsRoot.exists()){
                downloadsRoot.mkdirs();
            }
            DOWNLOAD_DIRECTORY = downloadsRoot.getAbsolutePath();
            File downloadsImageRoot = context.getExternalFilesDir(DOWNLOADED_IMAGE_ROOT_DIRECTORY);
            if(!downloadsImageRoot.exists()){
                downloadsImageRoot.mkdirs();
            }
            DOWNLOADED_IMAGE_DIRECTORY = downloadsImageRoot.getAbsolutePath();
        }
    }
}
