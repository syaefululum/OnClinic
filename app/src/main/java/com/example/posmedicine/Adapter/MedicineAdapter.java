package com.example.posmedicine.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.activities.EditMedicineActivity;
import com.example.posmedicine.activities.MedicineActivity;
import com.example.posmedicine.R;
import com.example.posmedicine.models.Medicine;
import com.example.posmedicine.models.Unit;

import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.MedicineSingleResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Syaeful_U1438 on 02-Feb-17.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    private List<Medicine> medicine;
   // private List<Unit> unit;
    private MedicineActivity activity;

    public MedicineAdapter(List<Medicine> medicine, MedicineActivity activity){
        this.medicine = medicine;
        this.activity = activity;
    }

    public MedicineAdapter(){

    }

    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_medicine, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MedicineAdapter.ViewHolder holder, final int position) {
       // Unit unit = medicine.get(position).getUnit();
        holder.medicineName.setText(medicine.get(position).getName());
        holder.medicineType.setText(medicine.get(position).getType());
        holder.medicineStock.setText(NumberFormat.getInstance().format(Double.parseDouble(medicine.get(position).getQuantity())));
        holder.medicinePrice.setText("Rp. " + NumberFormat.getInstance().format(Double.parseDouble(medicine.get(position).getPrice())));

        holder.unitName1.setText(" / "+ medicine.get(position).getUnitName());
        holder.unitName2.setText("" + medicine.get(position).getUnitName());

        holder.cvMedicine.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Bundle extras = new Bundle();
                extras.putInt("idMedicine", medicine.get(position).getId());
                extras.putString("medicineName", medicine.get(position).getName());
                extras.putString("medicineType", medicine.get(position).getType());
                extras.putString("medicinePrice", medicine.get(position).getPrice());
                extras.putString("medicineStock", medicine.get(position).getQuantity());
                extras.putString("expireDate", medicine.get(position).getDateExpiration());
                extras.putString("stockedDate", medicine.get(position).getDateStock());
                extras.putInt("medicineUnitId", medicine.get(position).getUnitId());
                extras.putParcelable("interface", (Parcelable) activity);

                Intent editMedicine = new Intent(v.getContext(), EditMedicineActivity.class);
                editMedicine.putExtras(extras);
                v.getContext().startActivity(editMedicine);
            }
        });
        holder.bDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int medicineId = medicine.get(position).getId();
                                Log.d("medicineId", String.valueOf(medicineId));
                                deleteMedicine(medicineId);
                                activity.getMedicine("",10,1);
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

    public void deleteMedicine(int medId){
        ApiService service;
        service = RestClient.getInstance().getApiService();
        service.deleteMedicine(medId).enqueue(new Callback<MedicineSingleResponse>() {
            @Override
            public void onResponse(Call<MedicineSingleResponse> call, Response<MedicineSingleResponse> response) {
                if(response.body().isStatus()){
                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Delete Success", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Cannot delete this item", Toast.LENGTH_SHORT);
                    toast.show();
                }
                activity.onResume();
            }

            @Override
            public void onFailure(Call<MedicineSingleResponse> call, Throwable t) {
                Toast toast = Toast.makeText(activity.getApplicationContext(), "Connection failed, please try again", Toast.LENGTH_SHORT);
                toast.show();
                activity.onResume();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicine.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        // private final TextView textView;
        public CardView cvMedicine;
        public TextView medicineName;
        public TextView medicineType;
        public TextView medicineStock;
        public TextView medicinePrice;
        public TextView unitName1;
        public TextView unitName2;
//        public ImageView bDelete;
        public TextView bDelete;
        public ViewHolder(View v) {
            super(v);
            Iconify.with(new FontAwesomeModule());
            cvMedicine = (CardView)v.findViewById(R.id.categoryMedicine);
            medicineName = (TextView)v.findViewById(R.id.medicine_name);
            medicineType = (TextView)v.findViewById(R.id.medicine_type);
            medicineStock = (TextView)v.findViewById(R.id.medicine_stock);
            medicinePrice = (TextView)v.findViewById(R.id.medicine_price);
            unitName1 = (TextView)v.findViewById(R.id.unit_medicine_1);
            unitName2 = (TextView)v.findViewById(R.id.unit_medicine_2);
            bDelete = (TextView) v.findViewById(R.id.bDeleteMedicine);
            //bDelete.setText("");
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
//            textView = (TextView) v.findViewById(R.id.textView);
        }
    }

}
