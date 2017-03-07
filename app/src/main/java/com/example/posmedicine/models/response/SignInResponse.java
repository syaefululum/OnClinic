package com.example.posmedicine.models.response;
import com.example.posmedicine.models.SignIn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 03-Mar-17.
 */



public class SignInResponse {
        @SerializedName("status")
        @Expose
        private boolean status;
        @SerializedName("data")
        @Expose
        private SignIn signIn;
        @SerializedName("message")
        @Expose
        private String message;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public SignIn getSignIn() {
            return signIn;
        }

        public void setSignIn(SignIn data) {
            this.signIn = data;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
