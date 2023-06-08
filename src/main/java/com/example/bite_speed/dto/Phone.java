package com.example.bite_speed.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Phone {

    @Id
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "contactid", referencedColumnName = "id")
    private Contact contactid;
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Contact getContactid() {
        return contactid;
    }
    public void setContactid(Contact contactid) {
        this.contactid = contactid;
    }
    
    
}
