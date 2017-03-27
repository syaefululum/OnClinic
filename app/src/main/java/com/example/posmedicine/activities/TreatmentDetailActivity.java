package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.example.posmedicine.models.ComplaintDetail;

public class TreatmentDetailActivity extends AppCompatActivity {
    private ComplaintDetail treatment;
    TextView doctor,patient,serviceName,description,result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_detail);
        treatment = (ComplaintDetail) getIntent().getExtras().getSerializable("TREATMENT");
        doctor = (TextView) findViewById(R.id.viewTreatmentDoctorName);
        patient = (TextView) findViewById(R.id.viewTreatmentPatientName);
        serviceName = (TextView) findViewById(R.id.viewTreatmentServiceName);
        description = (TextView) findViewById(R.id.viewTreatmentDescription);
        result = (TextView) findViewById(R.id.viewTreatmentResult);

        doctor.setText(treatment.getDoctorName());
        patient.setText(treatment.getPatientName());
        serviceName.setText(treatment.getServiceName());
        description.setText(treatment.getDescription());
        result.setText(treatment.getResult());
    }
}
