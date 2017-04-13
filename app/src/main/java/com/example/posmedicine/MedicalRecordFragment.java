package com.example.posmedicine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.Adapter.ComplaintDetailAdapter;
import com.example.posmedicine.Adapter.MedicalRecordDetailAdapter;
import com.example.posmedicine.Adapter.MedicineHistoryAdapter;
import com.example.posmedicine.activities.ComplaintDetailActivity;
import com.example.posmedicine.models.ComplaintHeader;
import com.example.posmedicine.models.response.ComplaintDetailsResponse;
import com.example.posmedicine.models.response.PrescriptionHeaderSingleResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Surya_N2267 on 3/29/2017.
 */

public class MedicalRecordFragment extends Fragment {
    private ComplaintHeader record;
    private ApiService service;
    private ViewGroup rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.list_row_patient_record, container, false);

        service = RestClient.getInstance().getApiService();
        record = (ComplaintHeader) getArguments().getSerializable("Obj");
        TextView date = (TextView) rootView.findViewById(R.id.tRecord_Date);
        getComplaintDetails(record.getId());
        getPrescriptionDetails(record.getId());
        date.setText(record.getRegisteredDate());
        return rootView;
    }

    public static MedicalRecordFragment newInstance(ComplaintHeader complaintHeader) {

        MedicalRecordFragment medicalRecordFragment = new MedicalRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("Obj", complaintHeader);

        medicalRecordFragment.setArguments(bundle);
        return medicalRecordFragment;
    }

    private void getComplaintDetails(Integer id) {
        service.getDetailbyId(id).enqueue(new Callback<ComplaintDetailsResponse>() {
            @Override
            public void onResponse(Call<ComplaintDetailsResponse> call, Response<ComplaintDetailsResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(rootView.getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                MedicalRecordDetailAdapter medicalRecordDetailAdapter = new MedicalRecordDetailAdapter(response.body().getComplaintDetail());
                RecyclerView rvMedicalRecord = (RecyclerView) rootView.findViewById(R.id.rvPatientMedicalRecord);

                rvMedicalRecord.setLayoutManager(llm);
                rvMedicalRecord.setAdapter(medicalRecordDetailAdapter);
            }

            @Override
            public void onFailure(Call<ComplaintDetailsResponse> call, Throwable t) {

            }
        });
    }

    private void getPrescriptionDetails(Integer complaintHeaderId){
        service.getPrescriptionByComplaint(complaintHeaderId).enqueue(new Callback<PrescriptionHeaderSingleResponse>() {
            @Override
            public void onResponse(Call<PrescriptionHeaderSingleResponse> call, Response<PrescriptionHeaderSingleResponse> response) {
                if(response.body().isStatus()){
                    if(response.body().getPrescriptionHeaders() != null){
                        Log.d("21gun", String.valueOf(response.body().getPrescriptionHeaders().getPrescriptionDetails()));
                        LinearLayoutManager llmm = new LinearLayoutManager(rootView.getContext());
                        llmm.setOrientation(LinearLayoutManager.VERTICAL);

                        MedicineHistoryAdapter medicineHistoryAdapter = new MedicineHistoryAdapter(response.body().getPrescriptionHeaders().getPrescriptionDetails());
                        RecyclerView rvMedicineHistory = (RecyclerView) rootView.findViewById(R.id.rvPatientMedicineHistory);

                        rvMedicineHistory.setLayoutManager(llmm);
                        rvMedicineHistory.setAdapter(medicineHistoryAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<PrescriptionHeaderSingleResponse> call, Throwable t) {

            }
        });
    }
}
