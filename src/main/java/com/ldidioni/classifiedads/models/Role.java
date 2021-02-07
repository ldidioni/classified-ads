package com.ldidioni.classifiedads.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int id;

    @Column
    private String role;

    public Role(String role)
    {
        this.role = role;
    }

    public Role() { }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
