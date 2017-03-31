package com.example.posmedicine.activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.activities.CreateMedicineActivity;
import com.example.posmedicine.models.Unit;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.MedicineSingleResponse;
import com.example.posmedicine.models.response.UnitResponse;
import com.example.posmedicine.models.response.UnitSingleDataResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.Required;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMedicineActivity extends AppCompatActivity implements Validator.ValidationListener{
    @Required(order = 1)
    AutoCompleteTextView autocomplete;

    @Required(order = 2, message = "Enter Medicine Name")
    EditText eMedicineName;

    @Required(order = 3, message = "Enter Medicine Type")
    EditText eMedicineType;

    @Required(order = 4)
    @NumberRule(order = 5, message = "Enter Price in Numeric", type = NumberRule.NumberType.INTEGER)
    EditText eMedicinePrice;

    @Required(order = 6)
    @NumberRule(order = 7, message = "Enter Stock in Numeric", type = NumberRule.NumberType.INTEGER)
    EditText eMedicineStock;

    @Required(order = 8, message = "Select Expire Date")
    TextView expireDate;

    @Required(order = 9, message = "Select Stock Date")
    TextView stockedDate;

    ApiService service;

    TextView unitSelected_1, unitSelected_2, bCancel, setExpireDate, setStockedDate;
    Button bEditMedicine;

    Unit selectedUnit;
    Validator validator;
    int medicineId, medicineUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        service = RestClient.getInstance().getApiService();


        validator = new Validator(this);
        validator.setValidationListener(this);

        getUnit();

        Bundle extras = getIntent().getExtras();
        medicineId = extras.getInt("idMedicine");
        medicineUnitId = extras.getInt("medicineUnitId");
        String medicineName = extras.getString("medicineName");
        String medicineType = extras.getString("medicineType");
        String medicinePrice = extras.getString("medicinePrice");
        String medicineStock = extras.getString("medicineStock");
        String eExpireDate = extras.getString("expireDate");
        String eStockedDate = extras.getString("stockedDate");

        eMedicineName = (EditText)findViewById(R.id.emedicine_name);
        eMedicineType = (EditText)findViewById(R.id.emedicine_type);
        eMedicinePrice = (EditText)findViewById(R.id.emedicine_price);
        eMedicineStock = (EditText)findViewById(R.id.emedicine_stock);
        expireDate = (TextView)findViewById(R.id.expire_date);
        stockedDate = (TextView)findViewById(R.id.stocked_date);
        unitSelected_1 = (TextView)findViewById(R.id.eunitSelected_1);
        unitSelected_2 = (TextView)findViewById(R.id.eunitSelected_2);

        getUnitById(medicineUnitId);

        setExpireDate = (TextView) findViewById(R.id.setExpireDate);
        setStockedDate = (TextView) findViewById(R.id.setStockedDate);
        autocomplete = (AutoCompleteTextView) findViewById(R.id.eAutoCompleteTextViewUnit);

        eMedicineName.setText(medicineName);
        eMedicineType.setText(medicineType);
        eMedicinePrice.setText(medicinePrice);
        eMedicineStock.setText(medicineStock);
        stockedDate.setText(eStockedDate);
        expireDate.setText(eExpireDate);

       autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               selectedUnit = (Unit) parent.getAdapter().getItem(position);
               unitSelected_1.setText(" / " + selectedUnit.getName());
               unitSelected_2.setText(" / " + selectedUnit.getName());
           }
       });

        bEditMedicine = (Button)findViewById(R.id.bEditMedicine);

        bEditMedicine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        bCancel = (TextView)findViewById(R.id.bCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setExpireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 1);

                DialogFragment newFragment = new CreateMedicineActivity.DatePickerFragment();
                newFragment.setArguments(bundle);

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        setStockedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 2);

                DialogFragment newFragment = new CreateMedicineActivity.DatePickerFragment();
                newFragment.setArguments(bundle);

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

    }

    public void updateUnitName(int medicineId, String medicineName, String medicineType, String medicinePrice, String medicineStock, String expireDate, String stockedDate, int unitId){
        service.updateMedicine(medicineId, medicineName, medicineStock, unitId, medicinePrice, medicineType, stockedDate, expireDate).enqueue(new Callback<MedicineSingleResponse>() {
            @Override
            public void onResponse(Call<MedicineSingleResponse> call, Response<MedicineSingleResponse> response) {
                if(response.body().isStatus()){
                    Toast toast = Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Failed edit this item", Toast.LENGTH_LONG);
                    toast.show();
                }
                finish();
                onResume();
            }

            @Override
            public void onFailure(Call<MedicineSingleResponse> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Connection failed, please try again", Toast.LENGTH_SHORT);
                toast.show();
                finish();
                onResume();
            }
        });
    }

    public void getUnitById(int unitId){
        service.getUnitById(unitId).enqueue(new Callback<UnitSingleDataResponse>() {
            @Override
            public void onResponse(Call<UnitSingleDataResponse> call, Response<UnitSingleDataResponse> response) {
                selectedUnit = response.body().getUnit();

                unitSelected_1.setText(" / " + selectedUnit.getName());
                unitSelected_2.setText(" / " + selectedUnit.getName());

                autocomplete.setText(selectedUnit.getName());
            }

            @Override
            public void onFailure(Call<UnitSingleDataResponse> call, Throwable t) {
                selectedUnit = null;
            }
        });
    }

    public void getUnit(){
        service.getUnits().enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                ArrayAdapter myAdapter = new ArrayAdapter<Unit>(EditMedicineActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getUnit());
                autocomplete.setThreshold(2);
                autocomplete.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        if(selectedUnit == null){
            Toast.makeText(getApplicationContext(),"Please select Unit of Medicine",Toast.LENGTH_SHORT).show();
        }else{
            String medicineName = eMedicineName.getText().toString();
            String medicineType = eMedicineType.getText().toString();
            String medicinePrice = eMedicinePrice.getText().toString();
            String medicineStock = eMedicineStock.getText().toString();
            String eExpireDate = expireDate.getText().toString();
            String eStockedDate = stockedDate.getText().toString();

            updateUnitName(medicineId, medicineName,medicineType, medicinePrice, medicineStock, eExpireDate, eStockedDate, selectedUnit.getId());
            finish();
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
}
