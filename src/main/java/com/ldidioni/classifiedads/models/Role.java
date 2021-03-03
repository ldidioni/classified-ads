package com.ldidioni.classifiedads.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles", cascade = CascadeType.PERSIST)
    private Set<User> users = new HashSet<>();

    /*
    public void linkUser(User user) {
        user.getRoles().add(this);
        this.getUsers().add(user);
    }*/

    public Set<User> getUsers() { return users; }

    public void setUsers(Set<User> users) { this.users = users; }

    public Role(String name)
    {
        this.name = name;
    }

    public Role() { }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void removeUser(User user) {
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }
}
