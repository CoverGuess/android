package info.acidflow.coverguess.network.controller;

import info.acidflow.coverguess.datamodel.DataType;
import info.acidflow.coverguess.utils.Constants;

/**
 * Utility class for downloads
 * Created by acidflow on 19/01/14.
 */
public class DownloadUtils {

    /**
     * Give the path on which the file has to be stored depending on its data type
     * @param type the file type
     * @return the path on which the file has to be stored depending on its data type or a default
     * path if the type does not exist
     */
    public static String getDownloadDirectory(DataType type){
        switch (type){
            case COVER:
                return Constants.CONFIGURATION.DOWNLOADED_COVER_DIRECTORY;
            default:
                return Constants.CONFIGURATION.DOWNLOAD_DIRECTORY;
        }
    }
}
