package com.example.searchservice.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.searchservice.model.Booking;
import com.example.searchservice.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookingEventListener {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "searchBookingQueue")
    public void handleBookingEvent(String message) {
        try {
          //  System.out.println("Received message: " + message);

            // Parse the JSON message
            EventMessage event = objectMapper.readValue(message, EventMessage.class);

            if ("BookingAdded".equals(event.getType())) {
                Booking booking = objectMapper.convertValue(event.getData(), Booking.class);
                bookingRepository.save(booking);
                System.out.println("Booking saved: " + booking);
            } else if ("BookingCancelled".equals(event.getType())) {
                String bookingId = event.getData().toString();
                bookingRepository.deleteById(bookingId);
                System.out.println("Booking removed with ID: " + bookingId);
            }
        } catch (Exception e) {
            System.err.println("Failed to process Booking event: " + message);
            e.printStackTrace();
        }
    }

    // Inner class for EventMessage
    static class EventMessage {
        private String type;
        private Object data;

        // Getters and setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}