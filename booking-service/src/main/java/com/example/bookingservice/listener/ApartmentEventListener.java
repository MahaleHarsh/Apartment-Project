package com.example.bookingservice.listener;

import com.example.bookingservice.model.Apartment;
import com.example.bookingservice.repository.ApartmentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApartmentEventListener {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @RabbitListener(queues = "apartmentQueueBooking")
    public void handleApartmentEvent(String message) {
        try {
            System.out.println("Received message: " + message);

            if (message.startsWith("apartment.add")) {
                String apartmentId = message.split(":")[1].trim();
                Apartment apartment = new Apartment();
                apartment.setId(apartmentId);
                apartmentRepository.save(apartment);
                System.out.println("Apartment added locally with ID: " + apartmentId);
            } else if (message.startsWith("apartment.remove")) {
                String apartmentId = message.split(":")[1].trim();
                apartmentRepository.deleteById(apartmentId);
                System.out.println("Apartment removed locally with ID: " + apartmentId);
            }
        } catch (Exception e) {
            System.err.println("Failed to process Apartment event: " + message);
            e.printStackTrace();
        }
    }
}
