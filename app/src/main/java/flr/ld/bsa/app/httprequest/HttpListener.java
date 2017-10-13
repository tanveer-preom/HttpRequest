package flr.ld.bsa.app.httprequest;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.VolleyError;

/**
 * Created by root on 12/27/15.
 */
public interface HttpListener {
    public void respond(String jsonRespond, int respondId, Bundle extras);
    public void errorRespond(Throwable e, int respondId,String message, Bundle extras);
    public Context getContext();

}
