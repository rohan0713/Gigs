package com.financebazaar.gigs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class tickets {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ticket_id")
    @Expose
    private String ticket_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("short_note")
    @Expose
    private String short_note;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image")
    @Expose
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(String ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_note() {
        return short_note;
    }

    public void setShort_note(String short_note) {
        this.short_note = short_note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public tickets(String id, String ticket_id, String title, String short_note, String date, String time, String location, String status, String image) {
        this.id = id;
        this.ticket_id = ticket_id;
        this.title = title;
        this.short_note = short_note;
        this.date = date;
        this.time = time;
        this.location = location;
        this.status = status;
        this.image = image;
    }
}
