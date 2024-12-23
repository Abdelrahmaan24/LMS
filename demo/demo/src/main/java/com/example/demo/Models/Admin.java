package com.example.demo.Models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(Long id, String name, String email, String password, Role role) {
        super(id, email, password, name, role);
    }
}
