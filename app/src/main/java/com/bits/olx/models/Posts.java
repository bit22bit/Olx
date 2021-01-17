package com.bits.olx.models;


public class Posts {
    private String heading;
    private String price;
    private String detail;

    public Posts(){}

    public Posts(String heading, String price, String detail) {
        this.heading = heading;
        this.price = price;
        this.detail = detail;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
