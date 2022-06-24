package com.financebazaar.gigs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class userResponse {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("token")
    @Expose
    String token;

    public userResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "userResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
