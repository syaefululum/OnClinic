package com.example.posmedicine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.posmedicine.Adapter.ComplaintHeaderAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato;
import com.example.posmedicine.models.response.ComplaintHeadersResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintHeaderActivity extends BaseActivity {
    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_header);

        getSupportActionBar().setTitle("Complaint");
        TextView_Lato subtitle = (TextView_Lato) findViewById(R.id.labelSubTitle);
        subtitle.setText("Today Patient Complaint List");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(
                new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_plus_circle)
                        .color(Color.WHITE)
                        .actionBarSize());
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplaintHeaderActivity.this, CreateComplaintActivity.class));
            }
        });

        service = RestClient.getInstance().getApiService();
        getComplaintHeader();
    }

    @Override
    public void onResume() {
        super.onResume();
        getComplaintHeader();
    }

    public void getComplaintHeader() {
        service.getComplaints().enqueue(new Callback<ComplaintHeadersResponse>() {
            @Override
            public void onResponse(Call<ComplaintHeadersResponse> call, Response<ComplaintHeadersResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(ComplaintHeaderActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                ComplaintHeaderAdapter complaintHeader = new ComplaintHeaderAdapter(response.body().getComplaintHeader());
                RecyclerView rvHeaderComplaint = (RecyclerView) findViewById(R.id.rvHeaderComplaint);

                rvHeaderComplaint.setLayoutManager(llm);
                rvHeaderComplaint.setAdapter(complaintHeader);
            }

            @Override
            public void onFailure(Call<ComplaintHeadersResponse> call, Throwable t) {

            }
        });
    }

}
