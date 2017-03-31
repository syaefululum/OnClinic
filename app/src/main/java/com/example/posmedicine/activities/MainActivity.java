package com.example.posmedicine.activities;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.posmedicine.R;
import com.pixplicity.easyprefs.library.Prefs;

public class MainActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("On Clinic");

    }
}
