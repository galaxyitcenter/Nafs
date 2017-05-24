package net.theaimtech.nafs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vijay.jsonwizard.activities.JsonFormActivity;

import net.theaimtech.nafs.network.CustomRequest;
import net.theaimtech.nafs.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView counter;
    Button next;
    private int REQUEST_CODE_GET_JSON = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        next = (Button) findViewById(R.id.btnstartaurvey);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuestion();
            }
        });
        counter = (TextView) findViewById(R.id.tvCounter);
        HashMap<String, String> map = new HashMap<>();
        map.put("id", AppController.getInstance().loggedInUser.getId());

        CustomRequest request = new CustomRequest(Request.Method.POST, ServerConstants.FETCH_STATUS, map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    counter.setText(object.getString("userSurvey"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, this, "Loading...");
        AppController.getInstance().addToRequestQueue(request);

    }


    public void showQuestion() {
        CustomRequest request = new CustomRequest(Request.Method.GET, ServerConstants.SEND_FORM, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(MainActivity.this, JsonFormActivity.class);
                intent.putExtra("json", response);
                startActivityForResult(intent, REQUEST_CODE_GET_JSON);
                Log.d(" DATA_SERVER", response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, this, "Fetching  survey...");
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            Preference.getInstance().clear(this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
      counter.setText((!TextUtils.isEmpty(Preference.getInstance().getValue(this,"counter","")))?Preference.getInstance().getValue(this,"counter",""):"0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GET_JSON && resultCode == RESULT_OK) {
            Log.d(TAG + "DATA COMING", data.getStringExtra("json"));
            Preference.getInstance().put(this, AppController.SURVEY,data.getStringExtra("json"));
            startActivity(new Intent(this,OtpActivity.class));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startSurvery(View view) {
        CustomRequest request = new CustomRequest(Request.Method.GET, ServerConstants.SEND_FORM, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(MainActivity.this, JsonFormActivity.class);
                intent.putExtra("json", response);
                startActivityForResult(intent, REQUEST_CODE_GET_JSON);
                Log.d(" DATA_SERVER", response);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, this, "Fetching  survey...");
        AppController.getInstance().addToRequestQueue(request);

    }
}
