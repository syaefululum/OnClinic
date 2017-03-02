package com.example.posmedicine;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.interfaces.UnitActions;
import com.example.posmedicine.models.response.UnitResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUnitActivity extends AppCompatActivity {
    Button bUpdateUnit, bCancel;
    TextView updateUnitName;
    ApiService service;
    int unitId;
    UnitActions unitActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_unit);

        Bundle extras = getIntent().getExtras();
        unitId = extras.getInt("id");
        String unitName = extras.getString("unitName");
        unitActions = extras.getParcelable("interface");
        service = RestClient.getInstance().getApiService();

        updateUnitName = (TextView)findViewById(R.id.editUnitName);
        updateUnitName.setText(unitName);

        bUpdateUnit = (Button)findViewById(R.id.bUpdateUnit);
        bCancel = (Button)findViewById(R.id.bCancel);


        bUpdateUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("unit id",unitId+"");
                String unitName = updateUnitName.getText().toString().trim();
                if(unitName.isEmpty() || unitName.length() == 0 || unitName.equals("") || unitName == null){
                    Snackbar.make(v, "Please input unit name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }else{
                    updateUnitName(unitId,unitName);
                   // unitActions.editUnit();
                    finish();

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

    public void updateUnitName(int unitId, String unitName){
        service.updateUnit(unitId, unitName).enqueue(new Callback<UnitResponse>() {
            @Override
            public void onResponse(Call<UnitResponse> call, Response<UnitResponse> response) {
                Toast toast = Toast.makeText(getApplicationContext(), "Update Success", Toast.LENGTH_SHORT);
                toast.show();
                finish();
//                response.body().getStatus();
                unitActions.editUnit();
            }

            @Override
            public void onFailure(Call<UnitResponse> call, Throwable t) {

            }
        });
    }
}
