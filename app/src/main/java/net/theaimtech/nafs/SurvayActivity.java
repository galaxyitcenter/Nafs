package net.theaimtech.nafs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SurvayActivity extends AppCompatActivity
{
    EditText ans1,cmt2,ans5,ans6,ans8,ans9,ans11,ans12,cmt13,ans14,cmt15,cmt16,ans18A,ans18B,ans19,ans20,ans22A,ans22B,cmt23,occ,edu;
    RadioGroup rg2,rg3,rg4,rg7,rg10,rg13,rg15,rg16,rg17,rg21,rg23;
    RadioButton rb2,rb3,rb4,rb7,rb10,rb13,rb15,rb16,rb17,rb21,rb23;
    Button Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survay);
        ans1= (EditText) findViewById(R.id.ans1);
        ans5= (EditText) findViewById(R.id.ans5);
        ans6= (EditText) findViewById(R.id.ans6);
        ans8= (EditText) findViewById(R.id.ans8);
        ans9= (EditText) findViewById(R.id.etAns9);
        ans11= (EditText) findViewById(R.id.etAns11);
        ans12= (EditText) findViewById(R.id.etAns12);
        ans14= (EditText) findViewById(R.id.etAns14);
        ans18A= (EditText) findViewById(R.id.etAns18A);
        ans18B= (EditText) findViewById(R.id.etAns18B);
        ans19= (EditText) findViewById(R.id.etAns19);
        ans20= (EditText) findViewById(R.id.etAns20);
        ans22A= (EditText) findViewById(R.id.etAns22A);
        ans22B= (EditText) findViewById(R.id.etAns22B);

        cmt2= (EditText) findViewById(R.id.comentForans2);

        cmt13= (EditText) findViewById(R.id.comentForans13);
        cmt15= (EditText) findViewById(R.id.comentForans15);
        cmt16= (EditText) findViewById(R.id.comentForans16);
        cmt23= (EditText) findViewById(R.id.comentForans23);

        rg2= (RadioGroup) findViewById(R.id.rgAns2);
        rb2= (RadioButton) findViewById(R.id.rbNoForAns2);
        rg3= (RadioGroup) findViewById(R.id.rgAns3);
        rb3= (RadioButton) findViewById(R.id.rbNoForAns3);
        rg4= (RadioGroup) findViewById(R.id.rgAns4);
        rb4= (RadioButton) findViewById(R.id.rbNoForAns4);
        rg7= (RadioGroup) findViewById(R.id.rgAns7);
        rb7= (RadioButton) findViewById(R.id.rbNoForAns7);

        rg10= (RadioGroup) findViewById(R.id.rgAns10);
        rb10= (RadioButton) findViewById(R.id.rbNoForAns10);

        rg13= (RadioGroup) findViewById(R.id.rgAns13);
        rb13= (RadioButton) findViewById(R.id.rbNoForAns13);

        rg15= (RadioGroup) findViewById(R.id.rgAns15);
        rb15= (RadioButton) findViewById(R.id.rbNoForAns15);

        rg16= (RadioGroup) findViewById(R.id.rgAns16);
        rb16= (RadioButton) findViewById(R.id.rbNoForAns16);

        rg17= (RadioGroup) findViewById(R.id.rgAns17);
        rb17= (RadioButton) findViewById(R.id.rbNoForAns17);

        rg21= (RadioGroup) findViewById(R.id.rgAns21);
        rb21= (RadioButton) findViewById(R.id.rbNoForAns21);

        rg23= (RadioGroup) findViewById(R.id.rgAns23);
        rb23= (RadioButton) findViewById(R.id.rbNoForAns23);


        registerButtonControls();


    }
    void registerButtonControls()
    {

        Submit= (Button) findViewById(R.id.btnSubmit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String ans2,ans3,ans4,ans7,ans10,ans13,ans15,ans16,ans17,ans21,ans23;

                int s2=rg2.getCheckedRadioButtonId();
                int s3=rg3.getCheckedRadioButtonId();
                int s4=rg4.getCheckedRadioButtonId();
                int s7=rg7.getCheckedRadioButtonId();
                int s10=rg10.getCheckedRadioButtonId();
                int s13=rg13.getCheckedRadioButtonId();
                int s15=rg15.getCheckedRadioButtonId();
                int s16=rg16.getCheckedRadioButtonId();
                int s17=rg17.getCheckedRadioButtonId();
                int s21=rg21.getCheckedRadioButtonId();
                int s23=rg23.getCheckedRadioButtonId();
                if (TextUtils.isEmpty(ans1.getText().toString().trim()))
                {
                    ans1.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if (TextUtils.isEmpty(ans5.getText().toString().trim()))
                {
                    ans5.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if (TextUtils.isEmpty(ans6.getText().toString().trim()))
                {
                    ans6.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if (TextUtils.isEmpty(ans12.getText().toString().trim()))
                {
                    ans12.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if (TextUtils.isEmpty(ans14.getText().toString().trim()))
                {
                    ans14.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if (TextUtils.isEmpty(ans18A.getText().toString().trim()))
                {
                    ans18A.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if (TextUtils.isEmpty(ans18B.getText().toString().trim()))
                {
                    ans18B.setError(getString(R.string.pleaseEnterAns));
                    return;
                }

                else if (TextUtils.isEmpty(ans19.getText().toString().trim()))
                {
                    ans19.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if (TextUtils.isEmpty(ans20.getText().toString().trim()))
                {
                    ans20.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s2==-1)
                {
                   rb2.setError(getString(R.string.pleaseEnterAns));

                    return;
                }
                else if(s3==-1)
                {
                    rb3.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s4==-1)
                {
                    rb4.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s7==-1)
                {
                    rb7.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s10==-1)
                {
                    rb10.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s13==-1)
                {
                    rb13.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s15==-1)
                {
                    rb15.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s16==-1)
                {
                    rb16.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s17==-1)
                {
                    rb17.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s21==-1)
                {
                    rb21.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else if(s23==-1)
                {
                    rb23.setError(getString(R.string.pleaseEnterAns));
                    return;
                }
                else
                {


                }
            }
        });
    }
}
