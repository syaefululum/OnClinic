package com.example.posmedicine.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.posmedicine.PharmacyChasierActivity;
import com.example.posmedicine.R;
import com.example.posmedicine.models.TransactionMedicine;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Syaeful_U1438 on 09-Feb-17.
 */

public class MedicineTransactionAdapter extends RecyclerView.Adapter<MedicineTransactionAdapter.ViewHolder> {
    private List<TransactionMedicine> trMedicine;
    private PharmacyChasierActivity activity;

    public MedicineTransactionAdapter(List<TransactionMedicine> trMedicine, PharmacyChasierActivity activity){
        this.trMedicine = trMedicine;
        this.activity = activity;
    }


    @Override
    public MedicineTransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_medicine_transaction, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MedicineTransactionAdapter.ViewHolder holder, final int position) {
        holder.medicineName.setText(trMedicine.get(position).getMedicine().getName());
        holder.medicinePrice.setText("Rp. "+NumberFormat.getInstance().format(Double.parseDouble(trMedicine.get(position).getMedicine().getPrice())));
        holder.medicineQuantity.setText(""+trMedicine.get(position).getQuantity());
        holder.medicineType1.setText(trMedicine.get(position).getMedicine().getUnit().getName());
//        holder.medicineType2.setText(" / " + trMedicine.get(position).getMedicine().getUnit().getName());
        holder.bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trMedicine.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, trMedicine.size());
            }
        });

        holder.medicinePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged","beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged","onTextChanged");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterchange",holder.medicinePrice.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trMedicine.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cvTrMedicine;
        public TextView medicineName;
        public EditText medicineQuantity;
        public TextView medicinePrice;
        public TextView medicineType1;
        public TextView medicineType2;
        public ImageView bDelete;
        public ViewHolder(View v){
            super(v);
            cvTrMedicine = (CardView)v.findViewById(R.id.categoryMedicineTransaction);
            medicineName = (TextView)v.findViewById(R.id.tmedicine_name);
            medicineQuantity = (EditText)v.findViewById(R.id.tmedicine_stock);
            medicinePrice = (TextView)v.findViewById(R.id.tmedicine_price_text);
            medicineType1 = (TextView)v.findViewById(R.id.tmedicine_type_text1);
            medicineType2 = (TextView)v.findViewById(R.id.tmedicine_type_text2);
            bDelete = (ImageView)v.findViewById(R.id.bDeleteMedicine);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
