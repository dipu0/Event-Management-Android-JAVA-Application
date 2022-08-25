package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, phone, userid, passwword, re_passwword;
    private TextView tview;
    private CheckBox box1, box2;
    private Button cln, go;
    private LinearLayout lln, lle, llp, llr;

    SharedPreferences getSharedPreferences;
    SharedPreferences.Editor myEdit;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name= (EditText) findViewById(R.id.name);
        email= (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.Phone);
        userid= (EditText) findViewById(R.id.uid);
        passwword= (EditText) findViewById(R.id.pass);
        re_passwword= (EditText) findViewById(R.id.re_pass);

        box1 = (CheckBox)  findViewById(R.id.checkbox1);

        llr= findViewById(R.id.LL4);
        llp= findViewById(R.id.LL3);
        lle= findViewById(R.id.LL2);
        lln= findViewById(R.id.LL1);

        box2 = (CheckBox)  findViewById(R.id.checkbox2);

        tview = (TextView) findViewById(R.id.logintxt);
         cln = (Button) findViewById(R.id.btnCnl);
         go = (Button) findViewById(R.id.btnGo);

        getSharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        myEdit = getSharedPreferences.edit();

         tview.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  llr.setVisibility(View.GONE);
                  llp.setVisibility(View.GONE);
                  lle.setVisibility(View.GONE);
                  lln.setVisibility(View.GONE);

              }

         });

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().length()<2) {
                    name.setError("Error in name field");
                }
                else {
                    System.out.println("Name :" + name.getText());
                }
                if (email.getText().toString().length()<2) {
                    email.setError("Error in name field");
                }
                else {
                    System.out.println("Email :" + email.getText());
                }

            }
        });

}
}
