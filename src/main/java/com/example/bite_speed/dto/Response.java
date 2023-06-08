package com.example.bite_speed.dto;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Response")
public class Response {

    @Id
    private Integer primaryContatctId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Integer> secondaryContactIds;
    public Integer getPrimaryContatctId() {
        return primaryContatctId;
    }
    public void setPrimaryContatctId(Integer primaryContatctId) {
        this.primaryContatctId = primaryContatctId;
    }
    public List<String> getEmails() {
        return emails;
    }
    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }
    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
    public List<Integer> getSecondaryContactIds() {
        return secondaryContactIds;
    }
    public void setSecondaryContactIds(List<Integer> secondaryContactIds) {
        this.secondaryContactIds = secondaryContactIds;
    }
    
}
