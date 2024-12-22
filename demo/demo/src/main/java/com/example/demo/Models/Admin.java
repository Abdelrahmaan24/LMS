package com.example.demo.Models;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(Long id, String name, String email, String password, Role role) {
        super(name, email, password, id, role);
    }
}
