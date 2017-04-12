package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.posmedicine.R;
import com.example.posmedicine.models.response.ComplaintHeaderResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.joanzapata.iconify.widget.IconTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTreatmentActivity extends AppCompatActivity {
    private ApiService service;

    private EditText result;
    private EditText description;
    private Integer header_id;
    private AwesomeValidation awesomeValidation;
    private IconTextView submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_treatment);Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.header_id = Integer.parseInt(getIntent().getStringExtra("DETAIL_ID"));
        }

        submitButton = (IconTextView) findViewById(R.id.treatmentSubmit);
        result = (EditText) findViewById(R.id.treatmentResult);
        description = (EditText) findViewById(R.id.treatmentDescription);

        service = RestClient.getInstance().getApiService();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.treatmentResult, "^[a-zA-Z][a-zA-Z0-9!@#$&()\\\\:-`.+,/\n \\\"]*$", R.string.result_error);
        awesomeValidation.addValidation(this, R.id.treatmentDescription, "^[a-zA-Z][a-zA-Z0-9!@#$&()\\\\:-`.+,/\n \\\"]*$", R.string.result_error);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateComplaint();
            }
        });
    }

    protected void updateComplaint() {
        if (awesomeValidation.validate()) {
            service.putTreatment(header_id, result.getText().toString(), description.getText().toString())
                    .enqueue(new Callback<ComplaintHeaderResponse>() {
                        @Override
                        public void onResponse(Call<ComplaintHeaderResponse> call, Response<ComplaintHeaderResponse> response) {
//                Appointment appData = response.body().getAppointment();
//                appData.save();
                            if (response.body().getStatus()) {
                                Toast.makeText(EditTreatmentActivity.this, "Data Created Successfully", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(EditTreatmentActivity.this, "Data Failed to save : 404", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ComplaintHeaderResponse> call, Throwable t) {
                            Log.d("LOG", "Failed");
                        }
                    });
        }
    }
}
