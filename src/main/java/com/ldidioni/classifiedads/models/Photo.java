package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "photos")
public class Photo
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @NotEmpty(message = "*Please provide the URL of your image")
    private String image_url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ad_id")
    private Ad ad;
}
