package com.example.posmedicine.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.models.Doctor;
import com.example.posmedicine.models.response.AppointmentResponse;
import com.example.posmedicine.models.response.DoctorsResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.pixplicity.easyprefs.library.Prefs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAppointmentActivity extends AppCompatActivity implements Validator.ValidationListener{
    @Required(order = 1)
    AutoCompleteTextView autocompleteDoctor;

    @Required(order = 2, message = "Select Date")
    EditText iAppointmentDate;

    @Required(order = 3, message = "Select Time")
    EditText iAppointmentTime;

    Button bCreateAppointment;
    TextView bCancel, setDate, setTime;
    ApiService service;
    Validator validator;

    private Doctor selectedDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        service = RestClient.getInstance().getApiService();

        autocompleteDoctor = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextDoctor);

        setDate = (TextView)findViewById(R.id.setAppointmentDate);
        setTime = (TextView)findViewById(R.id.setAppointmentTime);
        bCreateAppointment = (Button)findViewById(R.id.bCreateAppointment);
        iAppointmentDate = (EditText)findViewById(R.id.tAppointmentDate);
        iAppointmentTime = (EditText)findViewById(R.id.tAppointmentTime);
        bCancel = (TextView)findViewById(R.id.bCancel);

        iAppointmentDate.setFocusable(false);
        iAppointmentDate.setClickable(false);
        iAppointmentTime.setFocusable(false);
        iAppointmentTime.setClickable(false);

        getDoctor();

        validator = new Validator(this);
        validator.setValidationListener(this);

        autocompleteDoctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedDoctor = (Doctor) parent.getAdapter().getItem(position);
            }
        });

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        bCreateAppointment.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public void onValidationSucceeded() {
        if(selectedDoctor == null){
            Toast.makeText(getApplicationContext(),"Please select Doctor",Toast.LENGTH_SHORT).show();
        }else{
            String date = iAppointmentDate.getText().toString() + " " + iAppointmentTime.getText().toString() + ":00";
            createAppointment(date,selectedDoctor.getId(), Integer.parseInt(Prefs.getString("USERID","1")));
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

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            TextView appointmentTime = (EditText) getActivity().findViewById(R.id.tAppointmentTime);
            appointmentTime.setText(hourOfDay+":"+minute);
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date d = sdf.parse(day + "/" + (month+1) + "/" + year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String formatted = format1.format(cal.getTime());

                TextView appointmentDate = (EditText) getActivity().findViewById(R.id.tAppointmentDate);
                appointmentDate.setText(formatted);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDoctor(){
        service.getDoctors().enqueue(new Callback<DoctorsResponse>() {
            @Override
            public void onResponse(Call<DoctorsResponse> call, Response<DoctorsResponse> response) {
                ArrayAdapter myAdapter = new ArrayAdapter<Doctor>(CreateAppointmentActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, response.body().getDoctor());
                autocompleteDoctor.setThreshold(2);
                autocompleteDoctor.setAdapter(myAdapter);
            }

            @Override
            public void onFailure(Call<DoctorsResponse> call, Throwable t) {

            }
        });
    }

    public void createAppointment(String date, Integer doctorId,Integer patientId){
        service.createAppointment(date,doctorId,patientId).enqueue(new Callback<AppointmentResponse>() {
            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                Log.d("responseCreate", String.valueOf(response.body().getAppointment()));
                if(response.body().getStatus()){
                    Toast.makeText(getBaseContext(), "Succesfully create appointment", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getBaseContext(), "Failed create appointment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Failed create appointment, Connection Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
