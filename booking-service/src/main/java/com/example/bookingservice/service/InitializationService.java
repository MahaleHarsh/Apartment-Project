package com.example.bookingservice.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.bookingservice.model.Apartment;
import com.example.bookingservice.repository.ApartmentRepository; // Local repository in Booking Service
@Service
public class InitializationService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Retryable(
        value = RestClientException.class,
        maxAttempts = 5,
        backoff = @Backoff(delay = 5000)
    )
    public void initializeApartments() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://apartment-service:8084/apartment/list";

        Apartment[] apartments = restTemplate.getForObject(url, Apartment[].class);
        if (apartments != null) {
            apartmentRepository.saveAll(Arrays.asList(apartments));
        }
    }
}
