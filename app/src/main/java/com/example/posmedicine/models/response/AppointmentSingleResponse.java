package com.example.posmedicine.models.response;

import com.example.posmedicine.models.Appointment;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Syaeful_U1438 on 01-Mar-17.
 */

public class AppointmentSingleResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private Appointment appointment;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setData(Appointment appointment) {
        this.appointment = appointment;
    }
}
