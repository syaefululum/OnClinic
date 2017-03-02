package com.example.posmedicine.Adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.activities.AppointmentActivity;
import com.example.posmedicine.FontManager;
import com.example.posmedicine.R;
import com.example.posmedicine.models.Appointment;
import com.example.posmedicine.models.Doctor;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Surya_N2267 on 2/6/2017.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointment;
    private List<Doctor> doctor;
    private AppointmentActivity activity;

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
        holder.appointmentDoctor.setText(appointment.get(position).getDoctor().getPerson().getName());
        holder.appointmentPatient.setText(appointment.get(position).getPatient().getPerson().getName());
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
        Log.d(TAG, holder.appointmentStatus.getText().toString());

        holder.appointmentUpdateAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Element " + position + " clicked.");
//                Bundle extras = new Bundle();
//                extras.putInt("idAppointment", appointment.get(position).getid());
//                extras.putString("appointmentName", appointment.get(position).getName());
//                extras.putParcelable("interface", (Parcelable) activity);

//                Intent editDoctor = new Intent(v.getContext(), EditDoctorActivity.class);
//                editDoctor.putExtras(extras);
//                v.getContext().startActivity(editDoctor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointment.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        // private final TextView textView;
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
//                     Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            //textView = (TextView) v.findViewById(R.id.textView);
        }
    }
}
