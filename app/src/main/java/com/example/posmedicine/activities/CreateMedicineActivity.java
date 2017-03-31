package com.example.posmedicine.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.models.Unit;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.UnitResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NumberRule;
import com.mobsandgeeks.saripaar.annotation.Required;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.list;

public class CreateMedicineActivity extends AppCompatActivity implements Validator.ValidationListener{
    @Required(order = 1)
    AutoCompleteTextView unitAutoComplete;

    @Required(order = 2, message = "Enter Medicine Name")
    EditText cMedicineName;

    @Required(order = 3, message = "Enter Medicine Type")
    EditText cMedicineType;

    @Required(order = 4)
    @NumberRule(order = 5, message = "Enter Price in Numeric", type = NumberRule.NumberType.INTEGER)
    EditText cMedicinePrice;

    @Required(order = 6)
    @NumberRule(order = 7, message = "Enter Stock in Numeric", type = NumberRule.NumberType.INTEGER)
    EditText cMedicineStock;

    @Required(order = 8, message = "Select Expire Date")
    TextView expireDate;

    @Required(order = 9, message = "Select Stock Date")
    TextView stockedDate;

    ApiService service;

    TextView bCancel, setStockedDate, setExpireDate, unitSelected_1, unitSelected_2;
    Button bCreateMedicine;

    String inputExpireDate, inputStockedDate;

    private Unit selectedUnit;
    Validator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_medicine);

        service = RestClient.getInstance().getApiService();

        cMedicineName = (EditText) findViewById(R.id.cmedicine_name);
        cMedicineType = (EditText) findViewById(R.id.cmedicine_type);
        cMedicinePrice = (EditText) findViewById(R.id.cmedicine_price);
        cMedicineStock = (EditText) findViewById(R.id.cmedicine_stock);
        expireDate = (TextView) findViewById(R.id.expire_date);
        stockedDate = (TextView) findViewById(R.id.stocked_date);

        bCreateMedicine = (Button) findViewById(R.id.bCreateMedicine);
        bCancel = (TextView) findViewById(R.id.bCancel);

        setExpireDate = (TextView) findViewById(R.id.setExpireDate);
        setStockedDate = (TextView) findViewById(R.id.setStockedDate);

        unitSelected_1 = (TextView)findViewById(R.id.unitSelected_1);
        unitSelected_2 = (TextView)findViewById(R.id.unitSelected_2);

        unitAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextViewUnit);

        getUnit();

        validator = new Validator(this);
        validator.setValidationListener(this);

        unitAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUnit = (Unit) parent.getAdapter().getItem(position);
                unitSelected_1.setText(" / " + selectedUnit.getName());
                unitSelected_2.setText(" / " + selectedUnit.getName());
            }
        });

        bCreateMedicine.setOnClickListener(new View.OnClickListener() {
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

        setExpireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 1);

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        setStockedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 2);

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);

                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });


    }

    @Override
    public void onValidationSucceeded() {
        if(selectedUnit == null){
            Toast.makeText(getApplicationContext(),"Please select Unit of Medicine",Toast.LENGTH_SHORT).show();
        }else{
            String medicineName = cMedicineName.getText().toString().trim();
            String medicineType = cMedicineType.getText().toString().trim();
            String medicinePrice = cMedicinePrice.getText().toString().trim();
            String medicineStock = cMedicineStock.getText().toString().trim();
            inputExpireDate =  expireDate.getText().toString();
            inputStockedDate = stockedDate.getText().toString();
            createNewMedicine(medicineName, medicineStock,selectedUnit.getId(),medicinePrice, medicineType, inputExpireDate,inputStockedDate);

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

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        static final int START_DATE = 1;
        static final int END_DATE = 2;

        private int mChosenDate;
        int cur = 0;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                mChosenDate = bundle.getInt("DATE", 1);
            }

            switch (mChosenDate) {
                case START_DATE:
                    cur = START_DATE;
                    return new DatePickerDialog(getActivity(), this, year, month, day);
                case END_DATE:
                    cur = END_DATE;
                    return new DatePickerDialog(getActivity(), this, year, month, day);

            }
            return null;
        }

        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            if (cur == START_DATE) {
                // set selected date into textview
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d = sdf.parse(day + "/" + month + "/" + year);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatted = format1.format(cal.getTime());

                    TextView expireDate = (TextView) getActivity().findViewById(R.id.expire_date);
                    expireDate.setText(formatted);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            } else {
                // set selected date into textview
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    Date d = sdf.parse(day + "/" + month + "/" + year);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(d);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    String formatted = format1.format(cal.getTime());

                    TextView stockedDate = (TextView) getActivity().findViewById(R.id.stocked_date);
                    stockedDate.setText(formatted);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public  void createNewMedicine(String medicineName, String medicineStock, Integer medicineUnit, String medicinePrice, String medicineType, String expireDate, String stockedDate){
        service.createMedicine(medicineName,medicineStock,medicineUnit, medicinePrice, medicineType, expireDate, stockedDate).enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {

                Toast toast = Toast.makeText(getApplicationContext(), "Create Success", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {
                finish();
            }
        });
    }

    public void getUnit() {
        service.getUnits().enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                ArrayAdapter myAdapter = new ArrayAdapter<Unit>(CreateMedicineActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getUnit());
                unitAutoComplete.setThreshold(2);
                unitAutoComplete.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {

            }
        });
    }
}
