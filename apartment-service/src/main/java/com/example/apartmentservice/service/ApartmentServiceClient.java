package com.example.apartmentservice.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApartmentServiceClient {
    public boolean isApartmentValid(String apartmentId) {
        try {
            // Make a REST call to the Apartment Service
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Boolean> response = restTemplate.getForEntity(
            	    "http://apartment-service:8084/apartment/validate/" + apartmentId, Boolean.class);
            return response.getBody();
        } catch (Exception e) {
            return false; // Treat any error as invalid
        }
    }
}
