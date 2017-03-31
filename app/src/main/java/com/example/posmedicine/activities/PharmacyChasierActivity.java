package com.example.posmedicine.activities;

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
import com.example.posmedicine.R;
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    TextView bSelectMedicine, bCancel;
    Button bPayment;
    Double totalPrice = 0.0;

    private ArrayList<TransactionMedicine> listTrMedicine = null;
    ApiService service;
    Medicine selectedMedicine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy_chasier);
        //Log.d("getFilesDir", String.valueOf(getFilesDir()));

        autocompleteMedicine = (AutoCompleteTextView) findViewById(R.id.autoCompleteMedicine);

        service = RestClient.getInstance().getApiService();
//        grandTotalTV = (TextView)findViewById(R.id.textGrandTotal);
        bCancel = (TextView)findViewById(R.id.bCancel);
        listTrMedicine = new ArrayList<>();

        getMedicine();

        autocompleteMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMedicine = (Medicine) parent.getAdapter().getItem(position);
            }
        });

        inputQty = (EditText)findViewById(R.id.iput_quantity);

        validator = new Validator(this);
        validator.setValidationListener(this);

        bSelectMedicine = (TextView) findViewById(R.id.bSelectMedicine);
        bSelectMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();

            }
        });

        bPayment = (Button) findViewById(R.id.bPayment);
        bPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listTrMedicine.size() != 0) {
                    Log.d("total price", String.valueOf(listTrMedicine));
                    Log.d("total price", String.valueOf(listTrMedicine));

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    Log.d("formattedDate", formattedDate);

                    createTransaction(formattedDate, String.valueOf(getTotalPrice()), String.valueOf(getTotalPrice()));
                }else{
                    Toast.makeText(getApplicationContext(),"Please select Medicine",Toast.LENGTH_SHORT).show();
                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getMedicine(){
        service.getMedicine().enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
                Log.d("responseMedicine", String.valueOf(response.body().getMedicine()));
                ArrayAdapter myAdapter = new ArrayAdapter<Medicine>(PharmacyChasierActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getMedicine());
                autocompleteMedicine.setThreshold(2);
                autocompleteMedicine.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        //Toast.makeText(this, "Yay! we got it right!", Toast.LENGTH_SHORT).show();
        Log.d("medicine data", String.valueOf(selectedMedicine));
        if(selectedMedicine == null){
            Toast.makeText(getApplicationContext(),"Please select Medicine",Toast.LENGTH_SHORT).show();
        }else{
            Integer valQty;
            Double medicinePrice,medicineQuantity;
            valQty = Integer.parseInt(String.valueOf(inputQty.getText()));
            medicineQuantity = Double.parseDouble(selectedMedicine.getQuantity());
            if(valQty <= medicineQuantity){
                selectedMedicine.setQuantity(String.valueOf(medicineQuantity - valQty));

                medicinePrice = Double.parseDouble(selectedMedicine.getPrice());
                selectedMedicine.setPrice(String.valueOf(medicinePrice * valQty));
                Log.d("perkalian",String.valueOf(medicinePrice * valQty));
                TransactionMedicine trMedicine = new TransactionMedicine();
                trMedicine.setMedicine(selectedMedicine);
                trMedicine.setMedicineId(selectedMedicine.getId());
                trMedicine.setQuantity(valQty);
                trMedicine.setUnitId(selectedMedicine.getUnitId());
                trMedicine.setPrice(String.valueOf(selectedMedicine.getPrice()));
                trMedicine.setTotalPrice(String.valueOf(Double.parseDouble(trMedicine.getPrice()) * trMedicine.getQuantity()));

                listTrMedicine.add(trMedicine);

                setTextTotalPrice();

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
                Toast.makeText(getApplicationContext(),"Quantity is not enough, "+selectedMedicine.getName()+" : "+myInt+" "+selectedMedicine.getUnitName(),Toast.LENGTH_LONG).show();
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
                int purchaseHeaderId = response.body().getData().getId();
                Log.d("purchaseID", String.valueOf(purchaseHeaderId));
                for(int i=0; i<listTrMedicine.size(); i++){
                    final TransactionMedicine trMedicineTemp;
                    trMedicineTemp =  listTrMedicine.get(i);
                    service.createPurchaseDetail(purchaseHeaderId,trMedicineTemp.getMedicineId(),trMedicineTemp.getQuantity(),trMedicineTemp.getMedicine().getUnitId(),trMedicineTemp.getPrice(),trMedicineTemp.getTotalPrice()).enqueue(new Callback<PurchaseDetailResponse>() {
                        @Override
                        public void onResponse(Call<PurchaseDetailResponse> call, Response<PurchaseDetailResponse> response) {
                            Log.d("Purchase Detail : ", String.valueOf(response.body().isStatus()));
                        }

                        @Override
                        public void onFailure(Call<PurchaseDetailResponse> call, Throwable t) {

                        }
                    });

//                    service.updateMedicine(trMedicineTemp.getMedicineId(),selectedMedicine.getName(),selectedMedicine.getQuantity(),selectedMedicine.getUnitId(),selectedMedicine.getPrice(),selectedMedicine.getType(),selectedMedicine.getDateStock(),selectedMedicine.getDateExpiration()).enqueue(new Callback<MedicineResponse>() {
//                        @Override
//                        public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
//                            Log.d("updateMedicine", String.valueOf(response.body().getMedicine()));
//                        }
//
//                        @Override
//                        public void onFailure(Call<MedicineResponse> call, Throwable t) {
//
//                        }
//                    });

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
                clearTextTotalPrice();
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
        double totalPriceCalculation = 0.0;
        for (TransactionMedicine medicineRow : listTrMedicine) {
            totalPriceCalculation = totalPriceCalculation + Double.parseDouble(medicineRow.getTotalPrice());
        }
        return this.totalPrice = totalPriceCalculation;
    }

    public void setTextTotalPrice(){
        bPayment.setText("Grand Total Rp : "+NumberFormat.getInstance().format(getTotalPrice()));
    }

    public void clearTextTotalPrice(){
        bPayment.setText("Grand Total Rp : ");
    }
}
