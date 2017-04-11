package com.example.posmedicine.Adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.example.posmedicine.activities.PrescriptionActivity;
import com.example.posmedicine.models.PrescriptionDetail;
import com.example.posmedicine.models.response.PrescriptionDetailResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import java.security.PublicKey;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Syaeful_U1438 on 11-Apr-17.
 */

public class PrescriptionDetailAdapter extends RecyclerView.Adapter<PrescriptionDetailAdapter.ViewHolder> {
    List<PrescriptionDetail> prescriptionDetails;
    PrescriptionActivity activity;

    public PrescriptionDetailAdapter(List<PrescriptionDetail> prescriptionDetails, PrescriptionActivity activity){
        this.activity = activity;
        this.prescriptionDetails = prescriptionDetails;
    }

    @Override
    public PrescriptionDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_prescription_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PrescriptionDetailAdapter.ViewHolder holder, final int position) {
        holder.medicineName.setText(prescriptionDetails.get(position).getMedicineName());
        holder.medicineType.setText(prescriptionDetails.get(position).getUnitName());
        holder.medicineQuantity.setText(prescriptionDetails.get(position).getQuantity());
        holder.medicineType1.setText(prescriptionDetails.get(position).getUnitName());
        holder.medicineNote.setText(prescriptionDetails.get(position).getNote());
        holder.bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do delete
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            int prescriptionDetailId = prescriptionDetails.get(position).getId();
                            deletePrescription(prescriptionDetailId, position);
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                    }
                };

                builder.setMessage("Are you sure to delete this item?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return prescriptionDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvTrMedicine;
        public TextView medicineType;
        public TextView medicineName;
        public TextView medicineQuantity;
        public TextView medicineNote;
        public TextView medicineType1;
        public TextView bDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            cvTrMedicine = (CardView)itemView.findViewById(R.id.categoryMedicine);
            medicineName = (TextView)itemView.findViewById(R.id.tmedicine_name);
            medicineType = (TextView)itemView.findViewById(R.id.tmedicine_type);
            medicineQuantity = (TextView)itemView.findViewById(R.id.tmedicine_stock);
            medicineNote = (TextView)itemView.findViewById(R.id.tmedicine_note_text);
            medicineType1 = (TextView)itemView.findViewById(R.id.tmedicine_type_text1);
            bDelete = (TextView)itemView.findViewById(R.id.bDeleteMedicine);
        }
    }

    public void deletePrescription(int prescriptionId, final int position){
        ApiService service;
        service = RestClient.getInstance().getApiService();
        service.deletePrescriptionDetail(prescriptionId).enqueue(new Callback<PrescriptionDetailResponse>() {
            @Override
            public void onResponse(Call<PrescriptionDetailResponse> call, Response<PrescriptionDetailResponse> response) {
                if(response.body().isStatus()){
                    prescriptionDetails.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, prescriptionDetails.size());
                }
            }

            @Override
            public void onFailure(Call<PrescriptionDetailResponse> call, Throwable t) {

            }
        });
    }
}
