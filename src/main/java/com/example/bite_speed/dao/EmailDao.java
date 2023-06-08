package com.example.bite_speed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bite_speed.dto.Email;

public interface EmailDao extends JpaRepository<Email, String> {
    
}
