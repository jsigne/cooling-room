package com.example.cold_room.scanalert;

import com.example.cold_room.model.Alert;
import com.example.cold_room.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AlertParserTest {

    @InjectMocks
    private AlertParser alertParser;

    @Test
    void parse_shouldReturnAlert_whenMatchingString(){
        // Given
        Alert expectedAlert = new Alert();
        expectedAlert.setIdRoom(3);
        expectedAlert.setTemperature(-17.62);
        expectedAlert.setConsumption(0.0);

        // When
        Alert actualAlert = alertParser.parse("Cooling(id=64276, idRoom=3, isCooling=false, temperature=-17.62, consumption=0.0, messageDate=null)");

        // Then
        assertThat(actualAlert).usingRecursiveComparison().isEqualTo(expectedAlert);
    }
}
