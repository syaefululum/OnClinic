package com.example.posmedicine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.posmedicine.Adapter.UnitAdapter;
import com.example.posmedicine.interfaces.UnitActions;
import com.example.posmedicine.models.response.UnitResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UnitActivity extends AppCompatActivity implements UnitActions, Parcelable {

    ApiService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createUnit = new Intent(UnitActivity.this,CreateUnitActivity.class);
                UnitActivity.this.startActivity(createUnit);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
    }

    public void getUnit() {
        service.getUnits().enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(UnitActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                UnitAdapter unitAdapter = new UnitAdapter(response.body().getUnit(), UnitActivity.this);
                RecyclerView rvUnit = (RecyclerView) findViewById(R.id.rvUnit);
                rvUnit.setLayoutManager(llm);
                rvUnit.setAdapter(unitAdapter);
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void addUnit() {
        service = RestClient.getInstance().getApiService();
        getUnit();
    }

    @Override
    public void editUnit() {
        service = RestClient.getInstance().getApiService();
        getUnit();
    }

    @Override
    public void deleteUnit() {
        service = RestClient.getInstance().getApiService();
        getUnit();;
//        service = RestClient.getInstance().getApiService();
//        getUnit();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        service = RestClient.getInstance().getApiService();
        getUnit();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public UnitActivity() {
    }

    protected UnitActivity(Parcel in) {
    }

    public static final Parcelable.Creator<UnitActivity> CREATOR = new Parcelable.Creator<UnitActivity>() {
        @Override
        public UnitActivity createFromParcel(Parcel source) {
            return new UnitActivity(source);
        }

        @Override
        public UnitActivity[] newArray(int size) {
            return new UnitActivity[size];
        }
    };
}
