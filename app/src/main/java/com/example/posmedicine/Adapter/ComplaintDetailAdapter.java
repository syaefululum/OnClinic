package com.example.posmedicine.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.example.posmedicine.activities.ComplaintDetailActivity;
import com.example.posmedicine.models.ComplaintDetail;

import java.util.List;

/**
 * Created by Surya_N2267 on 3/3/2017.
 */

public class ComplaintDetailAdapter extends RecyclerView.Adapter<ComplaintDetailAdapter.ViewHolder> {
    private List<ComplaintDetail> complaintDetail;

    public ComplaintDetailAdapter(List<ComplaintDetail> complaintDetail) {
        this.complaintDetail = complaintDetail;
    }

    @Override
    public ComplaintDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_detail_complaint, parent, false);
        return new ComplaintDetailAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComplaintDetailAdapter.ViewHolder holder, int position) {
        String status = complaintDetail.get(position).getStatus();
        holder.complaintService.setText(complaintDetail.get(position).getServiceName());
        holder.complaintStatus.setText(status);
        if (status.equals("Queuing")) {
            holder.complaintStatus.setTextColor(Color.parseColor("#009acd"));
        } else if (status.equals("Done")) {
            holder.complaintStatus.setTextColor(Color.parseColor("#228b22"));
        } else {
            holder.complaintStatus.setTextColor(Color.parseColor("#ffff00"));
        }
        holder.complaintDoctor.setText(complaintDetail.get(position).getDoctorName());
        holder.complaintTime.setText(complaintDetail.get(position).getTime());

        holder.complaintDetailUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.complaintDetailDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvDetailComplaint;
        public TextView complaintDoctor;
        public TextView complaintTime;
        public TextView complaintService;
        public TextView complaintStatus;
        public TextView complaintDetailUpdateButton;
        public TextView complaintDetailDeleteButton;

        public ViewHolder(View v) {
            super(v);
            cvDetailComplaint = (CardView) v.findViewById(R.id.cvDetailComplaint);
            complaintDoctor = (TextView) v.findViewById(R.id.tComplaint_DoctorName);
            complaintTime = (TextView) v.findViewById(R.id.tComplaint_Time);
            complaintStatus = (TextView) v.findViewById(R.id.tComplaint_Status);
            complaintService = (TextView) v.findViewById(R.id.tComplaint_Serviceid);

            complaintDetailUpdateButton = (TextView) v.findViewById(R.id.tComplaint_UpdateButton);
            complaintDetailDeleteButton = (TextView) v.findViewById(R.id.tComplaint_DeleteButton);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
