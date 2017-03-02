package com.example.posmedicine;

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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.posmedicine.models.Appointment;
import com.example.posmedicine.models.local.LocalAppointment;
import com.example.posmedicine.models.response.AppointmentResponse;
import com.example.posmedicine.models.response.AppointmentSingleResponse;
import com.example.posmedicine.models.response.DoctorResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAppointmentActivity extends AppCompatActivity {
    Button setDate, setTime, bCreateAppointment;
    AutoCompleteTextView autocomplete;
    TextView iAppointmentDate,iAppointmentTime;
    String[] arrName;
    Long[] arrId;
    ApiService service;


    private Long docterSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        service = RestClient.getInstance().getApiService();
        getDoctor();

        setDate = (Button)findViewById(R.id.setAppointmentDate);
        setTime = (Button)findViewById(R.id.setAppointmentTime);
        bCreateAppointment = (Button)findViewById(R.id.bCreateAppointment);
        iAppointmentDate = (TextView)findViewById(R.id.tAppointmentDate);
        iAppointmentTime = (TextView)findViewById(R.id.tAppointmentTime);

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
                Long doctorId = getDocterSelected();
                Integer patientId = 1;
                String status = "Pending";
                String date = iAppointmentDate.getText().toString() + " " + iAppointmentTime.getText().toString() + ":00";
                createAppointment(date,doctorId,patientId,status);
            }
        });
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
            TextView appointmentTime = (TextView) getActivity().findViewById(R.id.tAppointmentTime);
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
                Date d = sdf.parse(day + "/" + month + "/" + year);
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                String formatted = format1.format(cal.getTime());

                TextView appointmentDate = (TextView) getActivity().findViewById(R.id.tAppointmentDate);
                appointmentDate.setText(formatted);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDoctor(){
        service.getDoctor().enqueue(new Callback<DoctorResponse>() {
            @Override
            public void onResponse(Call<DoctorResponse> call, Response<DoctorResponse> response) {
                arrName = new String[response.body().getDoctor().size()];
                arrId = new Long[response.body().getDoctor().size()];
                for (int i = 0; i < response.body().getDoctor().size(); i++) {
                    arrName[i] = new String(response.body().getDoctor().get(i).getPerson().getName());
                    arrId[i] = response.body().getDoctor().get(i).getId();
                }
                autocomplete = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextDoctor);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.select_dialog_item, arrName);

                autocomplete.setThreshold(3);
                autocomplete.setAdapter(adapter);

                autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String itemSelect = (String) parent.getItemAtPosition(position);

                        for (int i = 0; i < arrName.length; i++) {
                            if(itemSelect == arrName[i]){
                                setDocterSelected(arrId[i]);
                            }
                        }
                    }
                });
            }


            @Override
            public void onFailure(Call<DoctorResponse> call, Throwable t) {

            }
        });
    }

    public void setDocterSelected(Long docterSelected) {
        this.docterSelected = docterSelected;
    }

    public Long getDocterSelected() {
        return this.docterSelected;
    }

    public void createAppointment(String date, Long doctorId,Integer patientId,String status){
        service.createAppointment(date,doctorId,patientId,status).enqueue(new Callback<AppointmentSingleResponse>() {
            @Override
            public void onResponse(Call<AppointmentSingleResponse> call, Response<AppointmentSingleResponse> response) {
//                Appointment appData = response.body().getAppointment();
//                appData.save();
                finish();
            }

            @Override
            public void onFailure(Call<AppointmentSingleResponse> call, Throwable t) {
                Log.d("asd","gagal");
            }
        });

//       LocalAppointment appData = new LocalAppointment(patientId,doctorId,1,"Surya","Bruce",date,"Pending");
//        appData.save();


    }
}
