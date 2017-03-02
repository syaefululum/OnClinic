package com.example.posmedicine;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.posmedicine.models.response.UnitResponse;
import com.example.posmedicine.models.response.UnitSingleDataResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUnitActivity extends AppCompatActivity {
    Button bCreateUnit, bCancel;
    EditText editTextUnitName;
    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);

        bCreateUnit = (Button)findViewById(R.id.bCreateUnit);
        bCancel = (Button)findViewById(R.id.bCancel);
        editTextUnitName = (EditText)findViewById(R.id.editTextUnitName);

        service = RestClient.getInstance().getApiService();

        bCreateUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String unitName = editTextUnitName.getText().toString().trim();
                if(unitName.isEmpty() || unitName.length() == 0 || unitName.equals("") || unitName == null){
                    Snackbar.make(v, "Please input unit name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    createNewUnit(unitName);
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

    public void createNewUnit(String unitName){

        service.createUnit(unitName).enqueue(new Callback<UnitSingleDataResponse>() {
            @Override
            public void onResponse(Call<UnitSingleDataResponse> call, Response<UnitSingleDataResponse> response) {
//                Intent mainActivity = new Intent(CreateUnitActivity.this,MainActivity.class);
//                CreateUnitActivity.this.startActivity(mainActivity);
                Toast toast = Toast.makeText(getApplicationContext(), "Create Success", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }

            @Override
            public void onFailure(Call<UnitSingleDataResponse> call, Throwable t) {
                finish();
            }
        });
    }
}
