package com.example.posmedicine.models.local;

import com.orm.SugarApp;
import com.orm.SugarRecord;

/**
 * Created by Syaeful_U1438 on 28-Feb-17.
 */

public class LocalAppointment extends SugarRecord{

    Integer userId,doctorId,synchronize;
    String doctorName, patientName,date;
    public String status;

    public LocalAppointment(){

    }

    public LocalAppointment(Integer userId, Integer doctorId,Integer synchronize, String doctorName, String patientName, String date, String status){
        this.userId = userId;
        this.doctorId = doctorId;
        this.synchronize = synchronize;
        this.doctorName = doctorName;
        this.patientName = patientName;
        this.date = date;
        this.status = status;
    }

    public String getDoctorName(){
        return this.doctorName;
    }

    public String getPatientName(){
        return this.patientName;
    }

    public String getDate(){
        return this.date;
    }

    public String getStatus(){
        return this.status;
    }
}
