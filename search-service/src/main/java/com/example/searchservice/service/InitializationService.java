package com.example.searchservice.service;

import com.example.searchservice.model.Apartment;
import com.example.searchservice.model.Booking;
import com.example.searchservice.repository.ApartmentRepository;
import com.example.searchservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class InitializationService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public void initializeApartments() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://apartment-service:8084/apartment/list"; // Correct Apartment Service URL
        System.out.println("Fetching.... apartments from: " + url);

        Apartment[] apartments = restTemplate.getForObject(url, Apartment[].class);
        System.out.println("Fetching apartments from: " + url);

        if (apartments != null) {
            apartmentRepository.saveAll(Arrays.asList(apartments));
            System.out.println("Initialized Apartments: " + apartments.length);
        }
    }

    public void initializeBookings() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://booking-service:8087/booking/list"; // Correct Booking Service URL

        Booking[] bookings = restTemplate.getForObject(url, Booking[].class);
        if (bookings != null) {
            bookingRepository.saveAll(Arrays.asList(bookings));
            System.out.println("Initialized Bookings: " + bookings.length);
        }
    }
}
