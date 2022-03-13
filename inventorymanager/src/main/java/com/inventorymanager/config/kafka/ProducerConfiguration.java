package com.inventorymanager.config.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventorymanager.payload.response.OrderDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class ProducerConfiguration {

    private final Environment environment;


    @Bean
    public KafkaTemplate<String, OrderDetailsResponse> emailMessageTemplateKafka(@Autowired @Qualifier("jacksonObjectMapper") ObjectMapper objectMapper) {
        TypeReference<OrderDetailsResponse> ref = new TypeReference<OrderDetailsResponse>(){};
        ProducerFactory<String,OrderDetailsResponse> producerFactory = new DefaultKafkaProducerFactory<String,OrderDetailsResponse>(generateMapFactory("ordertemplate")
                ,new StringSerializer(),new JsonSerializer(ref,objectMapper));
        return new KafkaTemplate<String,OrderDetailsResponse>(producerFactory);
    }

    public Map<String, Object> generateMapFactory(String groupName) {
        Map<String,Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        configProps.put(JsonSerializer.TYPE_MAPPINGS, "OrderDetailsResponse:com.inventorymanager.payload.response.OrderDetailsResponse");
        return configProps;
    }

}
