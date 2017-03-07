package com.example.posmedicine.Adapter;

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

    public ComplaintDetailAdapter(List<ComplaintDetail> complaintDetail){
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
        holder.complaintDetailService.setText(complaintDetail.get(position).getService().getName());
        holder.complaintDetailStatus.setText(complaintDetail.get(position).getStatus());
        holder.complaintDetailResult.setText(complaintDetail.get(position).getResult());
        holder.complaintDetailDescription.setText(complaintDetail.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return complaintDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvHeaderComplaint;
        public TextView complaintDetailService;
        public TextView complaintDetailStatus;
        public TextView complaintDetailResult;
        public TextView complaintDetailDescription;

        public ViewHolder(View v) {
            super(v);
            cvHeaderComplaint = (CardView)v.findViewById(R.id.cvHeaderComplaint);
            complaintDetailService = (TextView)v.findViewById(R.id.tComplaint_Serviceid);
            complaintDetailStatus = (TextView)v.findViewById(R.id.tComplaint_Status);
            complaintDetailResult = (TextView)v.findViewById(R.id.tComplaint_Result);
            complaintDetailDescription = (TextView)v.findViewById(R.id.tComplaint_Detaildescription);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
