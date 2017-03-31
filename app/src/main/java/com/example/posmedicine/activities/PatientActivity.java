package com.example.posmedicine.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.Adapter.ComplaintHeaderAdapter;
import com.example.posmedicine.Adapter.MedicalRecordAdapter;
import com.example.posmedicine.MedicalRecordFragment;
import com.example.posmedicine.R;
import com.example.posmedicine.models.ComplaintHeader;
import com.example.posmedicine.models.Patient;
import com.example.posmedicine.models.response.ComplaintHeadersResponse;
import com.example.posmedicine.models.response.PatientResponse;
import com.example.posmedicine.models.response.PatientsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private List<ComplaintHeader> complaintHeader;
    private Patient patient;
    private TextView name, address, total, last;
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        service = RestClient.getInstance().getApiService();
        mPager = (ViewPager) findViewById(R.id.pagerPatient);
        name = (TextView) findViewById(R.id.tPatient_Name);
        address = (TextView) findViewById(R.id.tPatient_Address);
        total = (TextView) findViewById(R.id.tPatient_TotalTreatment);
        last = (TextView) findViewById(R.id.tPatient_LastDate);

        this.getPatient(Integer.parseInt(getIntent().getStringExtra("PATIENT_ID")));
    }

    protected void getPatient(Integer id){
        service.getPatient(id).enqueue(new Callback<PatientResponse>() {
            @Override
            public void onResponse(Call<PatientResponse> call, Response<PatientResponse> response) {
                patient = response.body().getPatient();
                name.setText(patient.getPersonName());
                address.setText(patient.getPersonAddress());
                getHeader();
            }

            @Override
            public void onFailure(Call<PatientResponse> call, Throwable t) {

            }
        });
    }

    protected void getHeader() {
        service.getFindByPatient(patient.getId()).enqueue(new Callback<ComplaintHeadersResponse>() {
            @Override
            public void onResponse(Call<ComplaintHeadersResponse> call, Response<ComplaintHeadersResponse> response) {
                complaintHeader = response.body().getComplaintHeader();
                total.setText(complaintHeader.size()+"");
                last.setText(complaintHeader.get(0).getRegisteredDate());
                mPagerAdapter = new MedicalRecordAdapter(getSupportFragmentManager(), complaintHeader);
                mPager.setAdapter(mPagerAdapter);
            }

            @Override
            public void onFailure(Call<ComplaintHeadersResponse> call, Throwable t) {

            }
        });
    }
}
