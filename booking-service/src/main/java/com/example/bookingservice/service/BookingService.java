package com.example.bookingservice.service;

import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.ApartmentRepository;
import com.example.bookingservice.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void publishEvent(String eventType, Object eventData) {
        try {
            String message = objectMapper.writeValueAsString(new EventMessage(eventType, eventData));
            rabbitTemplate.convertAndSend("microservices-exchange", "booking.#", message);
        } catch (Exception e) {
            System.err.println("Failed to publish event: " + eventType);
            e.printStackTrace();
        }
    }

    public Booking addBooking(Booking booking) {
        if (!apartmentRepository.existsById(booking.getApartmentId())) {
            throw new IllegalArgumentException("Invalid Apartment ID.");
        }

        if (booking.getFromDate() == null || booking.getToDate() == null) {
            throw new IllegalArgumentException("From date and To date cannot be null.");
        }

        if (booking.getFromDate().after(booking.getToDate())) {
            throw new IllegalArgumentException("Invalid date range: 'fromDate' must be earlier than or equal to 'toDate'.");
        }

        Booking savedBooking = bookingRepository.save(booking);
        publishEvent("BookingAdded", savedBooking);
        return savedBooking;
    }

    public void cancelBooking(String bookingId) {
        if (!bookingRepository.existsById(bookingId)) {
            throw new EntityNotFoundException("Booking not found");
        }
        bookingRepository.deleteById(bookingId);
        publishEvent("BookingCancelled", bookingId);
    }

    public Booking changeBooking(String id, Date fromDate, Date toDate) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException("From date and To date cannot be null.");
        }

        if (fromDate.after(toDate)) {
            throw new IllegalArgumentException("Invalid date range: 'fromDate' must be earlier than or equal to 'toDate'.");
        }

        booking.setFromDate(fromDate);
        booking.setToDate(toDate);

        Booking updatedBooking = bookingRepository.save(booking);
        publishEvent("BookingChanged", updatedBooking);
        return updatedBooking;
    }

    public List<Booking> listBookings() {
        return bookingRepository.findAll();
    }

    static class EventMessage {
        public String type;
        public Object data;

        public EventMessage(String type, Object data) {
            this.type = type;
            this.data = data;
        }
    }
}
