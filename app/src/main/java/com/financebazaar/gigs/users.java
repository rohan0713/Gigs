package com.financebazaar.gigs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class users {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("contact_id")
    @Expose
    private String contact_id;

    @SerializedName("phone_no")
    @Expose
    private String phone_no;

    @SerializedName("user_code")
    @Expose
    private String user_code;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("aadhar_no")
    @Expose
    private String aadhar_no;

    @SerializedName("pan_no")
    @Expose
    private String pan_no;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("referral_code")
    @Expose
    private String referral_code;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("token")
    @Expose
    private String token;

    public users(String phone_no, String name) {
        this.phone_no = phone_no;
        this.name = name;
    }

    public users(String id, String contact_id, String phone_no, String user_code, String name, String gender, String email, String address, String aadhar_no, String pan_no, String image, String referral_code, String dob, String token) {
        this.id = id;
        this.contact_id = contact_id;
        this.phone_no = phone_no;
        this.user_code = user_code;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.aadhar_no = aadhar_no;
        this.pan_no = pan_no;
        this.image = image;
        this.referral_code = referral_code;
        this.dob = dob;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhar_no() {
        return aadhar_no;
    }

    public void setAadhar_no(String aadhar_no) {
        this.aadhar_no = aadhar_no;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReferral_code() {
        return referral_code;
    }

    public void setReferral_code(String referral_code) {
        this.referral_code = referral_code;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "users{" +
                "id='" + id + '\'' +
                ", contact_id='" + contact_id + '\'' +
                ", phone_no='" + phone_no + '\'' +
                ", user_code='" + user_code + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", aadhar_no='" + aadhar_no + '\'' +
                ", pan_no='" + pan_no + '\'' +
                ", image='" + image + '\'' +
                ", referral_code='" + referral_code + '\'' +
                ", dob='" + dob + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
