package com.example.searchservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("microservices-exchange", true, false);
    }

    @Bean
    public Queue searchApartmentQueue() {
        return new Queue("searchApartmentQueue", true); // Durable queue
    }

    @Bean
    public Queue searchBookingQueue() {
        return new Queue("searchBookingQueue", true); // Durable queue
    }

    @Bean
    public Binding searchApartmentBinding(Queue searchApartmentQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(searchApartmentQueue).to(topicExchange).with("apartment.#");
    }

    @Bean
    public Binding searchBookingBinding(Queue searchBookingQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(searchBookingQueue).to(topicExchange).with("booking.#");
    }
}
