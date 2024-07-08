package com.example.cold_room.scanalert;

import com.example.cold_room.model.Alert;
import com.example.cold_room.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertConsumerTest {

    @InjectMocks
    private AlertConsumer alertConsumer;
    @Mock
    private AlertParser alertParser;
    @Mock
    private AlertRepository alertRepository;


    @Test
    void consumer_shouldSave_whenAlertMessage(){
        // Given
        String alertMessage = "Cooling(id=62460, idRoom=3, isCooling=false, temperature=-27.32, consumption=0.0, messageDate=null)";

        when(alertParser.parse(alertMessage)).thenReturn(new Alert());
        Alert expectedAlert = new Alert();
        expectedAlert.setIsAlert(true);

        // When
        alertConsumer.consumeAlert(alertMessage);

        // Then
        ArgumentCaptor<Alert> alertArgumentCaptor = ArgumentCaptor.forClass(Alert.class);
        verify(alertRepository).save(alertArgumentCaptor.capture());
        assertThat(alertArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(expectedAlert);
    }

}
