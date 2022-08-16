package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, phone, userid, passwword, re_passwword;
    private TextView tview;
    private CheckBox box1, box2;
    private Button cln, go;

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
        box2 = (CheckBox)  findViewById(R.id.checkbox2);

        tview = (TextView) findViewById(R.id.logintxt);
         cln = (Button) findViewById(R.id.btnCnl);
         go = (Button) findViewById(R.id.btnGo);

         tview.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  name.setVisibility(View.GONE);
                  phone.setVisibility(View.GONE);
                  email.setVisibility(View.GONE);
                  re_passwword.setVisibility(View.GONE);

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