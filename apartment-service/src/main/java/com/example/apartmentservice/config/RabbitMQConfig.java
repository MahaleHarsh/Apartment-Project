package com.example.apartmentservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
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
        return new Queue("apartmentQueueBooking", true);
    }

    @Bean
    public Queue apartmentQueueSearch() {
        return new Queue("apartmentQueueSearch", true);
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
}
