package com.example.bite_speed.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Email {

    @Id
    private String email;

    @ManyToOne
    @JoinColumn(name = "contactid", referencedColumnName = "id")
    private Contact contactid;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact getContactid() {
        return contactid;
    }

    public void setContactid(Contact contactid) {
        this.contactid = contactid;
    }
    
}
