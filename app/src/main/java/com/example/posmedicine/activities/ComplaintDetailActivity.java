package com.example.posmedicine.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.posmedicine.Adapter.ComplaintDetailAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.models.response.ComplaintDetailResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintDetailActivity extends AppCompatActivity {

    private ApiService service;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        service = RestClient.getInstance().getApiService();
        this.id = Integer.parseInt(getIntent().getStringExtra("EXTRA_ID"));
        getComplaintDetails(id);
    }

    private void getComplaintDetails(Integer id) {
        service.getDetailbyId((long)id).enqueue(new Callback<ComplaintDetailResponse>() {
            @Override
            public void onResponse(Call<ComplaintDetailResponse> call, Response<ComplaintDetailResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(ComplaintDetailActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                ComplaintDetailAdapter complaintHeader = new ComplaintDetailAdapter(response.body().getComplaintDetail());
                RecyclerView rvDetailComplaint = (RecyclerView)findViewById(R.id.rvDetailComplaint);

                rvDetailComplaint.setLayoutManager(llm);
                rvDetailComplaint.setAdapter(complaintHeader);
            }

            @Override
            public void onFailure(Call<ComplaintDetailResponse> call, Throwable t) {

            }
        });
    }

}
