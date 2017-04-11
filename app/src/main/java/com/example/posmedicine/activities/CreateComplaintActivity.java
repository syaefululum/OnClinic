package com.example.posmedicine.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato_Thin;
import com.example.posmedicine.models.Appointment;
import com.example.posmedicine.models.Patient;
import com.example.posmedicine.models.response.ComplaintHeaderResponse;
import com.example.posmedicine.models.response.PatientResponse;
import com.example.posmedicine.models.response.PatientsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.joanzapata.iconify.widget.IconTextView;

public class CreateComplaintActivity extends AppCompatActivity {
    private ApiService service;
    private AutoCompleteTextView patients;
    private Patient selectedPatient;
    private TextView_Lato_Thin registeredDate, docterName;
    private Button submitButton;
    private EditText description;
    private AwesomeValidation awesomeValidation;
    private TextView bCancel;
    private Appointment appointment;
    private LinearLayout doctorContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaint);

        service = RestClient.getInstance().getApiService();
        selectedPatient = new Patient();

        bCancel = (TextView)findViewById(R.id.bCancel);
        submitButton = (Button) findViewById(R.id.complaintSubmit);
        description = (EditText) findViewById(R.id.complaintHeaderDescription);
        patients = (AutoCompleteTextView) findViewById(R.id.complaintPatient);
        registeredDate = (TextView_Lato_Thin) findViewById(R.id.complaintRegisteredDate);
        registeredDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        doctorContainer = (LinearLayout)findViewById(R.id.doctorContainer);
        docterName = (TextView_Lato_Thin)findViewById(R.id.doctorName);

        appointment = new Appointment();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            appointment = (Appointment) extras.getParcelable("appointment");
            getPatient(appointment.getPatientId());
            docterName.setText(appointment.getDoctorName());
        }else{
            doctorContainer.setVisibility(View.GONE);
            getPatient();
            patients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedPatient = (Patient) parent.getAdapter().getItem(position);
                }
            });
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.complaintPatient, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.patient_error);
        awesomeValidation.addValidation(this, R.id.complaintHeaderDescription, "^[a-zA-Z][a-zA-Z0-9!@#$&()\\\\:-`.+,/\n \\\"]*$", R.string.description_error);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createComplaint();
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getPatient() {
        service.getPatients().enqueue(new Callback<PatientsResponse>() {
            @Override
            public void onResponse(Call<PatientsResponse> call, Response<PatientsResponse> response) {
                ArrayAdapter myAdapter = new ArrayAdapter<Patient>(CreateComplaintActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getPatient());
                patients.setThreshold(2);
                patients.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<PatientsResponse> call, Throwable t) {

            }
        });
    }

    private void getPatient(Integer patientId){
        selectedPatient = new Patient();
        service.getPatient(patientId).enqueue(new Callback<PatientResponse>() {
            @Override
            public void onResponse(Call<PatientResponse> call, Response<PatientResponse> response) {
                if(response.body().getStatus()){
                    selectedPatient = response.body().getPatient();
                    if(selectedPatient == null){
                        getPatient();
                        patients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                selectedPatient = (Patient) parent.getAdapter().getItem(position);
                            }
                        });
                        doctorContainer.setVisibility(View.GONE);
                    }else{
                        patients.setText(selectedPatient.getPersonName());
                        patients.setFocusable(false);
                        patients.setClickable(false);

                        doctorContainer.setVisibility(View.VISIBLE);

                    }
                }
            }

            @Override
            public void onFailure(Call<PatientResponse> call, Throwable t) {

            }
        });
    }

    protected void createComplaint() {
        if (awesomeValidation.validate()) {
            service.postComplaint(registeredDate.getText().toString(), selectedPatient.getId(), description.getText().toString())
                    .enqueue(new Callback<ComplaintHeaderResponse>() {
                        @Override
                        public void onResponse(Call<ComplaintHeaderResponse> call, Response<ComplaintHeaderResponse> response) {
                            if (response.body().getStatus()) {
                                if(appointment != null){
                                    String time = new SimpleDateFormat("HH:mm").format(new Date());
                                    service.postComplaintDetail(response.body().getComplaintHeader().getId(),appointment.getDoctorId(), 1, time).enqueue(new Callback<ComplaintHeaderResponse>() {
                                        @Override
                                        public void onResponse(Call<ComplaintHeaderResponse> call, Response<ComplaintHeaderResponse> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<ComplaintHeaderResponse> call, Throwable t) {

                                        }
                                    });
                                }
                                Toast.makeText(CreateComplaintActivity.this, "Data Created Successfully", Toast.LENGTH_LONG).show();
                                finish();
                                Intent listAppointment = new Intent(CreateComplaintActivity.this,ComplaintHeaderActivity.class);
                                listAppointment.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                CreateComplaintActivity.this.startActivity(listAppointment);
                            } else {
                                Toast.makeText(CreateComplaintActivity.this, "Data Failed to save : 404", Toast.LENGTH_LONG).show();
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

}
