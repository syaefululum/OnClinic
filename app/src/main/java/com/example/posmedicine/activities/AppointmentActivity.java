package com.example.posmedicine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.posmedicine.Adapter.AppointmentAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato;
import com.example.posmedicine.models.response.AppointmentsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentActivity extends BaseActivity {

    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        getSupportActionBar().setTitle("Appointment");
        TextView_Lato subtitle = (TextView_Lato) findViewById(R.id.labelSubTitle);
        subtitle.setText("Appointment List");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(
                new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_plus_circle)
                        .color(Color.WHITE)
                        .actionBarSize());
        if(Prefs.getString("USERROLE","Not Set").equals("Patient")){
            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.GONE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent v = new Intent(AppointmentActivity.this,AppointmentDetailActivity.class);
//                AppointmentActivity.this.startActivity(v);
                Intent createAppointment = new Intent(AppointmentActivity.this,CreateAppointmentActivity.class);
                AppointmentActivity.this.startActivity(createAppointment);
            }
        });

        service = RestClient.getInstance().getApiService();
        getAppointment();
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.activity_appointment), iconFont);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        getAppointment();
    }

    public void getAppointment(){
        Log.d("USERID",Prefs.getString("USERID","1"));
        if(Prefs.getString("USERROLE","Not Set").equals("Patient")){
            service.getAppointmentbyPatient(Integer.parseInt(Prefs.getString("USERID","1"))).enqueue(new Callback<AppointmentsResponse>() {
                @Override
                public void onResponse(Call<AppointmentsResponse> call, Response<AppointmentsResponse> response) {
                    LinearLayoutManager llm = new LinearLayoutManager(AppointmentActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);

                    AppointmentAdapter appointmentAdapter = new AppointmentAdapter(response.body().getAppointment(), AppointmentActivity.this);
                    RecyclerView rvAppointment = (RecyclerView)findViewById(R.id.rvAppointment);
                    rvAppointment.setLayoutManager(llm);
                    rvAppointment.setAdapter(appointmentAdapter);
                }

                @Override
                public void onFailure(Call<AppointmentsResponse> call, Throwable t) {

                }
            });
        }else if(Prefs.getString("USERROLE","Not Set").equals("Doctor")){
            service.getAppointmentbyDoctor(Integer.parseInt(Prefs.getString("USERID","1"))).enqueue(new Callback<AppointmentsResponse>() {
                @Override
                public void onResponse(Call<AppointmentsResponse> call, Response<AppointmentsResponse> response) {
                    LinearLayoutManager llm = new LinearLayoutManager(AppointmentActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);

                    AppointmentAdapter appointmentAdapter = new AppointmentAdapter(response.body().getAppointment(), AppointmentActivity.this);
                    RecyclerView rvAppointment = (RecyclerView)findViewById(R.id.rvAppointment);
                    rvAppointment.setLayoutManager(llm);
                    rvAppointment.setAdapter(appointmentAdapter);
                }

                @Override
                public void onFailure(Call<AppointmentsResponse> call, Throwable t) {

                }
            });
        }else if(Prefs.getString("USERROLE","Not Set").equals("Nurse")){
            service.getAppointmentByNurse().enqueue(new Callback<AppointmentsResponse>() {
                @Override
                public void onResponse(Call<AppointmentsResponse> call, Response<AppointmentsResponse> response) {
                    LinearLayoutManager llm = new LinearLayoutManager(AppointmentActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);

                    AppointmentAdapter appointmentAdapter = new AppointmentAdapter(response.body().getAppointment(), AppointmentActivity.this);
                    RecyclerView rvAppointment = (RecyclerView)findViewById(R.id.rvAppointment);
                    rvAppointment.setLayoutManager(llm);
                    rvAppointment.setAdapter(appointmentAdapter);
                }

                @Override
                public void onFailure(Call<AppointmentsResponse> call, Throwable t) {

                }
            });
        }

    }

}
