package com.reportmanager.config.kafka;


import com.reportmanager.payload.request.OrderDetailsRequest;
import com.reportmanager.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReceivingNotification {

    private final OrderService orderService;

    @KafkaListener(topics = "orderNow", groupId = "ordertemplate",containerFactory = "emailKafkaListenerContainerFactory")
    public void handleEmailTemplateMessage(OrderDetailsRequest orderDetailsRequest){
        log.info("Entered Consuming Network");
        try{
            log.info("Entered Consuming Network");
            if(!ObjectUtils.isEmpty(orderDetailsRequest)){
          orderService.saveOrder(orderDetailsRequest);
            }
        }catch(Exception err){
            log.error("Error",err);
        }
    }
}
