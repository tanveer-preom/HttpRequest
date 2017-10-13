package flr.ld.bsa.app.httprequest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Siyam on 26-Dec-15.
 */
public class HttpRequest {

    private KeyValue[] params,headers;
    private String url;
    private boolean urlencoded;
    private int reqId;
    private HttpListener activity;
    private Bundle bundle;
    private final int request_time_out=8000;

    private JSONObject jsonData;



    public HttpRequest(String url,int requestId,HttpListener mActivity) {
        this.url =url;
        reqId =requestId;
        activity =mActivity;
    }

    public void setParams(KeyValue... params){
        this.params = params;
    }

    private class CustomRequest extends StringRequest
    {

        public CustomRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String,String> mParams = new HashMap<>();
            if(params!=null)
            for(int i=0;i<params.length;i++)
            {
                mParams.put(params[i].getKey(),params[i].getValue());
                Log.i("params","key = "+params[i].getKey()+" value= "+params[i].getValue());
            }
            Log.i("params","key = "+"sent");
            return mParams;
        }

        @Override
        public byte[] getBody() throws AuthFailureError {
            if(jsonData!=null)
            {
                Log.i("tanvy",jsonData.toString());
                return jsonData.toString().getBytes();

            }
            else
            {
                return super.getBody();
            }
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            Map<String,String> params = new HashMap<>();
            if(headers!=null)
            for(int i=0;i<headers.length;i++)
            {
                params.put(headers[i].getKey(),headers[i].getValue());

            }

            //headers.put();
            return params;
        }

        @Override
        public String getBodyContentType() {
            if(urlencoded)
                return "application/x-www-form-urlencoded";
            else
                return super.getBodyContentType();
        }
    }
    public void setRawJson(JSONObject data)
    {
        jsonData =data;
    }
    public void urlencoded(boolean encoded)
    {
        this.urlencoded = encoded;

    }


    public void setHeaders(KeyValue... headers)
    {
        this.headers = headers;
    }

    public void execute(String method)
    {
        Response.Listener<String> rlistener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                activity.respond(response,reqId,bundle);

            }
        };
        Response.ErrorListener elistener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = new String(error.networkResponse.data);
                activity.errorRespond(error,reqId,message,bundle);

            }
        };

        CustomRequest stringRequest;
        if(method.equals("post"))
        {
            stringRequest = new CustomRequest(Request.Method.POST, url, rlistener , elistener);
            Log.i("requestedd","post");

        }
        else
        {
            stringRequest = new CustomRequest(Request.Method.GET, url, rlistener , elistener);
            Log.i("request","get");
        }

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                request_time_out,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestManager.getRequestQueue(activity.getContext()).add(stringRequest);


    }
    public void putExtras(Bundle bundle)
    {
        this.bundle = bundle;
    }
    public Bundle getExtras()
    {
        return bundle;
    }

}
