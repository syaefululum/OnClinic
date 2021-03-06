package com.example.posmedicine.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.posmedicine.Adapter.AppointmentAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.models.response.AppointmentsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentDoctorActivity extends AppCompatActivity {

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

                Intent createAppointment = new Intent(AppointmentDoctorActivity.this,CreateAppointmentActivity.class);
                AppointmentDoctorActivity.this.startActivity(createAppointment);
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
        service.getAppointmentbyDoctor(Integer.parseInt(Prefs.getString("USERID","1"))).enqueue(new Callback<AppointmentsResponse>() {
            @Override
            public void onResponse(Call<AppointmentsResponse> call, Response<AppointmentsResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(AppointmentDoctorActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);

               // AppointmentAdapter appointmentAdapter = new AppointmentAdapter(response.body().getAppointment(), AppointmentActivity.class);
                RecyclerView rvAppointment = (RecyclerView)findViewById(R.id.rvAppointment);
                rvAppointment.setLayoutManager(llm);
               // rvAppointment.setAdapter(appointmentAdapter);
        }

            @Override
            public void onFailure(Call<AppointmentsResponse> call, Throwable t) {

            }
        });
    }

}
