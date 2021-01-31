package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    @NotEmpty(message = "*Please provide a name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category") //TODO: check!!!
    private List<Ad> ads;
}
