package flr.ld.bsa.app.httprequest;

/**
 * Created by root on 10/2/17.
 */

public interface UploadProgressListener {
    public void onProgress(long bytesWritten,long totalSize);

}
