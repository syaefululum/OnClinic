package com.example.posmedicine.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.posmedicine.CashierTransactionActivity;
import com.example.posmedicine.CashierTransactionDetailActivity;
import com.example.posmedicine.R;
import com.example.posmedicine.models.CashierDetailTransaction;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Syaeful_U1438 on 21-Feb-17.
 */

public class TransactionDetailAdapter extends RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder> {
    private List<CashierDetailTransaction> detailTransactions;
    private CashierTransactionDetailActivity activity;

    public TransactionDetailAdapter(List<CashierDetailTransaction> detailTransactions, CashierTransactionDetailActivity activity){
        this.detailTransactions = detailTransactions;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_detail_transaction,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.medicineName.setText(detailTransactions.get(position).getMedicine().getName());
        holder.quantity.setText(detailTransactions.get(position).getQuantity());
        holder.medicinePrice.setText("Rp. " + NumberFormat.getInstance().format(Double.parseDouble(detailTransactions.get(position).getMedicine().getPrice())));

        holder.totalPrice.setText("Rp. " + NumberFormat.getInstance().format(Double.parseDouble(detailTransactions.get(position).getTotalPrice())));
    }

    @Override
    public int getItemCount() {
        return detailTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cvDetail;
        public TextView medicineName;
        public TextView quantity;
        public TextView medicinePrice;
        public TextView totalPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            cvDetail = (CardView)itemView.findViewById(R.id.cvDetailTransaction);
            medicineName = (TextView) itemView.findViewById(R.id.vDetail_name);
            quantity = (TextView) itemView.findViewById(R.id.vDetail_qty);
            medicinePrice = (TextView) itemView.findViewById(R.id.vDetail_price);
            totalPrice = (TextView) itemView.findViewById(R.id.vDetail_total);
        }
    }
}
