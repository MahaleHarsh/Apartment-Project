package com.example.bookingservice.controller;

import com.example.bookingservice.model.Booking;
import com.example.bookingservice.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @PostMapping("/add")
    public Booking addBooking(@RequestBody Booking booking) {
        return bookingService.addBooking(booking);
    }

    @GetMapping("/list")
    public List<Booking> listBookings() {
        return bookingService.listBookings();
    }

    @DeleteMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable String id) {
        bookingService.cancelBooking(id);
        return "Booking cancelled successfully.";
    }

    @PutMapping("/change/{id}")
    public Booking changeBooking(@PathVariable String id,
                                 @RequestParam String fromDate,
                                 @RequestParam String toDate) {
        try {
            Date parsedFromDate = dateFormat.parse(fromDate);
            Date parsedToDate = dateFormat.parse(toDate);

            return bookingService.changeBooking(id, parsedFromDate, parsedToDate);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Supported format: dd/MM/yyyy.");
        }
    }
}
