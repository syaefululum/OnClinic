package com.example.posmedicine.activities;

import android.content.ContextWrapper;
import android.content.Intent;

import com.orm.SugarApp;
import com.orm.SugarContext;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Syaeful_U1438 on 27-Feb-17.
 */

public class MyApplication extends SugarApp {
    public static final String TOKEN = "";
    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);

        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        String token = Prefs.getString(TOKEN,"TOKEN");
        Intent intent;
        if(token.equals("TOKEN")){
            intent = new Intent(this, LoginActivity.class);
        }else{
            intent = new Intent(this, MainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
