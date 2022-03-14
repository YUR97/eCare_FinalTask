package ru.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOCKCLIENT")
public class LockClient {

    @Id
    @Column(name = "email")
    private String email;

    public LockClient() {
    }

    public LockClient(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
