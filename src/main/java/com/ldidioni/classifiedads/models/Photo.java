package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "photos")
public class Photo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column(name = "image_url")
    @NotEmpty(message = "*Please provide the URL of your image")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ad_id")
    private Ad ad;

    public Photo(@NotEmpty(message = "*Please provide the URL of your image") String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public Photo() { }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Photo))
            return false;
        return this.imageUrl != null && this.imageUrl == (((Photo) o).getImageUrl());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
