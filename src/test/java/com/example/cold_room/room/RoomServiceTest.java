package com.example.cold_room.room;

import com.example.cold_room.model.Cooling;
import com.example.cold_room.model.Room;
import com.example.cold_room.model.RoomConsumption;
import com.example.cold_room.model.RoomTemperature;
import com.example.cold_room.repository.CoolingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
    void getAlertRooms_shouldReturnRoomLastData() {
        // Given
        String instantExpected = "2022-03-14T09:33:52";
        LocalDateTime localDateTime = LocalDateTime.parse(instantExpected);

        List<Cooling> expected = List.of(
                new Cooling(1, 1, true, -35., 10., localDateTime),
                new Cooling(2, 1, true, -21., 10., localDateTime)
        );

        when(repository.getRoomInAlert()).thenReturn(List.of(1));


        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(localDateTime);

            when(repository.getAllLastEntryInAlert(List.of(1), localDateTime.minusHours(5)))
                    .thenReturn(expected);
            // When
            List<Cooling> actual = roomService.getAlertRooms();

            // Then
            assertThat(actual).isEqualTo(expected);
        }
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
    void averageConsumptionByMonth_shouldReturnAverageConsumption_whenExistingRoomId(){
        String instantExpected = "2022-03-14T09:33:52";
        LocalDateTime localDateTime = LocalDateTime.parse(instantExpected);

        LocalDateTime day1month1 = LocalDateTime.now().minusHours(2);
        LocalDateTime day2month1 = LocalDateTime.now().minusDays(2);
        LocalDateTime day1month2 = LocalDateTime.now().minusMonths(2);

        try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class)) {
            mockedLocalDateTime.when(LocalDateTime::now).thenReturn(localDateTime);

            when(repository.getAllConsumptionsSince(localDateTime.minusMonths(10)))
                    .thenReturn(List.of(
                            new RoomConsumption(1,10., day1month1),
                            new RoomConsumption(1, 30., day2month1),
                            new RoomConsumption(1, 6., day1month2),
                            new RoomConsumption(2, 12., day1month1),
                            new RoomConsumption(2, 14., day1month2)));

            List<RoomConsumption> expected = List.of(
                    new RoomConsumption(1,20., day1month1),
                    new RoomConsumption(1, 6., day1month2),
                    new RoomConsumption(2, 12., day1month1),
                    new RoomConsumption(2, 14., day1month2));

            List<RoomConsumption> actualConsumptionAverage = roomService.roomsMonthConsumptionAverage();

            assertThat(actualConsumptionAverage)
                    .containsAll(expected)
                    .hasSize(4);
        }
    }


}
