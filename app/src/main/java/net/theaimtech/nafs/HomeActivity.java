package net.theaimtech.nafs;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import net.theaimtech.nafs.utils.Preference;

public class HomeActivity extends AppCompatActivity {
    WebView webview;
    ProgressDialog diag;
    boolean isLoadingGujarat;

    @Override
    protected void onResume() {
        super.onResume();
        isLoadingGujarat = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Welcome " + AppController.loggedInUser.getUsername());
        webview = (WebView) findViewById(R.id.wvMain);
        webview.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = webview.getSettings();
        webview.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setDomStorageEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.loadUrl(ServerConstants.VOTER_ID);
        diag = new ProgressDialog(this);
        diag.setTitle("Loading...");
        diag.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview.canGoBack()) {
                        webview.goBack();
                    } else {

                        webview.removeAllViews();
                        webview.destroy();
                        this.finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_startover:
                this.recreate();
                return true;
            case R.id.action_logout:
                Preference.getInstance().clear(this);
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                webview.removeAllViews();
                webview.destroy();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }

    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }


    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().contains("gujarat")) {
                Toast.makeText(HomeActivity.this, "Moved to Gujarat Website", Toast.LENGTH_SHORT).show();
                return false;
            }
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (isLoadingGujarat) {
                webview.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementById('gdGuj').innerHTML+'</html>');");
            }
            diag.dismiss();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            if (url.equals("http://erms.gujarat.gov.in/ceo-gujarat/master/images/banner.jpg")) {
                view.setVisibility(View.GONE);
                isLoadingGujarat = true;
            }
        }
    }

    class MyJavaScriptInterface {
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void processHTML(final String html) {
            Log.i("processed html", html);

            Thread OauthFetcher = new Thread(new Runnable() {

                @Override
                public void run() {

                    String oAuthDetails = null;
                    oAuthDetails = Html.fromHtml(html).toString();
                    Log.i("oAuthDetails", oAuthDetails);

                }
            });
            OauthFetcher.start();
        }
    }
}
