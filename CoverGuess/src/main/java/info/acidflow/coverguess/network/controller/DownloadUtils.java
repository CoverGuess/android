package info.acidflow.coverguess.network.controller;

import info.acidflow.coverguess.datamodel.DataType;
import info.acidflow.coverguess.utils.Constants;

/**
 * Created by acidflow on 19/01/14.
 */
public class DownloadUtils {

    public static String getDownloadDirectory(DataType type){
        switch (type){
            case COVER:
                return Constants.CONFIGURATION.DOWNLOADED_COVER_DIRECTORY;
            default:
                return Constants.CONFIGURATION.DOWNLOAD_DIRECTORY;
        }
    }
}
