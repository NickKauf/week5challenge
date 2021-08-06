package com.example.week5challenge;

import javax.persistence.*;

@Entity
@Table(name = "role_table")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;

    public Role(){

    }

    public Role(String username, String role) {
        this.username = username;
        this.role = role;
    }
}