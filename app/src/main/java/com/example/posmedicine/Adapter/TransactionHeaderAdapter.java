package com.example.posmedicine.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.CashierTransactionActivity;
import com.example.posmedicine.CashierTransactionDetailActivity;
import com.example.posmedicine.R;
import com.example.posmedicine.models.CashierHeaderTransaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syaeful_U1438 on 20-Feb-17.
 */

public class TransactionHeaderAdapter extends RecyclerView.Adapter<TransactionHeaderAdapter.ViewHolder>{
    private List<CashierHeaderTransaction> headerTransactions;
    private CashierTransactionActivity activity;

    public TransactionHeaderAdapter(List<CashierHeaderTransaction> headerAdapters, CashierTransactionActivity activity){
        this.headerTransactions = headerAdapters;
        this.activity = activity;
    }

    @Override
    public TransactionHeaderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_header_transaction,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TransactionHeaderAdapter.ViewHolder holder, final int position) {
        holder.headerID.setText(headerTransactions.get(position).getId());
        holder.headerDate.setText(headerTransactions.get(position).getDate());
        holder.headerTotalPrice.setText("Rp. " + NumberFormat.getInstance().format(Double.parseDouble(headerTransactions.get(position).getTotalPrice())));
        holder.cvHeaderTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = new Bundle();
                extras.putString("headerID",headerTransactions.get(position).getId());
                extras.putString("headerDate",headerTransactions.get(position).getDate());
                extras.putString("headerTotalPrice",headerTransactions.get(position).getTotalPrice());
               // extras.putParcelableArrayList("detailTransaction", (ArrayList<? extends Parcelable>) headerTransactions.get(position).getTransactionDetails());
                //new intent detail transaction
                Intent detailTransaction = new Intent(v.getContext(), CashierTransactionDetailActivity.class);
                detailTransaction.putParcelableArrayListExtra("detailTransaction",((ArrayList)headerTransactions.get(position).getTransactionDetails()));
                detailTransaction.putExtras(extras);
                v.getContext().startActivity(detailTransaction);
            }
        });
    }

    @Override
    public int getItemCount() {
        return headerTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cvHeaderTransaction;
        public TextView headerID;
        public TextView headerDate;
        public TextView headerTotalPrice;
        public ViewHolder(View v) {
            super(v);
            cvHeaderTransaction = (CardView)v.findViewById(R.id.cvHeaderTransaction);
            headerID = (TextView)v.findViewById(R.id.tHeaderid_val);
            headerDate = (TextView)v.findViewById(R.id.tHeader_date_val);
            headerTotalPrice = (TextView)v.findViewById(R.id.tHeader_total_val);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }
}
