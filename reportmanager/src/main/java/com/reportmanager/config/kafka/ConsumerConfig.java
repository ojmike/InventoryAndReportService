package com.reportmanager.config.kafka;

import com.reportmanager.payload.request.OrderDetailsRequest;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableKafka
public class ConsumerConfig {

private final Environment environment;
    @Bean(name = "emailKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrderDetailsRequest> emailKafkaListenerContainerFactory(@Autowired RetryTemplate retryTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, OrderDetailsRequest> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(createConnectionFactoryMap("ordertemplate"),new StringDeserializer(),new JsonDeserializer<>(OrderDetailsRequest.class,false)));
        factory.setRetryTemplate(retryTemplate);
        return factory;
    }

    private Map<String,Object> createConnectionFactoryMap(String groupName){
        Map<String, Object> props = new HashMap<>();
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG,groupName);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "OrderDetailsRequest:com.reportmanager.payload.request.OrderDetailsRequest");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        return props;
    }


    @Bean
    public  RetryTemplate retryTemplate(){
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
