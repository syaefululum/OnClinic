package com.example.posmedicine.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.posmedicine.Adapter.ComplaintHeaderAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.models.response.ComplaintHeaderResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintHeaderActivity extends AppCompatActivity {
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_header);

        service = RestClient.getInstance().getApiService();
        getComplaintHeader();
    }

    public void getComplaintHeader() {
        service.getComplaints().enqueue(new Callback<ComplaintHeaderResponse>() {
            @Override
            public void onResponse(Call<ComplaintHeaderResponse> call, Response<ComplaintHeaderResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(ComplaintHeaderActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                ComplaintHeaderAdapter complaintHeader = new ComplaintHeaderAdapter(response.body().getComplaintHeader());
                RecyclerView rvHeaderComplaint = (RecyclerView)findViewById(R.id.rvHeaderComplaint);

                rvHeaderComplaint.setLayoutManager(llm);
                rvHeaderComplaint.setAdapter(complaintHeader);
            }

            @Override
            public void onFailure(Call<ComplaintHeaderResponse> call, Throwable t) {

            }
        });
    }

}
