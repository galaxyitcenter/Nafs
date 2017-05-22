package net.theaimtech.nafs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import net.theaimtech.nafs.network.CustomRequest;
import net.theaimtech.nafs.pojo.User;
import net.theaimtech.nafs.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    Button button;
    EditText userName;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.button);
        userName = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        String loggedInUser=Preference.getInstance().getValue(this,"user","");
        if(!TextUtils.isEmpty(loggedInUser)){
            AppController.loggedInUser=(User)Preference.stringToObject(loggedInUser);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userName.getText())) {
                    userName.setError("Username Required");
                    return;
                }
                if (TextUtils.isEmpty(etPassword.getText())) {
                    etPassword.setError("Password Required");
                    return;
                }
                HashMap<String, String> params = new HashMap<>();
                params.put("username", userName.getText().toString());
                params.put("password", etPassword.getText().toString());
                params.put("mac",getMacAddr());
                CustomRequest request = new CustomRequest(Request.Method.GET,ServerConstants.LOGIN+"?username="+userName.getText().toString()+"&password="+etPassword.getText().toString()+"&mac="+getMacAddr(), null, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("404")) {
                            Log.e(TAG, response);
                            Toast.makeText(LoginActivity.this, "Username/Password/MAC is wrong.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            JSONObject obj = new JSONObject(response);
                            User user = new User();
                            user.setId(obj.getString("id"));
                            user.setUsername(obj.getString("username"));
                            user.setAddress(obj.getString("address"));
                            user.setId_token(obj.getString("id_token"));
                            AppController.loggedInUser = user;
                            Preference.getInstance().put(LoginActivity.this, "user", Preference.objectToString(user));
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Something went wrong! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }, LoginActivity.this, "Logging in...");
                AppController.getInstance().addToRequestQueue(request);
            }
        });
    }
    public static String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
}
