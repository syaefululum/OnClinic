package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato_Thin;
import com.example.posmedicine.models.Patient;
import com.example.posmedicine.models.response.ComplaintHeaderResponse;
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
    private TextView_Lato_Thin registeredDate;
    private IconTextView submitButton;
    private EditText description;
    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_complaint);

        submitButton = (IconTextView) findViewById(R.id.complaintSubmit);
        description = (EditText) findViewById(R.id.complaintHeaderDescription);
        patients = (AutoCompleteTextView) findViewById(R.id.complaintPatient);
        selectedPatient = new Patient();
        registeredDate = (TextView_Lato_Thin) findViewById(R.id.complaintRegisteredDate);

        registeredDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        service = RestClient.getInstance().getApiService();
        getPatient();

        patients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPatient = (Patient) parent.getAdapter().getItem(position);
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.complaintPatient, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.patient_error);
        awesomeValidation.addValidation(this, R.id.complaintHeaderDescription, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.description_error);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createComplaint();
            }
        });
    }

    private void getPatient() {
        service.getPatients().enqueue(new Callback<PatientsResponse>() {
            @Override
            public void onResponse(Call<PatientsResponse> call, Response<PatientsResponse> response) {
                ArrayAdapter myAdapter = new ArrayAdapter<>(CreateComplaintActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getPatient());
                patients.setThreshold(2);
                patients.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<PatientsResponse> call, Throwable t) {

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
                                Toast.makeText(CreateComplaintActivity.this, "Data Created Successfully", Toast.LENGTH_LONG).show();
                                finish();
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
