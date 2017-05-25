package net.theaimtech.nafs;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.theartofdev.edmodo.cropper.CropImage;

import net.theaimtech.nafs.network.CustomRequest;
import net.theaimtech.nafs.utils.Preference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;


public class OtpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OtpActivity";
    String encoded;
    Button btnTakePhoto;
    String otp = "undefined";
    MenuItem item;
    private LinearLayout llPhotoContact;
    private ImageView ivUserImage;
    private LinearLayout llGetOTP;
    private Uri mCropImageUri;
    private EditText etName;
    private EditText etPhoneNumber;
    private EditText etOTP;

    private void assignViews() {
        etName = (EditText) findViewById(R.id.etName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etOTP = (EditText) findViewById(R.id.etOTP);
        btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(this);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                /*.setCropShape(CropImageView.CropShape.RECTANGLE)// Uncomment this for Rectangle Cropping.
                .setFixAspectRatio(true)*/
                .start(this);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    ivUserImage.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    btnTakePhoto.setText("Re-Take");
                    item.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e("ERROR in CROPPING", error.getMessage());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            if (TextUtils.isEmpty(etName.getText())) {
                etName.setError("Please enter name");
                return false;
            }
            if (TextUtils.isEmpty(etPhoneNumber.getText())) {
                etPhoneNumber.setError("Please enter number");
                return false;
            }

            sendOtpRequest();

            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    private void sendOtpRequest() {
        HashMap<String, String> parmas = new HashMap<>();
        parmas.put("number", etPhoneNumber.getText().toString().trim());
        parmas.put("uid", AppController.getInstance().loggedInUser.getId());
        CustomRequest request = new CustomRequest(Request.Method.POST, ServerConstants.GENERATE_OTP, parmas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(" DATA_SERVER", response);
                try {
                    JSONObject obj = new JSONObject(response);
                    otp = !TextUtils.isEmpty(obj.getString("otp")) ? obj.getString("otp") : "undefined";
                    llGetOTP.setVisibility(View.VISIBLE);
                    llPhotoContact.setVisibility(View.GONE);
                    item.setVisible(false);
                } catch (JSONException e) {
                    Log.i(TAG, "onResponse: " + e.getMessage());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, this, "Sending OTP...");
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.otp_menu, menu);//Menu Resource, Menu
        item = menu.findItem(R.id.action_save);
        item.setVisible(false);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        assignViews();
        llPhotoContact = (LinearLayout) findViewById(R.id.llPhotoContact);
        findViewById(R.id.btnTakePhoto).setOnClickListener(this);
        ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
        llGetOTP = (LinearLayout) findViewById(R.id.llGetOTP);
        findViewById(R.id.btnResend).setOnClickListener(this);
        findViewById(R.id.btnSubmit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTakePhoto:
                startActivityForResult(CropImage.getCameraIntent(this, null), CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
                break;
            case R.id.btnResend:
                sendOtpRequest();
                break;
            case R.id.btnSubmit:
                if (etOTP.getText().toString().equalsIgnoreCase(otp)) {
                    String data = Preference.getInstance().getValue(this, AppController.SURVEY, "");
                    if (!TextUtils.isEmpty(data)) {
                        HashMap<String, String> parmas = new HashMap<>();
                        parmas.put("data", data);
                        parmas.put("photo", encoded);
                        parmas.put("name", etName.getText().toString().trim());
                        parmas.put("userid", AppController.getInstance().loggedInUser.getId());
                        Log.i("PARAMS_CUST_REQUEST",encoded);
                        CustomRequest request = new CustomRequest(Request.Method.POST, ServerConstants.SUBMIT_SURVEY, parmas, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d(" DATA_SERVER", response);
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    Preference.getInstance().put(OtpActivity.this, "counter", obj.getString("userSurvey"));
                                    AppController.getInstance().loggedInUser.setNoOfSurvay(obj.getString("userSurvey"));
                                    finish();
                                } catch (JSONException e) {
                                    Log.i(TAG, "onResponse: " + e.getMessage());
                                }
                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }, this, "Submitting  survey...");
                        AppController.getInstance().addToRequestQueue(request);
                    }
                }else {
                    Toast.makeText(this, "Wrong OTP Please try again!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}