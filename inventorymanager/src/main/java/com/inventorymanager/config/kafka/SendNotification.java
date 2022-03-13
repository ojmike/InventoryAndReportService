package com.inventorymanager.config.kafka;

import com.inventorymanager.payload.response.OrderDetailsResponse;
import com.inventorymanager.payload.response.ProductOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

@Slf4j
@Service
public class SendNotification {


    @Autowired
    private KafkaTemplate<String, OrderDetailsResponse> emailMessageTemplateKafka;



    public void sendEmailMessages(OrderDetailsResponse orderDetailsResponse){
        if(!ObjectUtils.isEmpty(orderDetailsResponse)){
            try {

                emailMessageTemplateKafka.send("orderNow", orderDetailsResponse).addCallback(new ListenableFutureCallback<SendResult<String, OrderDetailsResponse>>() {
                    @Override
                    public void onSuccess(SendResult<String, OrderDetailsResponse> result) {
                        log.info("sent successfully");
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        log.error("Error",ex);
                    }
                });
            }catch(Exception err){
                log.error("Error",err);
            }
        }
    }
}
