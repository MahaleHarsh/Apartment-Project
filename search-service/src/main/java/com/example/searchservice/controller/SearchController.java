package com.example.searchservice.controller;

import com.example.searchservice.model.Apartment;
import com.example.searchservice.model.Booking;
import com.example.searchservice.repository.ApartmentRepository;
import com.example.searchservice.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SearchController {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/search")
    public List<Apartment> searchAvailableApartments(@RequestParam String fromDate, @RequestParam String toDate) {
        try {
            // Parse and normalize the input dates
            Date from = parseDate(fromDate);
            Date to = parseDate(toDate);

            // Fetch all apartments
            List<Apartment> allApartments = apartmentRepository.findAll();

            // Filter apartments based on availability
            return allApartments.stream()
                    .filter(apartment -> {
                        List<Booking> bookings = bookingRepository.findByApartmentId(apartment.getId());
                        return bookings.stream().noneMatch(booking -> 
                            datesOverlap(from, to, booking.getFromDate(), booking.getToDate())
                        );
                    })
                    .collect(Collectors.toList());

        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use 'dd/MM/yyyy'.", e);
        }
    }

    // Helper method to parse dates in 'dd/MM/yyyy' format
    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false); // Ensure strict parsing
        return format.parse(date);
    }

    // Helper method to check if two date ranges overlap
    private boolean datesOverlap(Date from1, Date to1, Date from2, Date to2) {
        return !(to1.before(from2) || from1.after(to2));
    }
}
