package com.example.cold_room.processor;

import com.example.cold_room.model.Cooling;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CoolingParserTest {

    @InjectMocks
    CoolingParser coolingParser;

    @Test
    void parse_shouldReturnCooling_whenValidString(){
        Cooling expectedCooling = new Cooling();
        expectedCooling.setTemperature(-41.20);
        expectedCooling.setIdRoom(1);
        expectedCooling.setIsCooling(true);
        expectedCooling.setConsumption(14.94);
       Cooling actualCooling = coolingParser.parse("temperature:-41.20,idRoom:1,isCooling:true,consumption:14.94");
       assertThat(actualCooling).usingRecursiveComparison().isEqualTo(expectedCooling);
    }
}
