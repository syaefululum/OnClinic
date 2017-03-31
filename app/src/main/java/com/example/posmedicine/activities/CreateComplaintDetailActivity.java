package com.example.posmedicine.activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.models.Doctor;
import com.example.posmedicine.models.Service;
import com.example.posmedicine.models.response.ComplaintHeaderResponse;
import com.example.posmedicine.models.response.DoctorsResponse;
import com.example.posmedicine.models.response.ServicesResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.joanzapata.iconify.widget.IconTextView;

public class CreateComplaintDetailActivity extends AppCompatActivity {
    private ApiService service;
    private AutoCompleteTextView services;
    private AutoCompleteTextView doctors;
    private EditText time;
    private AwesomeValidation awesomeValidation;
    private IconTextView submitButton;
    private Doctor selectedDoctor;
    private Service selectedService;
    private Integer header_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaint_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.header_id = Integer.parseInt(getIntent().getStringExtra("HEADER_ID"));
        }

        submitButton = (IconTextView) findViewById(R.id.complaintSubmit);
        services = (AutoCompleteTextView) findViewById(R.id.complaintService);
        doctors = (AutoCompleteTextView) findViewById(R.id.complaintDoctor);
        time = (EditText) findViewById(R.id.complaintTime);
        selectedDoctor = new Doctor();
        selectedService = new Service();

        time.setText(new SimpleDateFormat("HH:mm").format(new Date()));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        service = RestClient.getInstance().getApiService();
        getDoctor();
        getService();

        doctors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDoctor = (Doctor) parent.getAdapter().getItem(position);
            }
        });
        services.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedService = (Service) parent.getAdapter().getItem(position);
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.complaintDoctor, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.doctor_error);
        awesomeValidation.addValidation(this, R.id.complaintService, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.service_error);
        awesomeValidation.addValidation(this, R.id.complaintTime, "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$", R.string.time_error);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createComplaint();
            }
        });
    }

    private void getDoctor() {
        service.getDoctors().enqueue(new Callback<DoctorsResponse>() {
            @Override
            public void onResponse(Call<DoctorsResponse> call, Response<DoctorsResponse> response) {
                ArrayAdapter myAdapter = new ArrayAdapter<Doctor>(CreateComplaintDetailActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getDoctor());
                doctors.setThreshold(2);
                doctors.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<DoctorsResponse> call, Throwable t) {

            }
        });
    }

    private void getService() {
        service.getServices().enqueue(new Callback<ServicesResponse>() {
            @Override
            public void onResponse(Call<ServicesResponse> call, Response<ServicesResponse> response) {
                ArrayAdapter myAdapter = new ArrayAdapter<Service>(CreateComplaintDetailActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getService());
                services.setThreshold(2);
                services.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<ServicesResponse> call, Throwable t) {

            }
        });
    }

    protected void createComplaint() {
        if (awesomeValidation.validate()) {
            service.postComplaintDetail(header_id, selectedDoctor.getId(), selectedService.getId(), time.getText().toString())
                    .enqueue(new Callback<ComplaintHeaderResponse>() {
                        @Override
                        public void onResponse(Call<ComplaintHeaderResponse> call, Response<ComplaintHeaderResponse> response) {
//                Appointment appData = response.body().getAppointment();
//                appData.save();
                            if (response.body().getStatus()) {
                                Toast.makeText(CreateComplaintDetailActivity.this, "Data Created Successfully", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(CreateComplaintDetailActivity.this, "Data Failed to save : 404", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ComplaintHeaderResponse> call, Throwable t) {
                            Log.d("LOG", "Failed");
                        }
                    });
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            EditText selectedTime = (EditText) getActivity().findViewById(R.id.complaintTime);
            selectedTime.setText(hourOfDay + ":" + minute);
        }
    }

}
