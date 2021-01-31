package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @NotEmpty(message = "*Please provide your username")
    private String username;

    @Column
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password_hash;

    @Column
    @Email(message = "*Please provide a valid e-mail address")
    @NotEmpty(message = "*Please provide an e-mail address")
    private String email_address;

    @Column
    private String phone_number;

    @Column
    private double rating;

    @Column
    private int ratings_nb;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id")) //TODO: invert join & inverseJoin? https://www.baeldung.com/spring-data-rest-relationships
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller") //TODO: check!!!
    private List<Ad> ads;

    /**
     * Function called every time a new rating is received to update the rating and the number of ratings received
     * @param latestRating
     */
    public void updateRating(double latestRating) {
        this.rating =  (this.rating  * this.ratings_nb + latestRating) / (this.ratings_nb + 1);
        this.ratings_nb = this.ratings_nb + 1;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatings_nb() {
        return ratings_nb;
    }

    public void setRatings_nb(int ratings_nb) {
        this.ratings_nb = ratings_nb;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }
}
