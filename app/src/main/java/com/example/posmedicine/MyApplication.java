package com.example.posmedicine;

import android.app.Application;

import com.orm.SugarApp;
import com.orm.SugarContext;

/**
 * Created by Syaeful_U1438 on 27-Feb-17.
 */

public class MyApplication extends SugarApp {
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }
}
