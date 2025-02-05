package org.example.model;

import java.sql.Date;
import java.time.LocalDate;

public class Customer extends Person {
    private int customerId;
    private Date birthDate;
    private String name;
    private String mail;
    private String password;

    public Customer(String name, LocalDate birthDate, String mail, String password) {
        super(name, mail);
        this.name = name;
        this.birthDate = Date.valueOf(birthDate);
        this.mail = mail;
        this.password = password;
    }

    public Customer() {
        super(null, null);
    }

    // Getters and Setters
    public int getId() {
        return customerId;
    }

    public void setId(int customerId) {
        this.customerId = customerId;
    }

    public String getName () {
        return this.name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public String getBirthDate() {
        return birthDate.toString();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = Date.valueOf(birthDate);
    }

    public String getEmail() {
        return mail;
    }

    public void setEmail(String email) {
        this.mail = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

