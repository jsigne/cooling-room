package com.example.cold_room.room;

import com.example.cold_room.repository.CoolingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;
    @Mock
    CoolingRepository repository;

    @Test
    void averageTemperatureByDay_shouldReturnAverageTemperature_whenExistingRoomId(){

        LocalDateTime day1 = LocalDateTime.now();
        LocalDateTime day2 = day1.minusDays(1);
        when(repository.getTemperaturesById(1))
                .thenReturn(List.of(
                        new RoomTemperature(1,-40., day1),
                        new RoomTemperature(1, -30., day1),
                        new RoomTemperature(1, -37., day2)));

        List<RoomTemperature> expected = List.of(
                new RoomTemperature(1,-35., day1),
                new RoomTemperature(1, -37., day2));

        List<RoomTemperature> actualTempAvgs = roomService.dailyAverageTemperatureByIdRoom(1);

        assertThat(actualTempAvgs).usingRecursiveComparison().isEqualTo(expected);
    }

}
