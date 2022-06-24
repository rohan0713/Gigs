package com.financebazaar.gigs;

public class gigs_detail {

    int image;
    String gig_name;
    String details;
    String offer;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getGig_name() {
        return gig_name;
    }

    public void setGig_name(String gig_name) {
        this.gig_name = gig_name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public gigs_detail(int image, String gig_name, String details, String offer) {
        this.image = image;
        this.gig_name = gig_name;
        this.details = details;
        this.offer = offer;
    }
}
