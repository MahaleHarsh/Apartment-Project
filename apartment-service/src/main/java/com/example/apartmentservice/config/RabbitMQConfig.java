package com.example.apartmentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("microservices-exchange", true, false);
    }

    @Bean
    public Queue apartmentQueueBooking() {
        return QueueBuilder.durable("apartmentQueueBooking").build();
    }

    @Bean
    public Queue apartmentQueueSearch() {
        return QueueBuilder.durable("apartmentQueueSearch").build();
    }

    @Bean
    public Binding bookingBinding(Queue apartmentQueueBooking, TopicExchange topicExchange) {
        return BindingBuilder.bind(apartmentQueueBooking).to(topicExchange).with("apartment.add");
    }

    @Bean
    public Binding searchBinding(Queue apartmentQueueSearch, TopicExchange topicExchange) {
        return BindingBuilder.bind(apartmentQueueSearch).to(topicExchange).with("apartment.add");
    }

    @Bean
    public Binding removeBinding(Queue apartmentQueueBooking, TopicExchange topicExchange) {
        return BindingBuilder.bind(apartmentQueueBooking).to(topicExchange).with("apartment.remove");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // ✅ Correct way to initialize queues AFTER Spring context is fully started
    @Bean
    public ApplicationRunner rabbitInitializer(RabbitAdmin rabbitAdmin) {
        return args -> {
            rabbitAdmin.initialize(); // This will declare all beans automatically
            System.out.println("✅ RabbitMQ Queues and Exchange declared successfully");
        };
    }
}
