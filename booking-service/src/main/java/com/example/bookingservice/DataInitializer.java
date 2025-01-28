package com.example.bookingservice;

import com.example.bookingservice.service.InitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class DataInitializer {
    @Autowired
    private InitializationService initializationService;

    @PostConstruct
    public void init() {
        initializationService.initializeApartments();
    }
}
