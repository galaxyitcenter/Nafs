package net.theaimtech.nafs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.theaimtech.nafs.Model.VoterId;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Aimtechpc5 on 5/13/2017.
 */

public class ShowDetailsActivity extends AppCompatActivity {
    private TextView tvNo;

    private TextView tvName;

    private TextView tvPartNo;

    private TextView tvSerialNo;

    private TextView tvEdiitorsAdd;

    private TextView tvSurname;

    private TextView tvSFirstName;

    private TextView tvMiddletName;

    private TextView tvAge;

    private TextView tvGender;

    private TextView tvBirthDate;

    private TextView tvPhotoIdNo;
    Button showNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        registerControls();



        if(getIntent().hasExtra("json")){
            try {
                JSONArray personDetails = new JSONArray(getIntent().getExtras().getString("json"));

                VoterId voterId = VoterId.parseJSON(personDetails.getString(0));
                if (voterId != null) {
                        tvNo.setText(voterId.getNo());
                        tvName.setText(voterId.getName());
                        tvPartNo.setText(voterId.getPartNo());
                        tvSerialNo.setText(voterId.getSerialNo());
                        tvEdiitorsAdd.setText(voterId.getEdditorsAddress());
                        tvSurname.setText(voterId.getSurname());
                        tvSFirstName.setText(voterId.getFirstName());
                        tvMiddletName.setText(voterId.getMiddelNAme());
                        tvAge.setText(voterId.getAge());
                        tvGender.setText(voterId.getGender());
                        tvBirthDate.setText(voterId.getBirtDate());
                        tvPhotoIdNo.setText(voterId.getPhotoIdCardNo());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public void registerControls()
    {
        tvNo = (TextView) findViewById(R.id.tvNo);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPartNo = (TextView) findViewById(R.id.tvPartNo);
        tvSerialNo = (TextView) findViewById(R.id.tvSerialNo);
        tvEdiitorsAdd = (TextView) findViewById(R.id.tvEdiitorsAdd);
        tvSurname = (TextView) findViewById(R.id.tvSurname);
        tvSFirstName = (TextView) findViewById(R.id.tvSFirstName);
        tvMiddletName = (TextView) findViewById(R.id.tvMiddletName);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvGender = (TextView) findViewById(R.id.tvGender);
        tvBirthDate = (TextView) findViewById(R.id.tvBirthDate);
        tvPhotoIdNo = (TextView) findViewById(R.id.tvPhotoIdNo);

        showNext= (Button) findViewById(R.id.btnShowNext);
        showNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(ShowDetailsActivity.this,SurveyActivity.class);
                startActivity(view);
            }
        });
    }

}