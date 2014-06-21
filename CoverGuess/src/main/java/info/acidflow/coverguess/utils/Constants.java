package info.acidflow.coverguess.utils;

import android.content.Context;
import android.net.Uri;

import java.io.File;

/**
 * Contants used accross the application
 *
 * Created by acidflow on 18/01/14.
 */
public class Constants {

    public static class CONFIGURATION {
        public static String DOWNLOAD_DIRECTORY;
        private static String DOWNLOAD_DIRECTORY_ROOT = "downloads";
        public static String DOWNLOADED_COVER_DIRECTORY;
        private static String DOWNLOADED_COVER_ROOT_DIRECTORY = DOWNLOAD_DIRECTORY_ROOT + File.separator + "covers";

        public static void initialize(Context context){
            File downloadsRoot = context.getExternalFilesDir(DOWNLOAD_DIRECTORY_ROOT);
            if(!downloadsRoot.exists()){
                downloadsRoot.mkdirs();
            }
            DOWNLOAD_DIRECTORY = downloadsRoot.getAbsolutePath();
            File downloadsImageRoot = context.getExternalFilesDir(DOWNLOADED_COVER_ROOT_DIRECTORY);
            if(!downloadsImageRoot.exists()){
                downloadsImageRoot.mkdirs();
            }
            DOWNLOADED_COVER_DIRECTORY = downloadsImageRoot.getAbsolutePath();
        }
    }

    public static class HTTP {
        private static final String SERVER_IP = "5.135.177.86";
        private static final String SERVER_PORT = "3042";
        private static final String SERVER_AUTHORITY = SERVER_IP + ":" + SERVER_PORT;
        private static final String SERVER_SCHEME = "http";

        public static final Uri SERVER_ROOT_URL = new Uri.Builder().scheme(SERVER_SCHEME).encodedAuthority(SERVER_AUTHORITY).build();
        private static final String API_PATH = "api";
        private static final String GET_UPDATES_PATH = "get_updates";

        public static final Uri API_ROOT_URL = SERVER_ROOT_URL.buildUpon().appendPath(API_PATH).build();

        public static final Uri GET_UPDATES = API_ROOT_URL.buildUpon().appendPath(GET_UPDATES_PATH).build();

    }

    public static class DATABASE {
        public static final String NAME = "coverguess.db";
    }
}
