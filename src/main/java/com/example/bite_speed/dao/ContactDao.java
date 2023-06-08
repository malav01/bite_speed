package com.example.bite_speed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bite_speed.dto.Contact;

public interface ContactDao extends JpaRepository<Contact, Integer> {
    
}
