package com.example.posmedicine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.posmedicine.Adapter.ComplaintDetailAdapter;
import com.example.posmedicine.Adapter.TreatmentAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato;
import com.example.posmedicine.models.response.ComplaintDetailsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TreatmentActivity extends BaseActivity {
    private ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);

        service = RestClient.getInstance().getApiService();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getComplaintDetails();
        }

        getSupportActionBar().setTitle("Treatment");
        TextView_Lato subtitle = (TextView_Lato) findViewById(R.id.labelSubTitle);
        subtitle.setText("Today Patient List");
    }

    private void getComplaintDetails() {
        service.getTreatments(Integer.parseInt(Prefs.getString("USERID","1"))).enqueue(new Callback<ComplaintDetailsResponse>() {
            @Override
            public void onResponse(Call<ComplaintDetailsResponse> call, Response<ComplaintDetailsResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(TreatmentActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                TreatmentAdapter treatmentAdapter = new TreatmentAdapter(response.body().getComplaintDetail());
                RecyclerView rvTreatment = (RecyclerView) findViewById(R.id.rvTreatment);

                rvTreatment.setLayoutManager(llm);
                rvTreatment.setAdapter(treatmentAdapter);
            }

            @Override
            public void onFailure(Call<ComplaintDetailsResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getComplaintDetails();
    }

}