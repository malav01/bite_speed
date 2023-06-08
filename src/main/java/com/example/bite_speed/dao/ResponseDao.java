package com.example.bite_speed.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bite_speed.dto.Response;

public interface ResponseDao extends JpaRepository<Response, Integer> {
    
}
