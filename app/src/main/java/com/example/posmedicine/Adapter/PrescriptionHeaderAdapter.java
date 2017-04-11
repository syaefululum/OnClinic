package com.example.posmedicine.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.posmedicine.activities.PrescriptionActivity;
import com.example.posmedicine.models.PrescriptionHeader;

import java.util.List;

/**
 * Created by Syaeful_U1438 on 11-Apr-17.
 */

public class PrescriptionHeaderAdapter extends RecyclerView.Adapter<PrescriptionHeaderAdapter.ViewHolder> {
    private List<PrescriptionHeader> prescriptionHeaders;
    private PrescriptionActivity activity;

    public PrescriptionHeaderAdapter(List<PrescriptionHeader> prescriptionHeaders, PrescriptionActivity activity){
        this.prescriptionHeaders = prescriptionHeaders;
        this.activity = activity;
    }
    @Override
    public PrescriptionHeaderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PrescriptionHeaderAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
