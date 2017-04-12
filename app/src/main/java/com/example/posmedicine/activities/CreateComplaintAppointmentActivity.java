package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.posmedicine.R;
import com.example.posmedicine.models.Appointment;

public class CreateComplaintAppointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaint_appointment);

        Bundle extras = getIntent().getExtras();
        Appointment appointment = (Appointment) extras.getParcelable("appointment");

    }
}
