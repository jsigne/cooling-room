package com.example.cold_room.scanalert;

import com.example.cold_room.model.Alert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AlertParserTest {

    @InjectMocks
    private AlertParser alertParser;

    @Test
    void parse_shouldReturnAlert_whenMatchingString(){
        // Given
        LocalDateTime date = LocalDateTime.now();
        Alert expectedAlert = new Alert();
        expectedAlert.setIdRoom(3);
        expectedAlert.setTemperature(-17.62);
        expectedAlert.setAlertDate(date);


        // When
        Alert actualAlert = alertParser.parse("Cooling(id=64276, idRoom=3, isCooling=false, temperature=-17.62, consumption=0.0, messageDate=%s)".formatted(date.toString()));

        // Then
        assertThat(actualAlert).usingRecursiveComparison().isEqualTo(expectedAlert);
    }
}
