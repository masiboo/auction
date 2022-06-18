package com.cognizant.core.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppMessageQueueConfig {
    @Value("${auction-queue}")
    public String QUEUE;
    @Value("${auction-exchange}")
    public String EXCHANGE;
    @Value("${auction_routingKey}")
    public  String ROUTING_KEY;

    @Bean(name = "${auction-queue}")
    public Queue queue() {
        return  new Queue(QUEUE);
    }

    @Bean(name = "${auction-exchange}")
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean(name = "${auction_binding}")
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean(name = "${auction_messageConverter")
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean(name = "${auction_template}")
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }

}
