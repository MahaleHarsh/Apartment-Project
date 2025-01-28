package com.example.bookingservice.config;

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
    public Queue bookingQueue() {
        return new Queue("bookingQueue", true); // Durable queue
    }

    @Bean
    public Binding bookingAddBinding(Queue bookingQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(bookingQueue).to(topicExchange).with("booking.add");
    }

    @Bean
    public Binding bookingRemoveBinding(Queue bookingQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(bookingQueue).to(topicExchange).with("booking.remove");
    }
}
