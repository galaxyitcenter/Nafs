package net.theaimtech.nafs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vijay.jsonwizard.activities.JsonFormActivity;

import net.theaimtech.nafs.network.CustomRequest;
import net.theaimtech.nafs.utils.Preference;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int REQUEST_CODE_GET_JSON = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomRequest request = new CustomRequest(Request.Method.GET, ServerConstants.SEND_FORM, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent = new Intent(MainActivity.this, JsonFormActivity.class);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_logout) {
            Preference.getInstance().clear(this);

        }
       return true;
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
            Log.d(TAG+"DATA COMING", data.getStringExtra("json"));
            HashMap<String,String> parmas= new HashMap<>();
            parmas.put("data",data.getStringExtra("json"));
            CustomRequest request = new CustomRequest(Request.Method.POST, ServerConstants.SUBMIT_SURVEY, parmas, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(" DATA_SERVER",response);
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }, this, "Submitting  survey...");
            AppController.getInstance().addToRequestQueue(request);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
