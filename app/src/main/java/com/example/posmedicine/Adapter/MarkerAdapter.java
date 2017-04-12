package com.example.posmedicine.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.models.Clinic;
import com.example.posmedicine.models.Schedule;
import com.example.posmedicine.models.response.ClinicsResponse;
import com.example.posmedicine.models.response.SchedulesResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Surya on 04/10/17.
 */

public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {
    private View v;
    private Activity activity;
    private GoogleMap mMap;
    private AlertDialog.Builder alertDialogBuilder;
    private ApiService service;
    private List<Schedule> listDoctor;

    public MarkerAdapter(GoogleMap mMap, Activity activity) {
        this.v = activity.getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        this.activity = activity;
        this.mMap = mMap;
        this.alertDialogBuilder = new AlertDialog.Builder(activity);
        this.service = RestClient.getInstance().getApiService();
        listDoctor = new ArrayList<>();
//        v = parent.inflate(R.layout.custom_info_contents, null);
//        myContentsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_info_contents, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = activity.getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        TextView tvTitle = ((TextView) v.findViewById(R.id.title));
        TextView tvSnippet = ((TextView) v.findViewById(R.id.snippet));

        tvTitle.setText("Clinic " + marker.getTitle());
        tvSnippet.setText(marker.getSnippet());
        service.getSchedules(Integer.parseInt(marker.getTag().toString())).enqueue(new Callback<SchedulesResponse>() {
            @Override
            public void onResponse(Call<SchedulesResponse> call, Response<SchedulesResponse> response) {
                listDoctor = response.body().getSchedule();
                final ArrayAdapter<Schedule> arrayAdapterItems =
                        new ArrayAdapter<Schedule>(activity, android.R.layout.simple_expandable_list_item_1, listDoctor);
                final List<Schedule> finalList = listDoctor;
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        alertDialogBuilder.setTitle("Doctor List");
                        alertDialogBuilder.setAdapter(arrayAdapterItems,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(activity.getApplicationContext(), finalList.get(which).getDoctorName()+ " "+finalList.get(which).getDoctorId(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });

            }

            @Override
            public void onFailure(Call<SchedulesResponse> call, Throwable t) {

            }
        });
//        listDoctor.add("Doctor A");
//        listDoctor.add("Doctor B");
//        listDoctor.add("Doctor C");

        return v;
    }
}
