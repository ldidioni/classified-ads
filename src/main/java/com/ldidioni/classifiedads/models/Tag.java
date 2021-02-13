package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @NotEmpty(message = "*Please provide a name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags", cascade = CascadeType.PERSIST)
    private Set<Ad> ads = new HashSet<>();

    public Tag(@NotEmpty(message = "*Please provide a name") String name)
    {
        this.name = name;
    }

    public Tag() { }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Ad> getAds() {
        return ads;
    }

    public void setAds(Set<Ad> ads) {
        this.ads = ads;
    }

    public void linkAd(Ad ad) {
        ad.getTags().add(this);
        this.getAds().add(ad);
    }

    public void removeAd(Ad ad) {
        ad.getTags().remove(this);
        this.getAds().remove(ad);
    }
}
