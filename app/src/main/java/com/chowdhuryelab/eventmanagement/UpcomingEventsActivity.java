package com.chowdhuryelab.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class UpcomingEventsActivity extends AppCompatActivity {

    private Button btnCreate, btnExit,btnHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_events);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(view -> {
            Intent intent = new Intent(UpcomingEventsActivity.this, EventInfoActivity.class);
            startActivity(intent);
        });
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(view -> finish());
    }

}