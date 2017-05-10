package net.theaimtech.nafs;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import net.theaimtech.nafs.network.LruBitmapCache;
import net.theaimtech.nafs.pojo.User;
import net.theaimtech.nafs.utils.Preference;


@TargetApi(Build.VERSION_CODES.DONUT)

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();
    public static User loggedInUser;
    private static AppController application;
    private static AppController mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    public static synchronized AppController getInstance() {
        String user = Preference.getInstance().getValue(getApplication(), "user", "");
        if (!TextUtils.isEmpty(user)) {
            loggedInUser = (User) Preference.stringToObject(user);
        }

        return mInstance;
    }

    public static AppController getApplication() {
        return application;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {

        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    @Override
    public void onCreate() {
        enableStrictMode();
        super.onCreate();

        application = this;
        mInstance = this;
    }

    public ImageLoader getImageLoader() {

        getRequestQueue();
        if (getImageLoader() == null) {
            mImageLoader = new ImageLoader(this.getRequestQueue(), new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        application = null;

    }

    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build());
    }

}
