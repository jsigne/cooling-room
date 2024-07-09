package com.example.cold_room.room;

import com.example.cold_room.repository.CoolingRepository;
import com.example.cold_room.room.response.Room;
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
    void getRooms_shouldReturnRoomLastData(){
        List<Room> expected = List.of(
                new Room(1, true, -35., 10., LocalDateTime.now(), null, true),
                new Room(2, true, -21., 10., LocalDateTime.now(), true, null)
        );
        when(repository.getAllLastEntry())
                .thenReturn(expected);

        List<Room> actual = roomService.getRooms();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void averageTemperatureByDay_shouldReturnAverageTemperature_whenExistingRoomId(){

        LocalDateTime day1 = LocalDateTime.now();
        LocalDateTime day2 = day1.minusDays(1);
        when(repository.getTemperaturesByIdRoom(1))
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

    @Test
    void averageConsumptionByDay_shouldReturnAverageConsumption_whenExistingRoomId(){

        LocalDateTime day1 = LocalDateTime.now();
        LocalDateTime day2 = day1.minusDays(1);
        when(repository.getConsumptionsByIdRoom(1))
                .thenReturn(List.of(
                        new RoomConsumption(1,10., day1),
                        new RoomConsumption(1, 6., day1),
                        new RoomConsumption(1, 12., day2)));

        List<RoomConsumption> expected = List.of(
                new RoomConsumption(1,8., day1),
                new RoomConsumption(1, 12., day2));

        List<RoomConsumption> actualConsumptionAverage = roomService.dailyAverageConsumptionByIdRoom(1);

        assertThat(actualConsumptionAverage).usingRecursiveComparison().isEqualTo(expected);
    }

}
