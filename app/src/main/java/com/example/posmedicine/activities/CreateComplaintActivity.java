package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.example.posmedicine.Textview_lato_thin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateComplaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaint);
        Textview_lato_thin registeredDate = (Textview_lato_thin) findViewById(R.id.complaintRegisteredDate);
        registeredDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
