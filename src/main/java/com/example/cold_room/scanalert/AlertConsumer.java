package com.example.cold_room.scanalert;

import com.example.cold_room.model.Alert;
import com.example.cold_room.repository.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AlertConsumer {

    private final AlertRepository alertRepository;

    private final AlertParser alertParser;

    @KafkaListener(topics= {"alert-topic"}, groupId="spring-boot-kafka")
    public void consumeAlert(String alertMessage) {
        Alert alert = alertParser.parse(alertMessage);
        alert.setIsAlert(true);
        alertRepository.save(alert);
    }

    @KafkaListener(topics= {"warning-topic"}, groupId="spring-boot-kafka")
    public void consumeWarning(String alertMessage) {
        Alert alert = alertParser.parse(alertMessage);
        alert.setIsWarning(true);
        alertRepository.save(alert);
    }
}
