package com.example.posmedicine.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.example.posmedicine.models.PrescriptionDetail;

import java.util.List;

/**
 * Created by Syaeful_U1438 on 13-Apr-17.
 */

public class MedicineHistoryAdapter extends RecyclerView.Adapter<MedicineHistoryAdapter.ViewHolder> {
    private List<PrescriptionDetail> prescriptionDetails;

    public MedicineHistoryAdapter(List<PrescriptionDetail> prescriptionDetails){
        this.prescriptionDetails = prescriptionDetails;
    }

    @Override
    public MedicineHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_medicine_history, parent, false);
        return new MedicineHistoryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MedicineHistoryAdapter.ViewHolder holder, int position) {
        holder.medicineName.setText(prescriptionDetails.get(position).getMedicineName());
        holder.medicineNote.setText(prescriptionDetails.get(position).getNote());
    }

    @Override
    public int getItemCount() {
        return prescriptionDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvPresMedicine;
        public TextView medicineName;
        public TextView medicineNote;
        public ViewHolder(View itemView) {
            super(itemView);
            cvPresMedicine = (CardView)itemView.findViewById(R.id.medicineCardView);
            medicineName = (TextView)itemView.findViewById(R.id.medicine_name);
            medicineNote = (TextView)itemView.findViewById(R.id.medicine_note);
        }
    }
}
