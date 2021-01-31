package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "ads")
public class Ad
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @NotEmpty(message = "*Please provide a title")
    private String title;

    @Column
    @NotEmpty(message = "*Please provide a description")
    private String description;

    @Column
    @NotEmpty(message = "*Please provide a description")
    private double asked_price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id")
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ad") //TODO: check!!!
    private List<Photo> photos;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ad") //TODO: check!!!
    private List<Tag> tags;
}
