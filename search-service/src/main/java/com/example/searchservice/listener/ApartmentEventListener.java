package com.example.searchservice.listener;

import com.example.searchservice.model.Apartment;
import com.example.searchservice.repository.ApartmentRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApartmentEventListener {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @RabbitListener(queues = "apartmentQueueSearch")
    public void handleApartmentEvent(String message) {
        try {
            System.out.println("Received Apartment Event: " + message);

            if (message.startsWith("Apartment added:")) {
                String apartmentId = message.replace("Apartment added:", "").trim();

                if (!apartmentId.isEmpty()) {
                    Apartment apartment = new Apartment();
                    apartment.setId(apartmentId);
                    apartmentRepository.save(apartment);
                    System.out.println("Apartment saved: " + apartment);
                }
            } else if (message.startsWith("Apartment removed:")) {
                String apartmentId = message.replace("Apartment removed:", "").trim();

                if (!apartmentId.isEmpty()) {
                    apartmentRepository.deleteById(apartmentId);
                    System.out.println("Apartment removed: " + apartmentId);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to process Apartment event: " + message);
            e.printStackTrace();
        }
    }
}
