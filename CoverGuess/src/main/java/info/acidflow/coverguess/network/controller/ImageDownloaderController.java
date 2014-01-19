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
 * Created by acidflow on 18/01/14.
 */
public class ImageDownloaderController {

    private static final String LOG_TAG = ImageDownloaderController.class.getSimpleName();

    private static ImageDownloaderController mInstance;
    private Listener mListener;

    public interface Listener{
        public void onDownloadSuccess(String fileName);
        public void onDownloadError(Exception e);
    }


    private ImageDownloaderController(){
        super();
    }

    public static ImageDownloaderController getInstance(){
        if(mInstance == null){
            mInstance = new ImageDownloaderController();
        }
        return mInstance;
    }

    public void downloadImage(String url, final DataType type, String fileName){
        if(!isAlreadyDownloaded(type, fileName)){
            new AsyncTask<String, Void, Void>(){
                @Override
                protected Void doInBackground(String... args) {

                    Bitmap downloaded = downloadBitmap(args[0]);
                    if(downloaded != null){
                        try {
                            saveImage(downloaded, type, args[1]);
                            notifyListenerSuccess(args[1]);
                            return null;
                        } catch (FileNotFoundException e) {
                            notifyListenerError(e);
                            Log.e(LOG_TAG, "File not found when saving bitmap " + e.toString());
                        }
                    }
                    if(downloaded != null && !downloaded.isRecycled()){
                        downloaded.recycle();
                    }
                    return null;
                }
            }.execute(url, fileName);
        }else{
            notifyListenerSuccess(fileName);
        }
    }

    private boolean isAlreadyDownloaded(DataType type, String fileName){
        File f = new File(DownloadUtils.getDownloadDirectory(type), fileName);
        return f.exists();
    }

    private void saveImage(Bitmap img, DataType type, String fileName) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(new File(DownloadUtils.getDownloadDirectory(type), fileName ));
        img.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        img.recycle();
    }

    private Bitmap downloadBitmap(String url) {
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                notifyListenerError(new HttpException());
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
            notifyListenerError(e);
            getRequest.abort();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url + e.toString());
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

    private void notifyListenerError(Exception e){
        if(mListener != null){
            mListener.onDownloadError(e);
        }
    }

    private void notifyListenerSuccess(String filename){
        if(mListener != null){
            mListener.onDownloadSuccess(filename);
        }
    }

    public void setListener(ImageDownloaderController.Listener listener){
        mListener = listener;
    }

    public void removeListener(){
        mListener = null;
    }
}
