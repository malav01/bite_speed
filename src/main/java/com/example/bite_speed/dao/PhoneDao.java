package com.example.bite_speed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bite_speed.dto.Phone;

public interface PhoneDao extends JpaRepository<Phone, String> {
    
}
