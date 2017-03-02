package com.example.posmedicine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.posmedicine.Adapter.TransactionHeaderAdapter;
import com.example.posmedicine.models.CashierHeaderTransaction;
import com.example.posmedicine.models.response.CashierHeaderResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashierTransactionActivity extends AppCompatActivity {
    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_transaction);

        service = RestClient.getInstance().getApiService();
        getTransactionHeader();
    }

    public void getTransactionHeader() {
        service.getTransactionHeader().enqueue(new Callback<CashierHeaderResponse>() {
            @Override
            public void onResponse(Call<CashierHeaderResponse> call, Response<CashierHeaderResponse> response) {
                LinearLayoutManager llm = new LinearLayoutManager(CashierTransactionActivity.this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                TransactionHeaderAdapter transactionHeader = new TransactionHeaderAdapter(response.body().getHeaderTransaction(), CashierTransactionActivity.this);
                RecyclerView rvTransactionHeader = (RecyclerView)findViewById(R.id.rvHeaderTransaction);

                rvTransactionHeader.setLayoutManager(llm);
                rvTransactionHeader.setAdapter(transactionHeader);
            }

            @Override
            public void onFailure(Call<CashierHeaderResponse> call, Throwable t) {

            }
        });
    }
}
