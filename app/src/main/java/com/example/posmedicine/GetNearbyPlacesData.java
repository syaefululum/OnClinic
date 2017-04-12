package com.example.posmedicine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.Adapter.MarkerAdapter;
import com.example.posmedicine.models.Clinic;
import com.example.posmedicine.models.response.ClinicsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Surya_N2267 on 4/6/2017.
 */

public class GetNearbyPlacesData extends AsyncTask<Object, Object, Void> {
    private String googlePlacesData;
    private GoogleMap mMap;
    private Activity activity;
    private LatLng north, south;
    private List<LatLng> nearbyPlacesList;
    private List<Clinic> nearbyClinics;
    private ApiService service;
    private AlertDialog.Builder alertDialogBuilder;

    @Override
    protected Void doInBackground(Object... params) {
        service = RestClient.getInstance().getApiService();
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            activity = (Activity) params[1];
            north = (LatLng) params[2];
            south = (LatLng) params[3];
//            nearbyPlacesList = (List<LatLng>) params[1];
//            googlePlacesData = downloadUrl.readUrl(url);

            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
//        return googlePlacesData;
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");

        ShowNearbyPlaces();

        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces() {
        service.getClinics(north.latitude, north.longitude, south.latitude, south.longitude).enqueue(new Callback<ClinicsResponse>() {
            @Override
            public void onResponse(Call<ClinicsResponse> call, Response<ClinicsResponse> response) {
                nearbyClinics = new ArrayList<Clinic>();
                nearbyClinics = response.body().getClinic();
                alertDialogBuilder = new AlertDialog.Builder(activity);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();

                for (Clinic clinic : nearbyClinics) {
                    Log.d("onPostExecute", "Entered into showing locations");
                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng coordinate = new LatLng(Double.parseDouble(clinic.getLatitude()), Double.parseDouble(clinic.getLongitude()));
                    builder.include(coordinate);
                    markerOptions
                            .position(coordinate)
                            .title(clinic.getName())
                            .snippet(clinic.getAddress());
                    mMap.addMarker(markerOptions).setTag(clinic.getId());
//                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//                        @Override
//                        public View getInfoWindow(Marker marker) {
//                            return null;
//                        }
//
//                        @Override
//                        public View getInfoContents(Marker marker) {
//                            View v = activity.getLayoutInflater().inflate(R.layout.custom_info_contents, null);
//                            TextView tvTitle = ((TextView) v.findViewById(R.id.title));
//                            tvTitle.setText("Clinic "+marker.getTitle());
//                            TextView tvSnippet = ((TextView) v.findViewById(R.id.snippet));
//                            tvSnippet.setText(marker.getSnippet());
//                            final String title = marker.getTitle(), snipet = marker.getSnippet();
//                            List<String> listDoctor = new ArrayList<>();
//                            listDoctor.add("Doctor A");
//                            listDoctor.add("Doctor B");
//                            listDoctor.add("Doctor C");
//
//                            final ArrayAdapter<String> arrayAdapterItems =
//                                    new ArrayAdapter<>(activity,android.R.layout.simple_expandable_list_item_1, listDoctor);
//
//                            final List<String> finalList = listDoctor;
//                            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//                                @Override
//                                public void onInfoWindowClick(Marker marker) {
//                                    alertDialogBuilder.setTitle("Doctor List");
//                                    alertDialogBuilder.setAdapter(arrayAdapterItems,
//                                            new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    Toast.makeText(activity.getApplicationContext(), finalList.get(which), Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//                                    AlertDialog alertDialog = alertDialogBuilder.create();
//                                    alertDialog.show();
//
//                                }
//                            });
//                            return v;
//                        }
//                    });
                }

                mMap.setInfoWindowAdapter(new MarkerAdapter(mMap, activity));
            }

            @Override
            public void onFailure(Call<ClinicsResponse> call, Throwable t) {

            }
        });
    }
}
