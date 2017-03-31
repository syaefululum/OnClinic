package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato_Thin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateComplaintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaint);
        TextView_Lato_Thin registeredDate = (TextView_Lato_Thin) findViewById(R.id.complaintRegisteredDate);
        registeredDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    }
}
