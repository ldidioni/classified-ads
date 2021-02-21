package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ads")
public class Ad
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    @NotEmpty(message = "*Please provide a title")
    private String title;

    @Column
    @NotEmpty(message = "*Please provide a description")
    private String description;

    @Column(name = "asked_price")
    @NotNull(message = "*Please provide a price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ad", cascade = CascadeType.ALL) //TODO: check!!!
    private List<Photo> photos;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "ads_tags", joinColumns = @JoinColumn(name = "ad_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags  = new HashSet<>();

    public Ad(@NotEmpty(message = "*Please provide a title") String title,
              @NotEmpty(message = "*Please provide a description") String description,
              @NotNull(message = "*Please provide a price") double price,
              User seller,
              Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.category = category;
    }

    // No seller
    public Ad(@NotEmpty(message = "*Please provide a title") String title,
              @NotEmpty(message = "*Please provide a description") String description,
              @NotNull(message = "*Please provide a price") double price,
              Category category) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public Ad() {}

    public Ad(int id,
              @NotEmpty(message = "*Please provide a title") String title,
              @NotEmpty(message = "*Please provide a description") String description,
              @NotNull(message = "*Please provide a price") double price,
              User seller,
              Category category,
              List<Photo> photos,
              Set<Tag> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.category = category;
        this.photos = photos;
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() { return price; }

    public void setPrice(double price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Photo> getPhotos() { return photos; }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
        photo.setAd(this);
    }

    public void removePhoto(Photo photo) {
        photos.remove(photo);
        photo.setAd(null);
    }
}
