package com.example.posmedicine.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.posmedicine.R;
import com.example.posmedicine.models.response.SignInResponse;
import com.example.posmedicine.network.ApiService;
import com.example.posmedicine.network.RestClient;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText iUsername, iPassword;
    TextView signin,message;

    ApiService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        service = RestClient.getInstance().getApiService();

        iUsername = (EditText)findViewById(R.id.iUsername);
        iPassword = (EditText)findViewById(R.id.iPassword);
        signin = (TextView)findViewById(R.id.bSingIn);
        message = (TextView)findViewById(R.id.messageText);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = iUsername.getText().toString();
                String password = iPassword.getText().toString();
                doSignIn(username,password);
                message.setText("");
                message.setPadding(0,0,0,0);
            }
        });


    }

    public void doSignIn(String username, String password){
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        service.signin(username,password).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                Log.d("userdata", String.valueOf(response.body()));

                if(response.body().isStatus()){
                    Prefs.putString("TOKEN",response.body().getSignIn().getToken());
                    Prefs.putString("USERID", String.valueOf(response.body().getSignIn().getId()));
                    Prefs.putString("USERNAME",response.body().getSignIn().getName());
                    Prefs.putString("USERROLE",response.body().getSignIn().getRole());

                    Intent mainActivity = new Intent(LoginActivity.this,MainActivity.class);
                    LoginActivity.this.startActivity(mainActivity);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Failed Login", Toast.LENGTH_SHORT);
                    toast.show();
                    message.setPadding(5,5,5,5);
                    message.setText(response.body().getMessage());
                }
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Log.d("userdata", String.valueOf(t));
                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });
    }
}
