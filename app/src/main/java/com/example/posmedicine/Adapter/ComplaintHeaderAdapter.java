package com.example.posmedicine.Adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.posmedicine.R;
import com.example.posmedicine.activities.ComplaintDetailActivity;
import com.example.posmedicine.activities.ComplaintHeaderActivity;
import com.example.posmedicine.models.ComplaintHeader;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Surya_N2267 on 3/3/2017.
 */

public class ComplaintHeaderAdapter extends RecyclerView.Adapter<ComplaintHeaderAdapter.ViewHolder> {
    private List<ComplaintHeader> complaintHeader;


    public ComplaintHeaderAdapter(List<ComplaintHeader> complaintHeader){
        this.complaintHeader = complaintHeader;
    }

    @Override
    public ComplaintHeaderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_header_complaint, parent, false);
        return new ComplaintHeaderAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComplaintHeaderAdapter.ViewHolder holder, final int position) {
        holder.complaintHeaderDoctor.setText(complaintHeader.get(position).getDoctor().getPerson().getName());
        holder.complaintHeaderPatient.setText(complaintHeader.get(position).getPatient().getPerson().getName());
        holder.complaintHeaderRegisterDate.setText(complaintHeader.get(position).getRegisteredDate());
        holder.complaintHeaderCheckDate.setText(complaintHeader.get(position).getCheckupDate());
        holder.complaintHeaderDescription.setText(complaintHeader.get(position).getDescription());
        holder.cvHeaderComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailComplaint = new Intent(v.getContext(), ComplaintDetailActivity.class);
                detailComplaint.putExtra("EXTRA_ID", complaintHeader.get(position).getId());
                v.getContext().startActivity(detailComplaint);
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintHeader.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvHeaderComplaint;
        public TextView complaintHeaderPatient;
        public TextView complaintHeaderDoctor;
        public TextView complaintHeaderDescription;
        public TextView complaintHeaderRegisterDate;
        public TextView complaintHeaderCheckDate;

        public ViewHolder(View v) {
            super(v);
            cvHeaderComplaint = (CardView)v.findViewById(R.id.cvHeaderComplaint);
            complaintHeaderPatient = (TextView)v.findViewById(R.id.tComplaint_Patientid);
            complaintHeaderDoctor = (TextView)v.findViewById(R.id.tComplaint_Doctorid);
            complaintHeaderDescription = (TextView)v.findViewById(R.id.tComplaint_Description);
            complaintHeaderRegisterDate = (TextView)v.findViewById(R.id.tComplaint_Registereddate);
            complaintHeaderCheckDate = (TextView)v.findViewById(R.id.tComplaint_Checkupdate);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
