package com.example.posmedicine.network;


import com.example.posmedicine.models.PrescriptionHeader;
import com.example.posmedicine.models.response.AppointmentsResponse;
import com.example.posmedicine.models.response.AppointmentResponse;
import com.example.posmedicine.models.response.CashierHeaderListResponse;
import com.example.posmedicine.models.response.CashierHeaderResponse;
import com.example.posmedicine.models.response.ComplaintDetailsResponse;
import com.example.posmedicine.models.response.ComplaintHeaderResponse;
import com.example.posmedicine.models.response.ComplaintHeadersResponse;
import com.example.posmedicine.models.response.DoctorsResponse;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.models.response.MedicineSingleResponse;
import com.example.posmedicine.models.response.PatientResponse;
import com.example.posmedicine.models.response.PatientsResponse;
import com.example.posmedicine.models.response.PrescriptionDetailResponse;
import com.example.posmedicine.models.response.PrescriptionHeaderResponse;
import com.example.posmedicine.models.response.PrescriptionHeaderSingleResponse;
import com.example.posmedicine.models.response.PurchaseDetailResponse;
import com.example.posmedicine.models.response.PurchaseHeaderResponse;
import com.example.posmedicine.models.response.ServicesResponse;
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

    @GET("/clinic/web/v1/unit/view")
    Call<UnitSingleDataResponse> getUnitById(
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

    @GET("/clinic/web/v1/medicine/search")
    Call<MedicineResponse> getMedicineListSearch(
            @Query("keywords") String keywords,
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
    Call<MedicineSingleResponse> updateMedicine(
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
    Call<MedicineSingleResponse> deleteMedicine(
            @Query("id") int id
    );


    /**
     * Appointment
     * Created by Surya on 02/06/17.
     */
    @GET("/clinic/web/v2/appointment/find-by-doctor")
    Call<AppointmentsResponse> getAppointmentbyDoctor(
            @Query("id") Integer id
    );

    @GET("/clinic/web/v2/appointment/find-by-patient")
    Call<AppointmentsResponse> getAppointmentbyPatient(
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

    @GET("/clinic/web/v1/purchase-header/list")
    Call<CashierHeaderListResponse> getTransactionHeaderByPage(
            @Query("keywords") String keywords,
            @Query("limit") int limit,
            @Query("page") int page
    );

    @FormUrlEncoded
    @POST("/clinic/web/v1/purchase-detail/create")
    Call<PurchaseDetailResponse> createPurchaseDetail(
            @Field("purchase_header_id") Integer headerId,
            @Field("medicine_id") Integer medicineId,
            @Field("quantity") int quantity,
            @Field("unit_id") Integer unitId,
            @Field("price") String price,
            @Field("total_price") String totalPrice
    );

    @FormUrlEncoded
    @POST("/clinic/web/v2/appointment/create")
    Call<AppointmentResponse> createAppointment(
            @Field("date") String appointmentDate,
            @Field("doctor_id") Integer doctorid,
            @Field("patient_id") Integer patientid
    );

    /**
     * Login
     * Created by Surya on 03/06/17.
     */
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
    Call<ComplaintHeadersResponse> getListComplaints();

    @GET("/clinic/web/v1/complaint-header/list")
    Call<ComplaintHeadersResponse> getComplaints();

    @GET("/clinic/web/v1/complaint-header/find-by-patient")
    Call<ComplaintHeadersResponse> getFindByPatient(
            @Query("id") Integer id
    );

    @FormUrlEncoded
    @POST("/clinic/web/v1/complaint-header/create")
    Call<ComplaintHeaderResponse> postComplaint(
            @Field("registered_date") String date,
            @Field("patient_id") Integer patientid,
            @Field("description") String description
    );

    @GET("/clinic/web/v1/complaint-detail/find-by-id")
    Call<ComplaintDetailsResponse> getDetailbyId(
            @Query("id") Integer id
    );

    @GET("/clinic/web/v1/complaint-detail/find-by-doctor")
    Call<ComplaintDetailsResponse> getTreatments(
            @Query("id") Integer id
    );

    @FormUrlEncoded
    @POST("/clinic/web/v1/complaint-detail/create")
    Call<ComplaintHeaderResponse> postComplaintDetail(
            @Field("complaint_header_id") Integer complaintHeaderId,
            @Field("doctor_id") Integer doctorId,
            @Field("service_id") Integer serviceId,
            @Field("time") String description
    );

    @FormUrlEncoded
    @PUT("/clinic/web/v1/complaint-detail/treatment")
    Call<ComplaintHeaderResponse> putTreatment(
            @Query("id") Integer id,
            @Field("result") Integer result,
            @Field("description") String description
    );

    @FormUrlEncoded
    @PUT("/clinic/web/v1/complaint-detail/update")
    Call<ComplaintHeaderResponse> putComplaintDetail(
            @Query("id") Integer id,
            @Field("result") String result,
            @Field("description") String description
    );

    @FormUrlEncoded
    @PUT("/clinic/web/v1/complaint-detail/treatment")
    Call<ComplaintHeaderResponse> putTreatment(
            @Query("id") Integer id,
            @Field("result") String result,
            @Field("description") String description
    );

    /**
     * Doctor
     * Created by Surya on 03/15/17.
     */
    @GET("/clinic/web/v2/doctor")
    Call<DoctorsResponse> getDoctors();

    /**
     * Patient
     * Created by Surya on 03/15/17.
     */
    @GET("/clinic/web/v1/patient")
    Call<PatientsResponse> getPatients();

    @GET("/clinic/web/v1/patient/view")
    Call<PatientResponse> getPatient(
            @Query("id") Integer id
    );

    /**
     * Service
     * Created by Surya on 03/17/17.
     */
    @GET("/clinic/web/v1/service")
    Call<ServicesResponse> getServices();

    @FormUrlEncoded
    @PUT("/clinic/web/v2/appointment/status")
    Call<AppointmentResponse> updateAppointmentStatus(
            @Query("id") int id,
            @Field("status") String status
    );

    @GET("/clinic/web/v2/appointment/find-by-today")
    Call<AppointmentsResponse> getAppointmentByNurse( );

    /**
     * prescription-header/create
     * Created by Ulum on 10/04/17.
     */
    @FormUrlEncoded
    @POST("/clinic/web/v1/prescription-header/create")
    Call<PrescriptionHeaderSingleResponse> createPrescriptionHeader(
            @Field("complaint_id") Integer complainId,
            @Field("date") String date
    );

    /**
     * prescription-detail/create
     * Created by Ulum on 10/04/17.
     */
    @FormUrlEncoded
    @POST("/clinic/web/v1/prescription-detail/create")
    Call<PrescriptionDetailResponse> createPrescriptionDetail(
            @Field("prescription_header_id") Integer prescriptionHeaderId,
            @Field("medicine_name") String medicineName,
            @Field("quantity") String quantity,
            @Field("unit_id") Integer unitId,
            @Field("note") String note
    );

    @GET("/clinic/web/v1/prescription-header/find-by-complaint")
    Call<PrescriptionHeaderSingleResponse> getPrescriptionByComplaint(
            @Query("id") Integer id
    );

    @DELETE("/clinic/web/v1/prescription-detail/delete")
    Call<PrescriptionDetailResponse> deletePrescriptionDetail(
            @Query("id") int id
    );
}
