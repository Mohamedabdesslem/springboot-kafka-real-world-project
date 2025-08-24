package net.javaguides.springboot.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.consumer.name}")
    private String topicName;

    @Value("${spring.kafka.topic.producer.name}")
    private String topicNameUppercase;

    @Value("${spring.kafka.topic.order.name}")
    private String ordersTopic;

    @Value("${spring.kafka.topic.general_orders.name}")
    private String generalOrdersTopic;

    @Value("${spring.kafka.topic.restaurant_orders.name}")
    private String generalRestaurantTopic;

    @Value("${spring.kafka.topic.store.name}")
    private String storesTopic;

    @Value("${spring.kafka.topic.words.name}")
    private String wordsTopic;

    @Value("${spring.kafka.topic.aggregate.name}")
    private String aggregateTopic;

    @Value("${spring.kafka.topic.alphabets.name}")
    private String alphabetsTopic;

    @Value("${spring.kafka.topic.alphabets_abbrevations.name}")
    private String alphabetsAbbrevationsTopic;


    @Bean
    public NewTopic greetingsTopic (){
        return TopicBuilder.name(topicName).build();
    }

    @Bean
    public NewTopic greetingsUppercaseTopic (){
        return TopicBuilder.name(topicNameUppercase).build();
    }

    @Bean
    public NewTopic ordersTopic (){
        return TopicBuilder.name(ordersTopic).build();
    }


    @Bean
    public NewTopic storesTopic (){
        return TopicBuilder.name(storesTopic).build();
    }

    @Bean
    public NewTopic generalOrdersTopic (){
        return TopicBuilder.name(generalOrdersTopic).build();
    }

    @Bean
    public NewTopic restaurantOrdersTopic (){
        return TopicBuilder.name(generalRestaurantTopic).build();
    }


    @Bean
    public NewTopic aggregateTopic (){
        return TopicBuilder.name(aggregateTopic).build();
    }

    @Bean
    public NewTopic alphabetsTopic (){
        return TopicBuilder.name(alphabetsTopic).build();
    }


    @Bean
    public NewTopic alphabetsAbbrevationsTopic (){
        return TopicBuilder.name(alphabetsAbbrevationsTopic).build();
    }



}
