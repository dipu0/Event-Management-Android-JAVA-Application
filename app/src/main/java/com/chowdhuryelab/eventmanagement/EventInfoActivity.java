package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.firestore.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class EventInfoActivity extends AppCompatActivity {
    EditText Name, Place, DateTime,Capacity, Budget, Email, Phone, Description;
    TextView errortv;
    RadioGroup radioGrp;
    RadioButton Indoor, Outdoor, Online, rdbtn;
    Button btnCancel,btnShare, btnSave;
    String value, key;
    private String existingKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventinfo);

        Name = (EditText)findViewById(R.id.Name);
        Place = (EditText)findViewById(R.id.Place);
        DateTime =(EditText) findViewById(R.id.DateTime);
        Capacity = (EditText)findViewById(R.id.Capacity);
        Budget = (EditText)findViewById(R.id.Budget);
        Email = (EditText)findViewById(R.id.Email);
        Phone = (EditText)findViewById(R.id.Phone);
        Description = (EditText)findViewById(R.id.Description);

        radioGrp = (RadioGroup) findViewById(R.id.rg1);
        Indoor = (RadioButton) findViewById(R.id.Indoor);
        Outdoor = (RadioButton) findViewById(R.id.Outdoor);
        Online = (RadioButton) findViewById(R.id.Online);

        btnCancel = (Button) findViewById(R.id.btnCnl);
        btnShare = (Button) findViewById(R.id.btnShare);
        btnSave = (Button) findViewById(R.id.btnSave);

        errortv = (TextView) findViewById(R.id.errortv);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Cancel button clicked");
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("Share button clicked");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = "";

                if (Name.getText().toString().length()<2) {
                    Name.setError("Error in name field");
                    errortv.setText(errortv.getText() + "\n Error in name field");
                    message = errortv.getText().toString();
                }
                else {
                    System.out.println("Name :" + Name.getText());
                    errortv.setText(message);
                }

                if (Place.getText().toString().trim().length()<5) {
                    Place.setError("Error place field");
                    errortv.setText(errortv.getText() + "\n Error in place field");
                    message = errortv.getText().toString();
                }
                else{
                    System.out.println("Place :" + Place.getText());
                    errortv.setText(message);
                }

                int selectedId = radioGrp.getCheckedRadioButtonId();
                rdbtn = (RadioButton) findViewById(selectedId);
                if(radioGrp.getCheckedRadioButtonId() <= 0){
                    Indoor.setError("Select Item");
                    Outdoor.setError("Select Item");
                    Online.setError("Select Item");
                    errortv.setText(errortv.getText() + "\n Error type field");
                    message = errortv.getText().toString();
                }
                else{
                    System.out.println("Type :" + rdbtn.getText());
                    errortv.setText(message);
                }

                if (DateTime.getText().toString().trim().length()<5) {
                    DateTime.setError("Error in Date & Time field");
                    errortv.setText(errortv.getText() + "\n Error in Date & Time field");
                    message = errortv.getText().toString();
                }
                else{
                    System.out.println("Date & Time :" + DateTime.getText());
                    errortv.setText(message);
                }

                if (Capacity.getText().toString().isEmpty() || Integer.valueOf(Capacity.getText().toString())<=0) {
                    Capacity.setError("Error in capacity field");
                    errortv.setText(errortv.getText() + "\n Error in capacity field");
                    message = errortv.getText().toString();
                }
                else {
                    System.out.println("Capacity :" + Capacity.getText());
                    errortv.setText(message);
                }

                if (Budget.getText().toString().isEmpty() || Integer.valueOf(Budget.getText().toString())<=0) {
                    Budget.setError("Error in Budget field");
                    errortv.setText(errortv.getText() + "\n Error in Budget field");
                    message = errortv.getText().toString();
                }
                else {
                    System.out.println("Budget :" + Budget.getText());
                    errortv.setText(message);
                }

                if (Email.getText().toString().trim().length()<5) {
                    Email.setError("Error in Email fiel");
                    errortv.setText(errortv.getText() + "\n Error in Email field");
                    message = errortv.getText().toString();
                }
                else {
                    System.out.println("Email :" + Email.getText());
                    errortv.setText(message);
                }

                if (Phone.getText().toString().trim().length()<10) {
                    Phone.setError("Error in Phone field");
                    errortv.setText(errortv.getText() + "\n Error in Phone field");
                    message = errortv.getText().toString();
                }
                else {
                    System.out.println("Phone :" + Phone.getText());
                    errortv.setText(message);
                }
                if (Description.getText().toString().trim().length()<10) {
                    Description.setError("Error in Description field");
                    errortv.setText(errortv.getText() + "\n Error in Description field");
                    message = errortv.getText().toString();
                }
                else {
                    System.out.println("Description :" + Description.getText());
                    errortv.setText(message);
                }

//                String message = "Do you want to delete event- "+events.get(position).name+"?";
//                showDialog(message, "Delete Event", event.get(position.key));

                if(message.isEmpty()){
                    showDialog("Do you want to save the event info?","info","Yes","No");
                }else{
                    showDialog(message,"Error","Ok","Back");
                }

            }


        });

        Intent intent = getIntent();
        existingKey = intent.getStringExtra("EventKey");

        loadDb();

    }

    private void showDialog(String message, String title, String key1, String key2){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false)
                .setPositiveButton(key1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       // Util.getInstance().deleteByKey(EventInfoActivity.this, key);

                        if(key1=="Yes"){
                            SaveInDb();
                            loadDb();
                            finish();
                        }
                        dialog.cancel();
                        // loadDate();
                        // adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(key2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private  void loadDb(){

    KeyValueDB db = new KeyValueDB(getApplicationContext());
    String value = db.getValueByKey(existingKey);
    if(value != null) {
        String[] fieldValues = value.split("::");
        String name = fieldValues[0];
        String place = fieldValues[1];
        String dateTime = fieldValues[2];
        String capacity = fieldValues[3];
        String budget = fieldValues[4];
        String email = fieldValues[5];
        String phone = fieldValues[6];
        String desc = fieldValues[7];
        String eventType = fieldValues[8];

        Name.setText(name);
        DateTime.setText(dateTime);
        Place.setText(place);
        Capacity.setText(capacity);
        Budget.setText(budget);
        Email.setText(email);
        Phone.setText(phone);
        Description.setText(desc);

        if (eventType.equals("Indoor")) {
            Indoor.setChecked(true);
        } else if (eventType.equals("Outdoor")) {
            Outdoor.setChecked(true);
        } else if (eventType.equals("Online")) {
            Online.setChecked(true);
        }
    }


}
    private void SaveInDb(){
        String name = Name.getText().toString().trim();
        String place = Place.getText().toString().trim();
        String time = DateTime.getText().toString().trim();
        String capacity = Capacity.getText().toString().trim();
        String budget = Budget.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String phone = Phone.getText().toString().trim();
        String description = Description.getText().toString().trim();
        String eventType = rdbtn.getText().toString();

        value = name+"::"+ place +"::"+time+"::"+capacity+"::"+budget+"::"+email+"::"+phone+"::"+description+"::"+eventType+"::";

        if(existingKey != null){
            key = existingKey;
        }else key = name+"::"+System.currentTimeMillis();

        String[] keys = {"action", "id","semester", "key","event"};
        String[] values ={"backup","2019-1-60-093","2022-2",key, value};

        httpRequest(keys, values);

        KeyValueDB db = new KeyValueDB(getApplicationContext());
        db.updateValueByKey(key,value);

    }
    @SuppressLint("StaticFieldLeak")
    private void httpRequest(String[] keys, String[] values) {
        new AsyncTask<Void, Void,String>(){
            @Override
            protected String doInBackground(Void...param){
                try{
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    for(int i =0; i<keys.length;i++){
                        params.add(new BasicNameValuePair(keys[i],values[i]));
                    }
                    String data = JSONParser.getInstance().makeHttpRequest("https://muthosoft.com/univ/cse489/index.php", "POST",params);
                    return data;
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
        @Override
            protected void onPostExecute(String data){
                if(data != null){
                    try{
                        System.out.println(data+"text/html"+"UTF-8");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        }

        }.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDb();
    }
}