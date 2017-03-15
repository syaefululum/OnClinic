package com.example.posmedicine.network;


import com.example.posmedicine.models.CashierHeaderTransaction;
import com.example.posmedicine.models.ComplaintHeader;
import com.example.posmedicine.models.response.AppointmentResponse;
import com.example.posmedicine.models.response.AppointmentSingleResponse;
import com.example.posmedicine.models.response.CashierHeaderResponse;
import com.example.posmedicine.models.response.ComplaintDetailResponse;
import com.example.posmedicine.models.response.ComplaintHeaderResponse;
import com.example.posmedicine.models.response.DoctorResponse;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.PurchaseDetailResponse;
import com.example.posmedicine.models.response.PurchaseHeaderResponse;
import com.example.posmedicine.models.response.SignInResponse;
import com.example.posmedicine.models.response.UnitResponse;
import com.example.posmedicine.models.response.UnitSingleDataResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Syaeful_U1438 on 01/27/17.
 */

public interface ApiService {
    /**
     * Unit
     * Created by Syaeful_U1438 on 01/27/17.
     */
    @GET("/clinic/web/v1/unit")
    Call<UnitResponse> getUnits();

    @FormUrlEncoded
    @GET("/clinic/web/v1/unit/view")
    Call<UnitResponse> getUnitById(
            @Query("id") int id
    );

    @FormUrlEncoded
    @POST("/clinic/web/v1/unit/create")
    Call<UnitSingleDataResponse> createUnit(
            @Field("name") String name
    );

    @FormUrlEncoded
    @PUT("/clinic/web/v1/unit/update")
    Call<UnitResponse> updateUnit(
            @Query("id") int id,
            @Field("name") String name
    );

    @DELETE("/clinic/web/v1/unit/delete")
    Call<UnitResponse> deleteUnit(
            @Query("id") int id
    );

    /**
     * Medicine
     * Created by Syaeful_U1438 on 01/27/17.
     */

    @GET("/clinic/web/v1/medicine")
    Call<MedicineResponse> getMedicine();

    @GET("/clinic/web/v1/medicine/list")
    Call<MedicineResponse> getMedicineList(
            @Query("limit") int limit,
            @Query("page") int page
    );

    @FormUrlEncoded
    @POST("/clinic/web/v1/medicine/create")
    Call<MedicineResponse> createMedicine(
            @Field("name") String name,
            @Field("quantity") String quantity,
            @Field("unit_id") Integer unitId,
            @Field("price") String price,
            @Field("type") String type,
            @Field("date_stock") String dateStock,
            @Field("date_expiration") String dateExpiration
    );

    @FormUrlEncoded
    @PUT("/clinic/web/v1/medicine/update")
    Call<MedicineResponse> updateMedicine(
            @Query("id") int id,
            @Field("name") String name,
            @Field("quantity") String quantity,
            @Field("unit_id") Integer unitId,
            @Field("price") String price,
            @Field("type") String type,
            @Field("date_stock") String dateStock,
            @Field("date_expiration") String dateExpiration
    );

    @DELETE("/clinic/web/v1/medicine/delete")
    Call<MedicineResponse> deleteMedicine(
            @Query("id") int id
    );


    /**
     * Appointment
     * Created by Surya on 02/06/17.
     */
    @GET("/clinic/web/v1/appointment/find-by-doctor")
    Call<AppointmentResponse> getAppointmentbyDoctor(
            @Query("id") Integer id
    );
    @GET("/clinic/web/v1/appointment/find-by-patient")
    Call<AppointmentResponse> getAppointmentbyPatient(
            @Query("id") Integer id
    );

    @FormUrlEncoded
    @POST("/clinic/web/v1/purchase-header/create")
    Call<PurchaseHeaderResponse> createPurchaseHeader(
            @Field("date") String date,
            @Field("total_price") String totalPrice,
            @Field("paid") String paid
    );

    @GET("/clinic/web/v1/purchase-header")
    Call<CashierHeaderResponse> getTransactionHeader();

    @FormUrlEncoded
    @POST("/clinic/web/v1/purchase-detail/create")
    Call<PurchaseDetailResponse> createPurchaseDetail(
            @Field("purchase_headerid") Integer headerId,
            @Field("medicine_id") Integer medicineId,
            @Field("quantity") int quantity,
            @Field("unit_id") Integer unitId,
            @Field("price") String price,
            @Field("total_price") String totalPrice
    );

    @GET("/clinic/web/v1/doctor")
    Call<DoctorResponse> getDoctor();

    @FormUrlEncoded
    @POST("/clinic/web/v1/appointment/create")
    Call<AppointmentSingleResponse> createAppointment(
            @Field("date") String appointmentDate,
            @Field("doctor_id") Integer doctorid,
            @Field("patient_id") Integer patientid,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("/clinic/web/v1/user-login/login")
    Call<SignInResponse> signin(
            @Field("username") String username,
            @Field("password") String password
    );
    /**
     * Complaint
     * Created by Surya on 03/06/17.
     */
    @GET("/clinic/web/v1/complaint-header")
    Call<ComplaintHeaderResponse> getComplaints();


    @GET("/clinic/web/v1/complaint-header/find-by-doctor")
    Call<ComplaintHeaderResponse> getComplaintsbyDoctor(
            @Query("id") Integer id
    );

    @GET("/clinic/web/v1/complaint-detail/detail-by-id")
    Call<ComplaintDetailResponse> getDetailbyId(
            @Query("id") Integer id
    );
}
