package flr.ld.bsa.app.httprequest;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Siyam on 15-Aug-15.
 */
public class RequestManager {

    private static RequestQueue queue;

    public static RequestQueue getRequestQueue(Context context){
        if(queue == null){
            queue = Volley.newRequestQueue(context);
        }
        return queue;
    }
}
