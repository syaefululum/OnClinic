package com.example.posmedicine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.posmedicine.Adapter.ComplaintDetailAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato;
import com.example.posmedicine.models.response.ComplaintDetailsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintDetailActivity extends BaseActivity {

    private ApiService service;
    private Integer id;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_detail);

        service = RestClient.getInstance().getApiService();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.id = Integer.parseInt(getIntent().getStringExtra("EXTRA_ID"));
            this.name = getIntent().getStringExtra("EXTRA_NAME");
            getComplaintDetails(id);
        }

        getSupportActionBar().setTitle("Treatment");
        TextView_Lato subtitle = (TextView_Lato) findViewById(R.id.labelSubTitle);
        subtitle.setText("Treatment for "+name);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(
                new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_plus_circle)
                        .color(Color.WHITE)
                        .actionBarSize());
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent createComplaint = new Intent(v.getContext(), CreateComplaintDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString("HEADER_ID", String.valueOf(id));
                createComplaint.putExtras(extras);
                v.getContext().startActivity(createComplaint);
            }
        });
    }

    private void getComplaintDetails(Integer id) {
        service.getDetailbyId(id).enqueue(new Callback<ComplaintDetailsResponse>() {
            @Override
            public void onResponse(Call<ComplaintDetailsResponse> call, Response<ComplaintDetailsResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(ComplaintDetailActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                ComplaintDetailAdapter complaintHeader = new ComplaintDetailAdapter(response.body().getComplaintDetail());
                RecyclerView rvDetailComplaint = (RecyclerView) findViewById(R.id.rvDetailComplaint);

                rvDetailComplaint.setLayoutManager(llm);
                rvDetailComplaint.setAdapter(complaintHeader);
            }

            @Override
            public void onFailure(Call<ComplaintDetailsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getComplaintDetails(id);
    }

}
