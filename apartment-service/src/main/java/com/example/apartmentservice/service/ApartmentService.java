package com.example.apartmentservice.service;

import com.example.apartmentservice.model.Apartment;
import com.example.apartmentservice.repository.ApartmentRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApartmentService {
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String exchangeName = "microservices-exchange";

    public void publishEvent(String routingKey, String message) {
        try {
            rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
          //  System.out.println("Published message to RabbitMQ: " + message);
        } catch (Exception e) {
            System.err.println("Failed to publish message: " + message);
            e.printStackTrace();
        }
    }

    public Apartment addApartment(Apartment apartment) {
        Apartment savedApartment = apartmentRepository.save(apartment);
        publishEvent("apartment.add", "Apartment added: " + savedApartment.getId());
        return savedApartment;
    }

    public void removeApartment(String id) {
        apartmentRepository.deleteById(id);
        publishEvent("apartment.remove", "Apartment removed: " + id);
    }

    public List<Apartment> listApartments() {
        return apartmentRepository.findAll();
    }
}
