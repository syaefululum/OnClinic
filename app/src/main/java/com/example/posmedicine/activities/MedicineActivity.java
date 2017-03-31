package com.example.posmedicine.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.posmedicine.Adapter.MedicineAdapter;
import com.example.posmedicine.Helper.EndlessRecyclerViewScrollListener;
import com.example.posmedicine.R;
import com.example.posmedicine.TextView_Lato;
import com.example.posmedicine.models.Medicine;
import com.example.posmedicine.models.response.MedicineResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineActivity extends BaseActivity implements Parcelable, AbsListView.OnScrollListener, SearchView.OnQueryTextListener {

    ApiService service;
    private ArrayList<Medicine> medicineList;
    private MedicineAdapter adapter;
    RecyclerView rvMedicine;
    TextView loadMore;
    SearchView searchName;
    String keywordSearch = "";

    private EndlessRecyclerViewScrollListener scrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        getSupportActionBar().setTitle("Medicine");
        TextView_Lato subtitle = (TextView_Lato) findViewById(R.id.labelSubTitle);
        subtitle.setText("Medicine List");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(
                new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_plus_circle)
                        .color(Color.WHITE)
                        .actionBarSize());
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createMedicine = new Intent(MedicineActivity.this,CreateMedicineActivity.class);
                MedicineActivity.this.startActivity(createMedicine);
            }
        });

        Iconify.with(new FontAwesomeModule());
        medicineList=new ArrayList();

        loadMore = (TextView)findViewById(R.id.loadMore);
        loadMore.setVisibility(View.GONE);

        service = RestClient.getInstance().getApiService();
        initMedicine(0);
        rvMedicine = (RecyclerView)findViewById(R.id.rvMedicine);
        LinearLayoutManager llm = new LinearLayoutManager(MedicineActivity.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        rvMedicine.setLayoutManager(llm);

        scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                //getMedicine(10,page);
                loadMore.setVisibility(View.VISIBLE);
                //initMedicine(page);
                getMedicine(keywordSearch, 10, page);
                Log.d("loadmore","Load more... "+page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvMedicine.addOnScrollListener(scrollListener);

        searchName = (SearchView) findViewById(R.id.search_view);
        // perform set on query text listener event
        searchName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // do something on text submit
                Log.d("search query",query);
                keywordSearch = query;
                medicineList.clear();
                getMedicine(keywordSearch, 10, 0);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // do something when text changes
                Log.d("search on change",newText);
                return false;
            }
        });

        ImageView closeButton = (ImageView)searchName.findViewById(R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("clear button","clearrrr");
                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");

                //Clear query
                searchName.setQuery("", false);
                medicineList.clear();
                getMedicine("", 10, 0);
            }
        });
    }


    public void getMedicine(String keywords, int limit,int pages){
       service.getMedicineListSearch(keywords, limit, pages).enqueue(new Callback<MedicineResponse>() {
           @Override
           public void onResponse(Call<MedicineResponse> call, Response<MedicineResponse> response) {
               medicineList.addAll(response.body().getMedicine());
               adapter = new MedicineAdapter(medicineList,MedicineActivity.this);
               rvMedicine.setAdapter(adapter);
               loadMore.setVisibility(View.GONE);
           }

           @Override
           public void onFailure(Call<MedicineResponse> call, Throwable t) {
                Log.d("dasd","dasdd");
           }
       });
    }

    public void initMedicine(int pages){
        getMedicine("", 10, pages);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        Log.d("onresume","on resume proses");

        getMedicine("", 10, 0);
        super.onResume();
        //service = RestClient.getInstance().getApiService();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public MedicineActivity() {
    }

    protected MedicineActivity(Parcel in) {
    }

    public static final Parcelable.Creator<MedicineActivity> CREATOR = new Parcelable.Creator<MedicineActivity>() {
        @Override
        public MedicineActivity createFromParcel(Parcel source) {
            return new MedicineActivity(source);
        }

        @Override
        public MedicineActivity[] newArray(int size) {
            return new MedicineActivity[size];
        }
    };

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
       // Log.d("search query",query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
       // Log.d("search on change",newText);
        return false;
    }
}
