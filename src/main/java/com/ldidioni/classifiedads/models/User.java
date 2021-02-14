package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.ldidioni.classifiedads.repositories.RoleRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    @NotEmpty(message = "*Please provide your username")
    private String username;

    @Column(name = "password_hash")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column
    @Email(message = "*Please provide a valid e-mail address")
    @NotEmpty(message = "*Please provide an e-mail address")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column
    private double rating;

    @Column(name = "ratings_nb")
    private int ratingsNb;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seller") //TODO: check!!!
    private List<Ad> ads;

    public User(@NotEmpty(message = "*Please provide your username") String username,
                @Length(min = 5, message = "*Your password must have at least 5 characters")
                @NotEmpty(message = "*Please provide your password") String password,
                @Email(message = "*Please provide a valid e-mail address")
                @NotEmpty(message = "*Please provide an e-mail address") String email,
                String phoneNumber)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.rating = 0;
        this.ratingsNb = 0;
    }

    public User(@NotEmpty(message = "*Please provide your username") String username,
                @Length(min = 5, message = "*Your password must have at least 5 characters")
                @NotEmpty(message = "*Please provide your password") String password,
                @Email(message = "*Please provide a valid e-mail address")
                @NotEmpty(message = "*Please provide an e-mail address") String email)
    {
        this(username, password, email, null);
    }

    public User() {}

    /**
     * Function called every time a new rating is received to update the rating and the number of ratings received
     * @param latestRating
     */
    public void updateRating(double latestRating) {
        this.rating =  (this.rating  * this.ratingsNb + latestRating) / (this.ratingsNb + 1);
        this.ratingsNb = this.ratingsNb + 1;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingsNb() {
        return ratingsNb;
    }

    public void setRatingsNb(int ratings_nb) {
        this.ratingsNb = ratings_nb;
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
