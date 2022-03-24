package com.example.gigs;

public class offers {

    int image;
    String price;
    String task;

    public offers(int image, String price, String task) {
        this.image = image;
        this.price = price;
        this.task = task;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
