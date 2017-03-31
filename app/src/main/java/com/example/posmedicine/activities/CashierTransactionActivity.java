package com.example.posmedicine.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.posmedicine.Adapter.TransactionHeaderAdapter;
import com.example.posmedicine.Helper.EndlessRecyclerViewScrollListener;
import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato;
import com.example.posmedicine.models.CashierHeaderTransaction;
import com.example.posmedicine.models.response.CashierHeaderListResponse;
import com.example.posmedicine.models.response.CashierHeaderResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CashierTransactionActivity extends BaseActivity implements SearchView.OnQueryTextListener{
    ApiService service;
    ArrayList<CashierHeaderTransaction> transList;
    TransactionHeaderAdapter adapter;
    RecyclerView rvTransactionList;
    TextView loadMore;

    SearchView svKeyword;
    String keywordSearch = "";

    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_transaction);

        transList=new ArrayList();

        loadMore = (TextView)findViewById(R.id.loadMore);
        loadMore.setVisibility(View.GONE);

        getSupportActionBar().setTitle("Transaction");
        TextView_Lato subtitle = (TextView_Lato) findViewById(R.id.labelSubTitle);
        subtitle.setText("List Transaction");

        service = RestClient.getInstance().getApiService();
        //getTransactionHeader();
        getTransactionHeaderByPage(keywordSearch,10,0);

        rvTransactionList = (RecyclerView)findViewById(R.id.rvHeaderTransaction);
        LinearLayoutManager llm = new LinearLayoutManager(CashierTransactionActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rvTransactionList.setLayoutManager(llm);

        scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMore.setVisibility(View.VISIBLE);
                getTransactionHeaderByPage(keywordSearch,10,page);
                Log.d("loadmore","page Load more... "+page);
                Log.d("loadmore","totalItemsCount Load more... "+totalItemsCount);
            }
        };

        rvTransactionList.addOnScrollListener(scrollListener);

        svKeyword = (SearchView) findViewById(R.id.search_view);
        svKeyword.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                keywordSearch = query;
                transList.clear();
                getTransactionHeaderByPage(keywordSearch,10,0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ImageView closeButton = (ImageView)svKeyword.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");

                svKeyword.setQuery("", false);
                transList.clear();
                getTransactionHeaderByPage("", 10, 0);
            }
        });

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

    public void getTransactionHeaderByPage(String keywords, int limit, int page){
        service.getTransactionHeaderByPage(keywords, limit, page).enqueue(new Callback<CashierHeaderListResponse>() {
            @Override
            public void onResponse(Call<CashierHeaderListResponse> call, Response<CashierHeaderListResponse> response) {
                Log.d("transaksi data", String.valueOf(response.body().getData()));
                if(response.body().getData().size() > 0){
                    transList.addAll(response.body().getData());
                    adapter = new TransactionHeaderAdapter(transList,CashierTransactionActivity.this);
                    rvTransactionList.setAdapter(adapter);
                }
                loadMore.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CashierHeaderListResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
