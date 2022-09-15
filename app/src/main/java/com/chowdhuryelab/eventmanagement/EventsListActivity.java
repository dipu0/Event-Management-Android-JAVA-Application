package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventsListActivity<adapter> extends AppCompatActivity {

    private Button btnCreate, btnExit,btnHistory;
    ListView listView;
    ArrayList<Event> list= new ArrayList<>();
    CustomEventAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventslist);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(EventsListActivity.this, EventInfoActivity.class);
            startActivity(intent);
        });

        listView = findViewById(R.id.lvE);
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(view -> finish());

        loadDb();
        adapter = new CustomEventAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                Intent i = new Intent(EventsListActivity.this, EventInfoActivity.class);
                i.putExtra("EventKey", list.get(position).key);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("@EventLintActivity.onStart");
        loadDb();

    }

    @SuppressLint("StaticFieldLeak")
    private void httpReadRequest(String[] keys, String[] values) {
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
                        //extract key and value from string
                        getRemoteData(data);
                        System.out.println(data+"text/html"+"UTF-8");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

        }.execute();
    }

    private  void loadDb(){

        list.clear();

        KeyValueDB db = new KeyValueDB(getApplicationContext());
        Cursor res = db.getAllKeyValues();

        if (res.getCount() == 0) {
            String[] keys = {"action", "id","semester"};
            String[] values ={"restore","2019-1-60-093","2022-2"};
            httpReadRequest(keys, values);
        }
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

        adapter = new CustomEventAdapter(this, list);
        listView.setAdapter(adapter);

        db.close();

    }

    private void getRemoteData(String Data) throws JSONException {
        //extract key and value from string converting string to JSONObject
        JSONObject jsonData = new JSONObject(Data);
        JSONArray valueObj = jsonData.getJSONArray("events");

        for(int i = 0; i < valueObj.length(); i++)
        {
            JSONObject objects = valueObj.getJSONObject(i);

            String key = objects.getString("key");
            String value = objects.getString("value");

            System.out.println("Remote Data base Key: "+key+"   Value"+value);

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

            value = name+"::"+ place +"::"+dateTime+"::"+capacity+"::"+budget+"::"+email+"::"+phone+"::"+desc+"::"+eventType+"::";

            //Load remote data and save in local db
            KeyValueDB db = new KeyValueDB(getApplicationContext());
            db.updateValueByKey(key,value);
        }
        list.clear();
        KeyValueDB db = new KeyValueDB(getApplicationContext());
        Cursor res = db.getAllKeyValues();

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

        adapter = new CustomEventAdapter(this, list);
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadDb();
        System.out.println("@EventLintActivity.onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("@EventLintActivity.onPause");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        System.out.println("@EventLintActivity.onRestart");
        // re-load events from database after coming back from the next page
        loadDb();
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("@EventLintActivity.onStop");
        // clear the event data from memory as the page is completely hidden by now
        loadDb();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("@EventLintActivity.onDestroy");
    }

}