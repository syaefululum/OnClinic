package com.example.posmedicine.activities;

import android.os.Bundle;

import com.example.posmedicine.R;

public class MainActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("On Clinic");
    }
}
