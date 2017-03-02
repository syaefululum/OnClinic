package com.example.posmedicine;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.posmedicine.Adapter.AppointmentAdapter;
import com.example.posmedicine.Adapter.local.LocalAppointmentAdapter;
import com.example.posmedicine.models.Appointment;
import com.example.posmedicine.models.local.LocalAppointment;
import com.example.posmedicine.models.response.AppointmentResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends AppCompatActivity {

    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent v = new Intent(AppointmentActivity.this,AppointmentDetailActivity.class);
//                AppointmentActivity.this.startActivity(v);

                Intent createAppointment = new Intent(AppointmentActivity.this,CreateAppointmentActivity.class);
                AppointmentActivity.this.startActivity(createAppointment);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        service = RestClient.getInstance().getApiService();
       // getLocalAppointment();
        getAppointment();
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.activity_appointment), iconFont);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        getLocalAppointment();
    }

    public void getLocalAppointment(){
//        List<Appointment> appointments = Appointment.listAll(Appointment.class);
//        if(appointments.size() != 0){
//            LinearLayoutManager llm = new LinearLayoutManager(AppointmentActivity.this);
//            llm.setOrientation(LinearLayoutManager.VERTICAL);
//
//            AppointmentAdapter appointmentAdapter = new AppointmentAdapter(appointments,AppointmentActivity.this);
//            RecyclerView rvAppointment = (RecyclerView)findViewById(R.id.rvAppointment);
//            rvAppointment.setLayoutManager(llm);
//            rvAppointment.setAdapter(appointmentAdapter);
//        }

//        List<LocalAppointment> listAppointment = LocalAppointment.listAll(LocalAppointment.class);
//        if(listAppointment != null){
//            Log.d("data", String.valueOf(listAppointment));
//            LinearLayoutManager llm = new LinearLayoutManager(AppointmentActivity.this);
//            llm.setOrientation(LinearLayoutManager.VERTICAL);
//
//            LocalAppointmentAdapter appointmentAdapter = new LocalAppointmentAdapter(listAppointment,AppointmentActivity.this);
//            RecyclerView rvAppointment = (RecyclerView)findViewById(R.id.rvAppointment);
//            rvAppointment.setLayoutManager(llm);
//            rvAppointment.setAdapter(appointmentAdapter);
//        }

    }

    public void getAppointment(){
        service.getAppointmentbyDoctor((long) 1).enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                Log.d("dasd", String.valueOf(response.body().getAppointment()));
                LinearLayoutManager llm = new LinearLayoutManager(AppointmentActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                AppointmentAdapter appointmentAdapter = new AppointmentAdapter(response.body().getAppointment(),AppointmentActivity.this);
                RecyclerView rvAppointment = (RecyclerView)findViewById(R.id.rvAppointment);
                rvAppointment.setLayoutManager(llm);
                rvAppointment.setAdapter(appointmentAdapter);
        }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {

            }
        });
    }

}
