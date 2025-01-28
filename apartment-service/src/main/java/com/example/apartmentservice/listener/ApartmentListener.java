//package com.example.apartmentservice.listener;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import com.example.apartmentservice.model.Apartment;
//
//@Component
//public class ApartmentListener {
//
//	@RabbitListener(queues = "apartmentQueue")
//	public void handleApartmentEvent(String message) {
//	    try {
//	        System.out.println("Received message: " + message);
//	        if (message.startsWith("Apartment added")) {
//	            String apartmentId = message.split(":")[1].trim();
//	            // Logic to add/update the apartment locally
//	        } else if (message.startsWith("Apartment removed")) {
//	            String apartmentId = message.split(":")[1].trim();
//	            // Logic to remove the apartment locally
//	        }
//	    } catch (Exception e) {
//	        System.err.println("Failed to process Apartment event: " + message);
//	        e.printStackTrace();
//	    }
//	}
//
//}
//
//
