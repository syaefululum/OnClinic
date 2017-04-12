package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.Adapter.PrescriptionDetailAdapter;
import com.example.posmedicine.R;
import com.example.posmedicine.models.Medicine;
import com.example.posmedicine.models.PrescriptionHeader;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.PrescriptionDetailResponse;
import com.example.posmedicine.models.response.PrescriptionHeaderResponse;
import com.example.posmedicine.models.response.PrescriptionHeaderSingleResponse;
import com.example.posmedicine.models.response.PurchaseDetailResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionActivity extends AppCompatActivity implements Validator.ValidationListener {
    Integer complaintHeaderId;
    Validator validator;

    @Required(order = 1)
    AutoCompleteTextView autocompleteMedicine;

    @Required(order = 2)
    @NumberRule(order = 3, message = "Enter Quatity in Numeric", type = NumberRule.NumberType.INTEGER)
    EditText inputQty;

    TextView bCancel;
    EditText prescriptionNote;
    Button bSelectMedicine;
    Medicine selectedMedicine;
    ApiService service;
    PrescriptionHeader prescriptionHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        service = RestClient.getInstance().getApiService();
        prescriptionHeader = new PrescriptionHeader();

        autocompleteMedicine = (AutoCompleteTextView) findViewById(R.id.autoCompleteMedicine);
        inputQty = (EditText)findViewById(R.id.iput_quantity);
        bCancel = (TextView)findViewById(R.id.bCancel);
        prescriptionNote = (EditText) findViewById(R.id.prescriptionNote);
        bSelectMedicine = (Button) findViewById(R.id.bSelectMedicine);

        bSelectMedicine.setFocusable(true);
        getMedicine();

        autocompleteMedicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMedicine = (Medicine) parent.getAdapter().getItem(position);
            }
        });

        validator = new Validator(this);
        validator.setValidationListener(this);

        bSelectMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        complaintHeaderId = Integer.parseInt(getIntent().getStringExtra("COMPLAINT_HEADER_ID"));
        getPresriptionHeader(complaintHeaderId);
        Log.d("COMPLAINT_HEADER_ID", String.valueOf(complaintHeaderId));
    }

    @Override
    public void onValidationSucceeded() {
        final String quantity = String.valueOf(inputQty.getText());
        final String note = String.valueOf(prescriptionNote.getText());
        if(prescriptionHeader == null){
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());

            service.createPrescriptionHeader(complaintHeaderId,formattedDate).enqueue(new Callback<PrescriptionHeaderSingleResponse>() {
                @Override
                public void onResponse(Call<PrescriptionHeaderSingleResponse> call, Response<PrescriptionHeaderSingleResponse> response) {
                    if(response.body().isStatus()){
                        postPrescriptionDetail(response.body().getPrescriptionHeaders().getId(),selectedMedicine.getName(),quantity,selectedMedicine.getUnitId(),note);
                    }else{
                        Toast.makeText(getApplicationContext(),"Failed create prescription",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<PrescriptionHeaderSingleResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),"Failed, Connection Problem",Toast.LENGTH_LONG).show();
                }
            });
        }else{
            postPrescriptionDetail(prescriptionHeader.getId(),selectedMedicine.getName(),quantity,selectedMedicine.getUnitId(),note);
        }
    }

    @Override
    public void onValidationFailed(View failedView, Rule<?> failedRule) {

    }

    public void postPrescriptionDetail(Integer presriptionHeaderId, String medicineName, String quantity, Integer unitId, String note){
        service.createPrescriptionDetail(presriptionHeaderId, medicineName, quantity, unitId, note).enqueue(new Callback<PrescriptionDetailResponse>() {
            @Override
            public void onResponse(Call<PrescriptionDetailResponse> call, Response<PrescriptionDetailResponse> response) {
                if(response.body().isStatus()){
                    //add to list
                    //prescriptionHeader.getPrescriptionDetails().add(response.body().getPrescriptionDetail());
                    prescriptionHeader = new PrescriptionHeader();
                    getPresriptionHeader(complaintHeaderId);

                    Toast.makeText(getApplicationContext(),"Successfully add medicine record",Toast.LENGTH_LONG).show();
                    inputQty.setText("");
                    prescriptionNote.setText("");
                    selectedMedicine = null;
                    autocompleteMedicine.setText("");

                    try  {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Failed add medicine record",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PrescriptionDetailResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed, Connection Problem",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getMedicine(){
        service.getMedicine().enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
                Log.d("responseMedicine", String.valueOf(response.body().getMedicine()));
                ArrayAdapter myAdapter = new ArrayAdapter<Medicine>(PrescriptionActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getMedicine());
                autocompleteMedicine.setThreshold(2);
                autocompleteMedicine.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {

            }
        });
    }

    public void getPresriptionHeader(Integer complaintId){
        service.getPrescriptionByComplaint(complaintId).enqueue(new Callback<PrescriptionHeaderSingleResponse>() {
            @Override
            public void onResponse(Call<PrescriptionHeaderSingleResponse> call, Response<PrescriptionHeaderSingleResponse> response) {
                if(response.body().isStatus()){
                    prescriptionHeader = response.body().getPrescriptionHeaders();
                    Log.d("prescriptionHeader", String.valueOf(prescriptionHeader));
                    if(prescriptionHeader != null){
                        LinearLayoutManager llm = new LinearLayoutManager(PrescriptionActivity.this);
                        llm.setOrientation(LinearLayoutManager.VERTICAL);

                        PrescriptionDetailAdapter prescriptionDetailAdapter = new PrescriptionDetailAdapter(prescriptionHeader.getPrescriptionDetails(),PrescriptionActivity.this);
                        RecyclerView rvMedicine = (RecyclerView)findViewById(R.id.rvMedicinePrescription);

                        rvMedicine.setLayoutManager(llm);
                        rvMedicine.setAdapter(prescriptionDetailAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<PrescriptionHeaderSingleResponse> call, Throwable t) {

            }
        });
    }
}
