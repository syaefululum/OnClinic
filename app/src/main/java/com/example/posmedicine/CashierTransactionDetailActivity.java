package com.example.posmedicine;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.posmedicine.Adapter.TransactionDetailAdapter;
import com.example.posmedicine.models.CashierDetailTransaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CashierTransactionDetailActivity extends AppCompatActivity {
    String headerId, headerDate, headerTotalPrice;
    ArrayList<CashierDetailTransaction> transactionDetail;

    TextView vHeaderId, vHeaderDate, vTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_transaction_detail);

        Bundle extras = getIntent().getExtras();
        headerId = extras.getString("headerID");
        headerDate = extras.getString("headerDate");
        headerTotalPrice = extras.getString("headerTotalPrice");
        transactionDetail = extras.getParcelableArrayList("detailTransaction");

        vHeaderId = (TextView)findViewById(R.id.tHeaderid_val);
        vHeaderDate = (TextView)findViewById(R.id.tHeader_date_val);
        vTotalPrice = (TextView)findViewById(R.id.tHeader_total_val);

        vHeaderId.setText(headerId);
        vHeaderDate.setText(headerDate);
        vTotalPrice.setText("Rp. " + NumberFormat.getInstance().format(Double.parseDouble(headerTotalPrice)));

        LinearLayoutManager llm = new LinearLayoutManager(CashierTransactionDetailActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        TransactionDetailAdapter detailAdapter = new TransactionDetailAdapter(transactionDetail,CashierTransactionDetailActivity.this);
        RecyclerView rvTransactionDetail = (RecyclerView)findViewById(R.id.rvDetailTransaction);

        rvTransactionDetail.setLayoutManager(llm);
        rvTransactionDetail.setAdapter(detailAdapter);
    }
}
