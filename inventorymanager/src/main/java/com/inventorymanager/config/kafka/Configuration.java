package com.inventorymanager.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@RequiredArgsConstructor
@org.springframework.context.annotation.Configuration
@EnableKafka
public class Configuration {

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("orderNow")
                .build();
    }

}
