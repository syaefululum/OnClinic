package com.example.posmedicine;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.Adapter.MedicineTransactionAdapter;
import com.example.posmedicine.models.Medicine;
import com.example.posmedicine.models.TransactionMedicine;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.PurchaseDetailResponse;
import com.example.posmedicine.models.response.PurchaseHeaderResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PharmacyChasierActivity extends AppCompatActivity implements Validator.ValidationListener {
    Validator validator;

    @Required(order = 2)
    @NumberRule(order = 3, message = "Enter Quatity in Numeric", type = NumberRule.NumberType.INTEGER)
    EditText inputQty;

    @Required(order = 1)
    AutoCompleteTextView autocompleteMedicine;

    String[] arrName;
    Integer[] arrId;
    Button bSelectMedicine, bPayment;
    Double totalPrice = 0.0;

    Medicine medicine;
    private ArrayList<TransactionMedicine> listTrMedicine = null;
    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_chasier);
        //Log.d("getFilesDir", String.valueOf(getFilesDir()));

        service = RestClient.getInstance().getApiService();

        listTrMedicine = new ArrayList<>();

        getMedicine();

        inputQty = (EditText)findViewById(R.id.iput_quantity);

        validator = new Validator(this);
        validator.setValidationListener(this);

        bSelectMedicine = (Button)findViewById(R.id.bSelectMedicine);
        bSelectMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });

        bPayment = (Button)findViewById(R.id.bPayment);
        bPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("total price",String.valueOf(listTrMedicine));
                Log.d("total price",String.valueOf(listTrMedicine));
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                createTransaction(currentDateTimeString,String.valueOf(getTotalPrice()),String.valueOf(getTotalPrice()));
            }
        });

    }

    public void getMedicine(){
        service.getMedicine().enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, final Response<MedicineResponse> response) {
                arrName = new String[response.body().getMedicine().size()];
                arrId = new Integer[response.body().getMedicine().size()];
                for (int i = 0; i < response.body().getMedicine().size(); i++) {
                    arrName[i] = new String(response.body().getMedicine().get(i).getName());
                    arrId[i] = response.body().getMedicine().get(i).getId();
                }

                autocompleteMedicine = (AutoCompleteTextView)findViewById(R.id.autoCompleteMedicine);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.select_dialog_item,arrName);
                autocompleteMedicine.setThreshold(2);
                autocompleteMedicine.setAdapter(adapter);

                autocompleteMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = (String) parent.getItemAtPosition(position);
                        for(Medicine medicineRow : response.body().getMedicine()){
                            if(medicineRow.getName().equals(itemSelect)){
                                medicine = medicineRow;
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
        Log.d("medicine data", String.valueOf(medicine));
        if(medicine == null){
            Toast.makeText(getApplicationContext(),"Please select Medicine",Toast.LENGTH_SHORT).show();
        }else{
            Integer valQty;
            Double medicinePrice,medicineQuantity;
            valQty = Integer.parseInt(String.valueOf(inputQty.getText()));
            medicineQuantity = Double.parseDouble(medicine.getQuantity());
            if(valQty <= medicineQuantity){
                medicine.setQuantity(String.valueOf(medicineQuantity - valQty));

                medicinePrice = Double.parseDouble(medicine.getPrice());
                medicine.setPrice(String.valueOf(medicinePrice * valQty));
                Log.d("perkalian",String.valueOf(medicinePrice * valQty));
                TransactionMedicine trMedicine = new TransactionMedicine();
                trMedicine.setMedicine(medicine);
                trMedicine.setMedicineId(medicine.getId());
                trMedicine.setQuantity(valQty);
                trMedicine.setUnitId(medicine.getUnitId());
                trMedicine.setPrice(String.valueOf(medicine.getPrice()));
                trMedicine.setTotalPrice(trMedicine.getPrice() +  trMedicine.getQuantity());

                double totalPriceTemp = getTotalPrice();
                totalPriceTemp = totalPriceTemp + Double.parseDouble(trMedicine.getTotalPrice());
                setTotalPrice(totalPriceTemp);

                listTrMedicine.add(trMedicine);
                LinearLayoutManager llm = new LinearLayoutManager(PharmacyChasierActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                MedicineTransactionAdapter trAdapter = new MedicineTransactionAdapter(listTrMedicine,PharmacyChasierActivity.this);
                RecyclerView rvTransaction = (RecyclerView)findViewById(R.id.rvMedicineTransaction);
                rvTransaction.setLayoutManager(llm);
                rvTransaction.setAdapter(trAdapter);

                autocompleteMedicine.setText(null);
                inputQty.setText(null);
            }else{
                double myDb = medicineQuantity;
                int myInt = (int) myDb;
                Toast.makeText(getApplicationContext(),"Quantity is not enough, "+medicine.getName()+" : "+myInt+" "+medicine.getUnit().getName(),Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {
        final String failureMessage = failedRule.getFailureMessage();
        if (failedView instanceof EditText) {
            EditText failed = (EditText) failedView;
            failed.requestFocus();
            failed.setError(failureMessage);
        } else {
            Toast.makeText(getBaseContext(), failureMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public void createTransaction(String date, String totalPrice, String paid){
        service.createPurchaseHeader(date,totalPrice, paid).enqueue(new Callback<PurchaseHeaderResponse>() {
            @Override
            public void onResponse(Call<PurchaseHeaderResponse> call, Response<PurchaseHeaderResponse> response) {
                int purchaseHeaderId = response.body().getId();
                Log.d("purchaseID", String.valueOf(purchaseHeaderId));
                for(int i=0; i<listTrMedicine.size(); i++){
                    TransactionMedicine trMedicineTemp;
                    trMedicineTemp =  listTrMedicine.get(i);
                    service.createPurchaseDetail(purchaseHeaderId,trMedicineTemp.getMedicineId(),trMedicineTemp.getQuantity(),trMedicineTemp.getMedicine().getUnitId(),trMedicineTemp.getPrice(),trMedicineTemp.getTotalPrice()).enqueue(new Callback<PurchaseDetailResponse>() {
                        @Override
                        public void onResponse(Call<PurchaseDetailResponse> call, Response<PurchaseDetailResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<PurchaseDetailResponse> call, Throwable t) {

                        }
                    });

                    service.updateMedicine(trMedicineTemp.getMedicineId(),medicine.getName(),medicine.getQuantity(),medicine.getUnitId(),medicine.getPrice(),medicine.getType(),medicine.getDateStock(),medicine.getDateExpiration()).enqueue(new Callback<MedicineResponse>() {
                        @Override
                        public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {

                        }

                        @Override
                        public void onFailure(Call<MedicineResponse> call, Throwable t) {

                        }
                    });

                }
                listTrMedicine.clear();
                LinearLayoutManager llm = new LinearLayoutManager(PharmacyChasierActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                MedicineTransactionAdapter trAdapter = new MedicineTransactionAdapter(listTrMedicine,PharmacyChasierActivity.this);
                RecyclerView rvTransaction = (RecyclerView)findViewById(R.id.rvMedicineTransaction);
                rvTransaction.setLayoutManager(llm);
                rvTransaction.setAdapter(trAdapter);

                autocompleteMedicine.setText(null);
                inputQty.setText(null);
                Toast.makeText(getApplicationContext(),"Successful create transaction",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<PurchaseHeaderResponse> call, Throwable t) {

            }
        });
    }



    public void setTotalPrice(double totalPrice){
        this.totalPrice = totalPrice;
    }

    public double getTotalPrice(){
        return this.totalPrice;
    }
}
