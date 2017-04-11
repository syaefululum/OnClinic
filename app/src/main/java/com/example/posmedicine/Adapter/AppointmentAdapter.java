package com.example.posmedicine.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.activities.AppointmentActivity;
import com.example.posmedicine.activities.CreateComplaintActivity;
import com.example.posmedicine.activities.CreateComplaintAppointmentActivity;
import com.example.posmedicine.activities.FontManager;
import com.example.posmedicine.R;
import com.example.posmedicine.models.Appointment;
import com.example.posmedicine.models.Doctor;
import com.example.posmedicine.models.response.AppointmentResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by Surya_N2267 on 2/6/2017.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointment;
    private List<Doctor> doctor;
    private AppointmentActivity activity;
    String role = Prefs.getString("USERROLE", "Not Set");
    ApiService service = RestClient.getInstance().getApiService();
    public AppointmentAdapter(List<Appointment> appointment, AppointmentActivity activity) {
        this.appointment = appointment;
        this.activity = activity;
    }

    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_appointment, parent, false);

        return new AppointmentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AppointmentAdapter.ViewHolder holder, final int position) {
        holder.appointmentDate.setText(appointment.get(position).getDate());
        if(role.equals("Patient")){
            holder.appointmentName.setText(appointment.get(position).getDoctorName());
            holder.textName.setText("Doctor");
            if (appointment.get(position).getStatus().equals("Pending")) {
                holder.appointmentUpdateReject.setVisibility(View.VISIBLE);
                holder.appointmentUpdateAccept.setVisibility(View.GONE);
            } else{
                holder.actionContainer.setVisibility(View.GONE);
            }
        }else if(role.equals("Doctor")){
            holder.appointmentName.setText(appointment.get(position).getPatientName());
            holder.textName.setText("Patient");
            if (appointment.get(position).getStatus().equals("Pending")) {
                holder.appointmentUpdateReject.setVisibility(View.VISIBLE);
                holder.appointmentUpdateAccept.setVisibility(View.VISIBLE);
            } else{
                holder.actionContainer.setVisibility(View.GONE);
            }
            // status pending
        }else if(role.equals("Nurse")) {
            holder.appointmentName.setText(appointment.get(position).getPatientName());
            holder.textName.setText("Patient");
            holder.actionContainer.setVisibility(View.GONE);
        }else{
            holder.appointmentName.setText("Null");
            holder.textName.setText("Name");
        }

        holder.appointmentStatus.setText(appointment.get(position).getStatus());

        if (holder.appointmentStatus.getText().equals("Approved")) {
            holder.appointmentStatus.setTextColor(Color.GREEN);
        } else if (holder.appointmentStatus.getText().equals("Rejected")) {
            holder.appointmentStatus.setTextColor(Color.RED);
        } else if(holder.appointmentStatus.getText().equals("Pending")) {
            holder.appointmentStatus.setTextColor(Color.BLUE);
        }

        holder.appointmentUpdateAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Element " + position + " clicked.");
                service.updateAppointmentStatus(appointment.get(position).getId(),"Approved").enqueue(new Callback<AppointmentResponse>() {
                    @Override
                    public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                        if(response.body().getStatus()){
                            Toast toast = Toast.makeText(activity.getApplicationContext(), "Succesfully approved appointment", Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            Toast toast = Toast.makeText(activity.getApplicationContext(), "Failed approved appointment", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        activity.onResume();
                    }

                    @Override
                    public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Failed approved appointment, please try again", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        });

        holder.appointmentUpdateReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statusUser = "";
                if(role.equals("Patient")){
                    statusUser = "Canceled";
                }else if(role.equals("Doctor")){
                    statusUser = "Rejected";
                }
                final String finalStatusUser = statusUser;
                service.updateAppointmentStatus(appointment.get(position).getId(),statusUser).enqueue(new Callback<AppointmentResponse>() {
                    @Override
                    public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                        if(response.body().getStatus()){
                            Toast toast = Toast.makeText(activity.getApplicationContext(), "Succesfully "+ finalStatusUser +" appointment", Toast.LENGTH_SHORT);
                            toast.show();
                        }else{
                            Toast toast = Toast.makeText(activity.getApplicationContext(), "Failed "+ finalStatusUser +" appointment", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        activity.onResume();
                    }

                    @Override
                    public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Failed "+finalStatusUser+" appointment, please try again", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
            }
        });
        if(role.equals("Nurse")){
            holder.cvAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle extras = new Bundle();
                    extras.putParcelable("appointment", (Parcelable) appointment.get(position));
//                    Intent createComplaintAppointment = new Intent(v.getContext(), CreateComplaintAppointmentActivity.class);
//                    createComplaintAppointment.putExtras(extras);
//                    v.getContext().startActivity(createComplaintAppointment);

                    Intent createComplaint = new Intent(v.getContext(), CreateComplaintActivity.class);
                    createComplaint.putExtras(extras);
                    v.getContext().startActivity(createComplaint);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return appointment.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvAppointment;
        public TextView appointmentDate;
        public TextView textName;
        public TextView appointmentName;
        public TextView appointmentStatus;
        public TextView appointmentUpdateAccept;
        public TextView appointmentUpdateReject;
        public LinearLayout actionContainer;

        public ViewHolder(View v) {
            super(v);
            Typeface iconFont = FontManager.getTypeface(v.getContext(), FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(v.findViewById(R.id.categoryAppointment), iconFont);

            cvAppointment = (CardView) v.findViewById(R.id.categoryAppointment);
            textName = (TextView)v.findViewById(R.id.name_title);

            appointmentDate = (TextView) v.findViewById(R.id.appointment_date);
            appointmentName = (TextView)v.findViewById(R.id.appointment_name);
            appointmentStatus = (TextView) v.findViewById(R.id.appointment_status);

            appointmentUpdateAccept = (TextView) v.findViewById(R.id.bAcceptAppointment);
            appointmentUpdateReject = (TextView) v.findViewById(R.id.bRejectAppointment);

            actionContainer = (LinearLayout) v.findViewById(R.id.appointmentActions);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}
