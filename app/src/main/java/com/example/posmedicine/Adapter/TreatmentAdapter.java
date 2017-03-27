package com.example.posmedicine.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.example.posmedicine.activities.EditTreatmentActivity;
import com.example.posmedicine.activities.TreatmentDetailActivity;
import com.example.posmedicine.models.ComplaintDetail;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Surya_N2267 on 3/3/2017.
 */

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {
    private List<ComplaintDetail> treatment;
    public TreatmentAdapter(List<ComplaintDetail> treatment) {
        this.treatment = treatment;
    }

    @Override
    public TreatmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_treatment, parent, false);
        return new TreatmentAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TreatmentAdapter.ViewHolder holder, final int position) {
        String status = treatment.get(position).getStatus();
        holder.treatmentService.setText(treatment.get(position).getServiceName());
        holder.treatmentStatus.setText(status);
        if (status.equals("Queuing")) {
            holder.treatmentStatus.setTextColor(Color.parseColor("#009acd"));
        } else if (status.equals("Done")) {
            holder.treatmentStatus.setTextColor(Color.parseColor("#228b22"));
        } else {
            holder.treatmentStatus.setTextColor(Color.parseColor("#ffff00"));
        }
        holder.treatmentPatient.setText(treatment.get(position).getPatientName());
        holder.treatmentTime.setText(treatment.get(position).getTime());

        holder.cvTreatment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putSerializable("TREATMENT", treatment.get(position));

                Intent viewTreatment = new Intent(v.getContext(), TreatmentDetailActivity.class);
                viewTreatment.putExtras(extras);
                v.getContext().startActivity(viewTreatment);
            }
        });

        holder.treatmentTreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("DETAIL_ID", treatment.get(position).getId().toString());

                Intent editTreatment = new Intent(v.getContext(), EditTreatmentActivity.class);
                editTreatment.putExtras(extras);
                v.getContext().startActivity(editTreatment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return treatment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvTreatment;
        public TextView treatmentPatient;
        public TextView treatmentTime;
        public TextView treatmentService;
        public TextView treatmentStatus;
        public TextView treatmentTreat;

        public ViewHolder(View v) {
            super(v);
            cvTreatment = (CardView) v.findViewById(R.id.cvTreatment);
            treatmentPatient = (TextView) v.findViewById(R.id.tTreatment_PatientName);
            treatmentTime = (TextView) v.findViewById(R.id.tTreatment_Time);
            treatmentStatus = (TextView) v.findViewById(R.id.tTreatment_Status);
            treatmentService = (TextView) v.findViewById(R.id.tTreatment_Serviceid);

            treatmentTreat = (TextView) v.findViewById(R.id.tTreatment_TreatmentButton);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
