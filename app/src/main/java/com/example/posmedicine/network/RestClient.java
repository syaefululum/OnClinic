package com.example.posmedicine.network;

import android.provider.SyncStateContract;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Syaeful_U1438 on 01/27/17.
 */

public class RestClient {
    private ApiService apiService;
    private static RestClient client = null;
    private Retrofit retrofit;
    private Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create();

    public static RestClient getInstance() {
        if (client == null) {
            client = new RestClient();
        }
        return client;
    }

    public RestClient() {
        //localhot http://172.19.11.20:8080
        //surya's pc http://172.19.1.168:8080
        retrofit = new Retrofit.Builder().baseUrl("http://172.19.11.168:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttp().build())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private OkHttpClient.Builder getOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("retrofit", message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(interceptor);

        return builder;
    }

    public ApiService getApiService() {
        return apiService;
    }
}
