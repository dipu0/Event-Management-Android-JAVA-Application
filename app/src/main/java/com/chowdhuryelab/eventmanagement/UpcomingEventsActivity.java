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
        System.out.println("LIST>>>>>>>>>>>>>>>>>:" +list);
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
        list.clear();
        KeyValueDB db = new KeyValueDB(getApplicationContext());
        Cursor res = db.getAllKeyValues();
        if (res.getCount() == 0) {
            return;
        }else{
            while(res.moveToNext()){
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

                Event e = new Event(key, name, place, dateTime,capacity, budget, email, phone, desc,eventType);
                list.add(e);
            }
        }
        db.close();
    }
    @Override
    public void onResume() {
        super.onResume();
        loadDb();
        CustomEventAdapter adapter = new CustomEventAdapter(this, list);
        listView.setAdapter(adapter);
        System.out.println("@MainActivity.onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("@MainActivity.onPause");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println("@MainActivity.onRestart");
        // re-load events from database after coming back from the next page
        loadDb();
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("@MainActivity.onStop");
        // clear the event data from memory as the page is completely hidden by now
        loadDb();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("@MainActivity.onDestroy");
    }

}