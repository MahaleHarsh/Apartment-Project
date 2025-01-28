package com.example.searchservice.service;

import com.example.searchservice.model.Apartment;
import com.example.searchservice.model.Booking;
import com.example.searchservice.repository.ApartmentRepository;
import com.example.searchservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Apartment> searchAvailableApartments(Date from, Date to) {
        List<Apartment> allApartments = apartmentRepository.findAll();

        return allApartments.stream()
                .filter(apartment -> {
                    List<Booking> bookings = bookingRepository.findByApartmentId(apartment.getId());
                    return bookings.stream().noneMatch(booking -> 
                        datesOverlap(from, to, booking.getFromDate(), booking.getToDate())
                    );
                })
                .collect(Collectors.toList());
    }

    private boolean datesOverlap(Date from1, Date to1, Date from2, Date to2) {
        return !(to1.before(from2) || from1.after(to2));
    }
}
