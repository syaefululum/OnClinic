package com.example.posmedicine.models.response;

/**
 * Created by Surya_N2267 on 2/6/2017.
 */

import java.util.List;

import com.example.posmedicine.models.Appointment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

public class AppointmentResponse{
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private List<Appointment> appointment = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Appointment> getAppointment() {
        return appointment;
    }

    public void setData(List<Appointment> appointment) {
        this.appointment = appointment;
    }
}
