package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    EditText Name, Place, DateTime,Capacity, Budget, Email, Phone, Description;
    TextView errortv;
    RadioGroup radioGrp;
    RadioButton Indoor, Outdoor, Online, rdbtn;
    Button btnCancel,btnShare, btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    }
    private void showDialog(String message, String title, String key1, String key2){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setCancelable(false)
                .setPositiveButton(key1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Util.getInstance().deleteByKey(MainActivity.this, key);
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
}