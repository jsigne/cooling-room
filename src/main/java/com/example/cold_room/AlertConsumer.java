package com.example.cold_room;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class AlertConsumer {
    @KafkaListener(topics= {"alert-topic", "warning-topic"}, groupId="spring-boot-kafka")
    public void consume(String alert) {
        System.out.println("received= " + alert);
    }
}
