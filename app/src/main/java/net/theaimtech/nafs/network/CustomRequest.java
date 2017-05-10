package net.theaimtech.nafs.network;

import android.app.Activity;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Aimanzaki Kagzi on 4/16/2016.
 */
public class CustomRequest extends Request<String> {
    Activity activity;
    SweetAlertDialog dialg;
    SweetAlertDialog completion;
    private Response.Listener<String> listener;
    private Map<String, String> params;

   /* public CustomRequest(String url, Map<String, String> params,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }*/

    public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<String> reponseListener, Response.ErrorListener errorListener, final Activity activity, final String loaderMessage, final int completionalertType, final String completionmessage) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
        this.activity = activity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialg = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
                dialg.setTitleText(loaderMessage);
                dialg.setCancelable(false);
                dialg.show();
                if (completionalertType != 0) {
                    completion = new SweetAlertDialog(activity, completionalertType);
                    completion.setTitleText(completionmessage);
                    completion.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            activity.finish();
                        }
                    });
                }
            }
        });

    }

    public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<String> reponseListener, Response.ErrorListener errorListener, final Activity activity, final String loaderMessage) {
        super(method, url, errorListener);
        Log.i("URL_CUSTOM_REQUEST:", url);
        if (params != null) {
            Log.i("DATA_CUSTOM_REQUEST:", params.toString());
        } else {
            Log.i("DATA_CUSTOM_REQUEST:", "No Parameters");
        }

        this.listener = reponseListener;
        this.params = params;
        this.activity = activity;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialg = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
                dialg.setTitleText(loaderMessage);
                dialg.setCancelable(false);
                dialg.show();

            }
        });

    }

    public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<String> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        Log.i("URL_CUSTOM_REQUEST:", url);
        if (params != null) {
            Log.i("DATA_CUSTOM_REQUEST:", params.toString());
        } else {
            Log.i("DATA_CUSTOM_REQUEST:", "No Parameters");
        }
        this.listener = reponseListener;
        this.params = params;

    }

    @Override
    protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
        return params;
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dialg.cancel();
                    if (completion != null)
                        dialg.show();
                }
            });
        }
        try {

            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.i("DATA_CUSTOM_RESPONSE:", jsonString);
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

    }

}
