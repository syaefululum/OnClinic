package com.example.posmedicine;

import android.os.Parcelable;
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

import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.UnitResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMedicineActivity extends AppCompatActivity {
    ApiService service;
    AutoCompleteTextView autocomplete;

    Button bEditMedicine, bCancel, setExpireDate, setStockedDate;
    EditText eMedicineName;
    EditText eMedicineType;
    EditText eMedicinePrice;
    EditText eMedicineStock;
    TextView expireDate, stockedDate, medicineType1, medicineType2;

    private Integer itemSelected;
    String[] arrName;
    Integer[] arrId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medicine);

        service = RestClient.getInstance().getApiService();
        final int medicineId, medicineUnitId;
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

        setExpireDate = (Button) findViewById(R.id.setExpireDate);
        setStockedDate = (Button) findViewById(R.id.setStockedDate);

        eMedicineName.setText(medicineName);
        eMedicineType.setText(medicineType);
        eMedicinePrice.setText(medicinePrice);
        eMedicineStock.setText(medicineStock);
        stockedDate.setText(eStockedDate);
        expireDate.setText(eExpireDate);

        setItemSelected(medicineUnitId);

        bEditMedicine = (Button)findViewById(R.id.bEditMedicine);
        bEditMedicine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String medicineName = eMedicineName.getText().toString();
                String medicineType = eMedicineType.getText().toString();
                String medicinePrice = eMedicinePrice.getText().toString();
                String medicineStock = eMedicineStock.getText().toString();
                String eExpireDate = expireDate.getText().toString();
                String eStockedDate = stockedDate.getText().toString();

                updateUnitName(medicineId, medicineName,medicineType, medicinePrice, medicineStock, eExpireDate, eStockedDate, getItemSelected());
                finish();
            }
        });
        bCancel = (Button)findViewById(R.id.bCancel);
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
        service.updateMedicine(medicineId, medicineName, medicineStock, unitId, medicinePrice, medicineType, stockedDate, expireDate).enqueue(new Callback<MedicineResponse>() {
            @Override
            public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {

                Toast toast = Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

            @Override
            public void onFailure(Call<MedicineResponse> call, Throwable t) {
                finish();
            }
        });
    }

    public void getUnit(){
        service.getUnits().enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                medicineType1 = (TextView)findViewById(R.id.eunitSelected_1);
                medicineType2 = (TextView)findViewById(R.id.eunitSelected_2);

                autocomplete = (AutoCompleteTextView) findViewById(R.id.eAutoCompleteTextViewUnit);
                arrName = new String[response.body().getUnit().size()];
                arrId = new Integer[response.body().getUnit().size()];
                for (int i = 0; i < response.body().getUnit().size(); i++) {
                    arrName[i] = new String(response.body().getUnit().get(i).getName());
                    arrId[i] = response.body().getUnit().get(i).getId();
                    if(response.body().getUnit().get(i).getId() == getItemSelected()){
                        autocomplete.setText(response.body().getUnit().get(i).getName());
                        medicineType1.setText(response.body().getUnit().get(i).getName());
                        medicineType2.setText(response.body().getUnit().get(i).getName());
                    }
                }



                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.select_dialog_item, arrName);
                autocomplete.setThreshold(2);
                autocomplete.setAdapter(adapter);
                autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView selectedItem_1 = (TextView) findViewById(R.id.eunitSelected_1);
                        TextView selectedItem_2 = (TextView) findViewById(R.id.eunitSelected_2);
                        String itemSelect = (String) parent.getItemAtPosition(position);

                        selectedItem_1.setText(" / " + itemSelect);
                        selectedItem_2.setText(" / " + itemSelect);

                        for (int i = 0; i < arrName.length; i++) {
                            if(itemSelect == arrName[i]){
                                setItemSelected(arrId[i]);
                            }
                        }
                    }


                });
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {

            }
        });
    }

    public void setItemSelected(Integer itemSelected) {
        this.itemSelected = itemSelected;
    }

    public int getItemSelected() {
        return this.itemSelected;
    }
}
