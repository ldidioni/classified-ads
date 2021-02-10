package com.ldidioni.classifiedads.models;

import java.lang.reflect.Array;
import java.util.List;

public class AdForm {

    private Ad ad;
    private String[] photoUrls;

    public AdForm(Ad ad, String[] photoUrls) {
        this.ad = ad;
        this.photoUrls = photoUrls;
    }

    public AdForm(){
        this.ad = new Ad();
        this.photoUrls = new String[3];
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(String[] photoUrls) {
        this.photoUrls = photoUrls;
    }

}
