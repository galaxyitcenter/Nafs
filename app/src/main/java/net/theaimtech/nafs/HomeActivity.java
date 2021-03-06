package net.theaimtech.nafs;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vijay.jsonwizard.activities.JsonFormActivity;

import net.theaimtech.nafs.network.CustomRequest;
import net.theaimtech.nafs.utils.Preference;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    Button bopen;
    private WebView webview;
    private ProgressDialog diag;
    private boolean isLoadingGujarat;
    private int REQUEST_CODE_GET_JSON = 111;

    @Override
    protected void onResume() {
        super.onResume();
        isLoadingGujarat = false;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Welcome " + AppController.getInstance().loggedInUser.getUsername());

        CustomRequest request = new CustomRequest(Request.Method.GET, ServerConstants.SEND_FORM, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(HomeActivity.this, JsonFormActivity.class);
                intent.putExtra("json", response);
                startActivityForResult(intent, REQUEST_CODE_GET_JSON);
                Log.d(" DATA_SERVER",response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, this, "Fetching  survey...");
        AppController.getInstance().addToRequestQueue(request);

        webview = (WebView) findViewById(R.id.wvMain);
        bopen = (Button) findViewById(R.id.btnShowQ);
        bopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ShowDetailsActivity.class));
            }
        });
        webview.setWebViewClient(new MyWebViewClient());
        WebSettings webSettings = webview.getSettings();
        webview.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 2.0; en-us; Droid Build/ESD20) AppleWebKit/530.17 (KHTML, like Gecko) Version/4.0 Mobile Safari/530.17");
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setDomStorageEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        webview.getSettings().setSaveFormData(false);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.loadUrl(ServerConstants.VOTER_ID);
        diag = new ProgressDialog(this);
        diag.setMessage("Loading...");
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

    public void getDetails(String data) {

        HashMap<String, String> map = new HashMap<>();
        map.put("uid", AppController.getInstance().loggedInUser.getUsername());
        map.put("data", data);


        CustomRequest request = new CustomRequest(Request.Method.POST, ServerConstants.LOGIN, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent view = new Intent(HomeActivity.this,ShowDetailsActivity.class);

                view.putExtra("json", response);
                startActivity(view);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, this, "Submitting Data...");
        AppController.getInstance().addToRequestQueue(request);

    }

    private class MyWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
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
            view.setVisibility(View.GONE);
            bopen.setVisibility(View.VISIBLE);
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
                diag.show();

            }
        }
    }

    class MyJavaScriptInterface {
        @SuppressWarnings("unused")
        @JavascriptInterface
        public void processHTML(final String html) {
            Log.i("processed html", html);

            Thread OauthFetcher = new Thread(new Runnable() {

                @SuppressWarnings("deprecation")
                @Override
                public void run() {

                    final String oAuthDetails = Html.fromHtml(html).toString();
                    Log.i("oAuthDetails", oAuthDetails);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            diag.dismiss();
                            webview.setVisibility(View.VISIBLE);
                            webview.loadData(html, "text/html", "UTF-8");
                            bopen.setVisibility(View.VISIBLE);
                            getDetails(html);

                        }
                    });


                }
            });
            OauthFetcher.start();
        }
    }
}
