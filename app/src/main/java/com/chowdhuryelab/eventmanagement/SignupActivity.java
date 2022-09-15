package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, phone, userid, passwword, re_passwword;
    private TextView topTv,tview;
    private CheckBox box1, box2;
    private Button cln, go;
    private LinearLayout lln, lle, llp, llr,lllogin;

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

        lllogin= findViewById(R.id.ll_already_have_a_account); //name
        llr= findViewById(R.id.LL4); //re-pass
        llp= findViewById(R.id.LL3); //phone
        lle= findViewById(R.id.LL2); //email
        lln= findViewById(R.id.LL1); //name


        box1 = (CheckBox)  findViewById(R.id.checkbox1);
        box2 = (CheckBox)  findViewById(R.id.checkbox2);

        tview = (TextView) findViewById(R.id.logintxt);
        topTv = (TextView) findViewById(R.id.topTv);
         go = (Button) findViewById(R.id.btnGo);

        getSharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        myEdit = getSharedPreferences.edit();


        if(getSharedPreferences.contains("userId") && getSharedPreferences.contains("userPwd")){
            loginUi();
        }else userSignup();


        tview.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  loginUi();

              }

         });

        findViewById(R.id.btnCnl).setOnClickListener(view -> finish());

}

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    private void loginUi(){

        topTv.setText("Login In");
        llr.setVisibility(View.GONE);
        llp.setVisibility(View.GONE);
        lle.setVisibility(View.GONE);
        lln.setVisibility(View.GONE);
        lllogin.setVisibility(View.GONE);


        if (getSharedPreferences.contains("userId")) {
            if(getSharedPreferences.getBoolean("rememberUserId",true)){
                box1.setChecked(true);
                userid.setText(getSharedPreferences.getString("userId", ""));
            }
        }
        if (getSharedPreferences.contains("userPwd")) {
            if(getSharedPreferences.getBoolean("rememberpass",true)){
                box2.setChecked(true);
                passwword.setText(getSharedPreferences.getString("userPwd", ""));
            }
        }
        else{
            Toast.makeText(SignupActivity.this, "No user found" , Toast.LENGTH_LONG).show();
        }

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matchInfo="";

                boolean rememberUser = box1.isChecked();
                boolean rememberPass = box2.isChecked();

                if(getSharedPreferences.getString("userId","").equals(userid.getText().toString())){
                    if(getSharedPreferences.getString("userPwd","").equals(passwword.getText().toString())){

                        myEdit.putBoolean("rememberUserId", rememberUser);
                        myEdit.putBoolean("rememberpass", rememberPass);
                        myEdit.commit();

                    Intent i = new Intent(SignupActivity.this, EventsListActivity.class);
                    startActivity(i);
                    finish();
                    }else matchInfo = matchInfo+"Password ";
                }else matchInfo = matchInfo+"User ID ";

                if(!matchInfo.isEmpty()){
                    matchInfo = matchInfo+" does not match";
                    Toast.makeText(SignupActivity.this, matchInfo, Toast.LENGTH_LONG).show();
                }else  Toast.makeText(SignupActivity.this, "Login successful", Toast.LENGTH_LONG).show();


            }
        });


    }

    private void userSignup() {

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userPhone = phone.getText().toString().trim();
                String userId = userid.getText().toString().trim();
                String userPwd = passwword.getText().toString().trim();
                String userRePwd = re_passwword.getText().toString().trim();
                boolean rememberUser = box1.isChecked();
                boolean rememberPass = box2.isChecked();

                boolean error= false;
                if (name.getText().toString().length()<2) {
                    name.setError("Error in name field");
                    error = true;
                }
                else {
                    System.out.println("Name :" + name.getText());
                }

                if (!isEmailValid(email.getText().toString())) {
                    email.setError("Error in name field");
                    error = true;
                }
                else {
                    System.out.println("Email :" + email.getText());
                }
                if (phone.getText().toString().length()<10) {
                    name.setError("Error in Phone field");
                    error = true;
                }
                else {
                    System.out.println("Phone :" + phone.getText());
                }
                if (passwword.getText().toString().length()<5) {
                    name.setError("Error in password field");
                    error = true;
                }
                else {

                }
                if(!error){

                    System.out.println("user Name: " + userName);
                    System.out.println("user Email: " + userEmail);
                    System.out.println("user Phone Number: " + userPhone);
                    System.out.println("user Id: " + userId);
                    System.out.println("user pwd: " + userPwd);

                    if (userPwd.equals(userRePwd)) {
                        myEdit.putString("userName", userName);
                        myEdit.putString("userEmail", userEmail);
                        myEdit.putString("userPhone", userPhone);
                        myEdit.putString("userId", userId);
                        myEdit.putString("userPwd", userPwd);
                        myEdit.putBoolean("rememberUserId", rememberUser);
                        myEdit.putBoolean("rememberpass", rememberPass);
                        myEdit.commit();

                        Toast.makeText(SignupActivity.this, "Data Saved", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, EventsListActivity.class));
                        finish();
                    } else {
                        Toast.makeText(SignupActivity.this, "Confirm Password doesn't match.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }
}
