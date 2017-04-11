package com.example.posmedicine.models;

/**
 * Created by Surya_N2267 on 2/6/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

public class Appointment implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("doctor_id")
    @Expose
    private int doctorId;
    @SerializedName("doctor_name")
    @Expose
    private String doctorName;
    @SerializedName("patient_id")
    @Expose
    private int patientId;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("status")
    @Expose
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.date);
        dest.writeInt(this.doctorId);
        dest.writeString(this.doctorName);
        dest.writeInt(this.patientId);
        dest.writeString(this.patientName);
        dest.writeString(this.status);
    }

    public Appointment() {
    }

    protected Appointment(Parcel in) {
        this.id = in.readInt();
        this.date = in.readString();
        this.doctorId = in.readInt();
        this.doctorName = in.readString();
        this.patientId = in.readInt();
        this.patientName = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Appointment> CREATOR = new Parcelable.Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}

