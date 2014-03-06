package info.acidflow.coverguess.network.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

import info.acidflow.coverguess.datamodel.DataType;

/**
 * A controller to download images
 *
 * This class is a singleton
 * Created by acidflow on 18/01/14.
 */
public class ImageDownloaderController {

    private static final String LOG_TAG = ImageDownloaderController.class.getSimpleName();

    /**
     * An interface describing callbacks executed on download success or error
     */
    public interface Listener{
        public void onDownloadSuccess(String fileName);
        public void onDownloadError(Exception e);
    }

    private static ImageDownloaderController mInstance;

    private Listener mListener;

    private ImageDownloaderController(){
        super();
    }

    public static ImageDownloaderController getInstance(){
        if(mInstance == null){
            mInstance = new ImageDownloaderController();
        }
        return mInstance;
    }

    /**
     * Download a file if it is not already cached on the external storage
     * @param url the url of the file to download
     * @param type the data type of the image
     * @param fileName the filename in which the result will be written
     */
    public void downloadImage(String url, final DataType type, String fileName){
        if(!isAlreadyDownloaded(type, fileName)){
            new AsyncTask<String, Void, String>(){
                private boolean errorHappened = false;
                private Exception exceptionRaised = null;
                @Override
                protected String doInBackground(String... args) {

                    Bitmap downloaded = downloadBitmap(args[0]);
                    if(downloaded != null){
                        try {
                            saveImage(downloaded, type, args[1]);
                            return args[1];
                        } catch (FileNotFoundException e) {
                            errorHappened = true;
                            exceptionRaised = e;
                            Log.e(LOG_TAG, "File not found when saving bitmap " + e.toString());
                        }
                    }
                    if(downloaded != null && !downloaded.isRecycled()){
                        downloaded.recycle();
                    }
                    errorHappened = true;
                    return null;
                }

                @Override
                protected void onPostExecute(String result) {
                    super.onPostExecute(result);
                    if(!errorHappened){
                        notifyListenerSuccess(result);
                    }else{
                        notifyListenerError(exceptionRaised);
                    }

                }
            }.execute(url, fileName);
        }else{
            notifyListenerSuccess(fileName);
        }
    }

    /**
     * Check if a file is already on the external storage
     * @param type the data type of the file
     * @param fileName the filename which will be checked
     * @return true if the file exists, false otherwise
     */
    private boolean isAlreadyDownloaded(DataType type, String fileName){
        File f = new File(DownloadUtils.getDownloadDirectory(type), fileName);
        return f.exists();
    }

    /**
     * Save the image which has been downloaded
     * @param img the bitmap which has been retrieved from the server
     * @param type the data type of the image
     * @param fileName the filename in which the result will be written
     * @throws FileNotFoundException
     */
    private void saveImage(Bitmap img, DataType type, String fileName) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(new File(DownloadUtils.getDownloadDirectory(type), fileName ));
        img.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        img.recycle();
    }

    /**
     * Download a bitmap from the given URL
     * @param url the URL of the file to download
     * @return the bitmap which has been downloaded
     */
    private Bitmap downloadBitmap(String url) {
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
//                notifyListenerError(new HttpException());
                Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // Could provide a more explicit error message for IOException or IllegalStateException
//            notifyListenerError(e);
            getRequest.abort();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url + e.toString());
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

    /**
     * Utility method to notify the listener if it is not null
     * @param e the exception which has occured
     */
    private void notifyListenerError(Exception e){
        if(mListener != null){
            mListener.onDownloadError(e);
        }
    }

    /**
     * Utility method to notify the listener if it is not null
     * @param filename the filename for which the download is complete
     */
    private void notifyListenerSuccess(String filename){
        if(mListener != null){
            mListener.onDownloadSuccess(filename);
        }
    }

    /**
     * Set the listener for this controller
     * @param listener the listener
     */
    public void setListener(ImageDownloaderController.Listener listener){
        mListener = listener;
    }

    /**
     * Remove the listener for this controller
     */
    public void removeListener(){
        mListener = null;
    }
}
