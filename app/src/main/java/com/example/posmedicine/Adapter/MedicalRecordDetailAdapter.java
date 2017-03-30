package com.example.posmedicine.Adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.posmedicine.models.ComplaintHeader;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * Created by Surya_N2267 on 3/3/2017.
 */

public class MedicalRecordDetailAdapter extends RecyclerView.Adapter<MedicalRecordDetailAdapter.ViewHolder> {
    private List<ComplaintDetail> complaintDetail;

    public MedicalRecordDetailAdapter(List<ComplaintDetail> complaintDetail) {
        this.complaintDetail = complaintDetail;
    }

    @Override
    public MedicalRecordDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_patient_medical_record, parent, false);
        return new MedicalRecordDetailAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.complaintDoctor.setText(complaintDetail.get(position).getDoctorName());
        holder.complaintService.setText(complaintDetail.get(position).getServiceName());
        holder.complaintResult.setText(complaintDetail.get(position).getResult());
        holder.complaintDescription.setText(complaintDetail.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return complaintDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvDetailComplaint;
        public TextView complaintDoctor;
        public TextView complaintResult;
        public TextView complaintService;
        public TextView complaintDescription;

        public ViewHolder(View v) {
            super(v);
            cvDetailComplaint = (CardView) v.findViewById(R.id.cvDetailComplaint);
            complaintDoctor = (TextView) v.findViewById(R.id.tRecord_DoctorName);
            complaintService = (TextView) v.findViewById(R.id.tRecord_ServiceName);
            complaintResult = (TextView) v.findViewById(R.id.tRecord_Result);
            complaintDescription = (TextView) v.findViewById(R.id.tRecord_Description);
        }
    }
}
