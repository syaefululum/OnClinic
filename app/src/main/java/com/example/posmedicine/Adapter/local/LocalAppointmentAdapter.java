package com.example.posmedicine.Adapter.local;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.AppointmentActivity;
import com.example.posmedicine.FontManager;
import com.example.posmedicine.R;
import com.example.posmedicine.models.Appointment;
import com.example.posmedicine.models.Doctor;
import com.example.posmedicine.models.local.LocalAppointment;

import java.util.List;

/**
 * Created by Syaeful_U1438 on 28-Feb-17.
 */

public class LocalAppointmentAdapter extends RecyclerView.Adapter<LocalAppointmentAdapter.ViewHolder> {
    private List<LocalAppointment> appointment;
    private AppointmentActivity activity;

    public LocalAppointmentAdapter(List<LocalAppointment> appointment, AppointmentActivity activity) {
        this.appointment = appointment;
        this.activity = activity;
    }

    @Override
    public LocalAppointmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_appointment, parent, false);

        return new LocalAppointmentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LocalAppointmentAdapter.ViewHolder holder, final int position) {
        holder.appointmentDate.setText(appointment.get(position).getDate());
        holder.appointmentDoctor.setText(appointment.get(position).getDoctorName());
        holder.appointmentPatient.setText(appointment.get(position).getPatientName());
        holder.appointmentStatus.setText(appointment.get(position).getStatus());

        if (holder.appointmentStatus.getText().toString().equals("Canceled")) {
            holder.appointmentUpdateReject.setVisibility(View.GONE);
            holder.appointmentUpdateAccept.setVisibility(View.GONE);
        } else if (holder.appointmentStatus.getText().equals("Approved")) {
            holder.appointmentUpdateAccept.setVisibility(View.GONE);
            holder.appointmentStatus.setTextColor(Color.GREEN);
        } else if (holder.appointmentStatus.getText().equals("Rejected")) {
            holder.appointmentUpdateReject.setVisibility(View.GONE);
            holder.appointmentStatus.setTextColor(Color.RED);
        } else {
            holder.appointmentStatus.setTextColor(Color.BLUE);
        }
        holder.appointmentUpdateAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(activity.getApplicationContext(), "Clicked", Toast.LENGTH_SHORT);
                toast.show();
                LocalAppointment tempApp = appointment.get(position);
                tempApp.status = "Approved";
                tempApp.save();
                activity.onResume();
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvAppointment;
        public TextView appointmentDate;
        public TextView appointmentDoctor;
        public TextView appointmentPatient;
        public TextView appointmentStatus;
        public TextView appointmentUpdateAccept;
        public TextView appointmentUpdateReject;
        public ViewHolder(View v) {
            super(v);
            Typeface iconFont = FontManager.getTypeface(v.getContext(), FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(v.findViewById(R.id.categoryAppointment), iconFont);

            cvAppointment = (CardView) v.findViewById(R.id.categoryAppointment);
            appointmentDate = (TextView) v.findViewById(R.id.appointment_date);
            appointmentDoctor = (TextView) v.findViewById(R.id.appointment_doctor);
            appointmentPatient = (TextView) v.findViewById(R.id.appointment_patient);
            appointmentStatus = (TextView) v.findViewById(R.id.appointment_status);
            appointmentUpdateAccept = (TextView) v.findViewById(R.id.bAcceptAppointment);
            appointmentUpdateReject = (TextView) v.findViewById(R.id.bRejectAppointment);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
                }
            });
        }
    }
}
