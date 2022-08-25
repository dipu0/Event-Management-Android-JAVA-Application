package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class UpcomingEventsActivity<adapter> extends AppCompatActivity {

    private Button btnCreate, btnExit,btnHistory;
    ListView listView;
    ArrayList<Event> list= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(UpcomingEventsActivity.this, EventInfoActivity.class);
            startActivity(intent);
        });

        listView = findViewById(R.id.lvE);
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(view -> finish());

        loadDb();

        CustomEventAdapter adapter = new CustomEventAdapter(this, list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Intent i = new Intent(UpcomingEventsActivity.this, EventInfoActivity.class);
                i.putExtra("EventKey", list.get(position).key);
                startActivity(i);
            }
        });
    }

    private  void loadDb(){
        KeyValueDB db = new KeyValueDB(getApplicationContext());
        Cursor res = db.getAllKeyValues();
        if (res.getCount() == 0) {
            return;
        }else{
            while(res.moveToNext()){
                list.clear();
                String key = res.getString(0);
                String value = res.getString(1);

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

                Event e = new Event(key, name, place, dateTime,eventType,capacity, budget, email, phone, desc);
                list.add(e);
            }
        }
        db.close();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadDb();
        CustomEventAdapter adapter = new CustomEventAdapter(this, list);
        listView.setAdapter(adapter);
    }
}