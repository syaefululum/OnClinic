package com.example.posmedicine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.posmedicine.Adapter.MedicineAdapter;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineActivity extends AppCompatActivity implements Parcelable {

    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent createMedicine = new Intent(MedicineActivity.this,CreateMedicineActivity.class);
                MedicineActivity.this.startActivity(createMedicine);
            }
        });

        service = RestClient.getInstance().getApiService();
        getMedicine();
    }

    public void getMedicine(){
       service.getMedicine().enqueue(new Callback<MedicineResponse>() {
           @Override
           public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
               LinearLayoutManager llm = new LinearLayoutManager(MedicineActivity.this);
               llm.setOrientation(LinearLayoutManager.VERTICAL);

               MedicineAdapter medicineAdapter = new MedicineAdapter(response.body().getMedicine(),MedicineActivity.this);
               Log.d("mamam", String.valueOf(response.body().getMedicine()));
               RecyclerView rvMedicine = (RecyclerView)findViewById(R.id.rvMedicine);
               rvMedicine.setLayoutManager(llm);
               rvMedicine.setAdapter(medicineAdapter);
           }

           @Override
           public void onFailure(Call<MedicineResponse> call, Throwable t) {

           }
       });
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        service = RestClient.getInstance().getApiService();
        getMedicine();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public MedicineActivity() {
    }

    protected MedicineActivity(Parcel in) {
    }

    public static final Parcelable.Creator<MedicineActivity> CREATOR = new Parcelable.Creator<MedicineActivity>() {
        @Override
        public MedicineActivity createFromParcel(Parcel source) {
            return new MedicineActivity(source);
        }

        @Override
        public MedicineActivity[] newArray(int size) {
            return new MedicineActivity[size];
        }
    };
}
