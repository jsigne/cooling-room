package com.example.cold_room.room;

import com.example.cold_room.model.Cooling;
import com.example.cold_room.model.Room;
import com.example.cold_room.model.RoomConsumption;
import com.example.cold_room.model.RoomTemperature;
import com.example.cold_room.repository.CoolingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class RoomService {

    public static final int MAX_HOURS = 5;
    public static final int MAX_MONTHS = 10;
    private final CoolingRepository coolingRepository;

    public List<Room> getRooms(){
        return coolingRepository.getAllLastEntry();
    }

    public List<RoomConsumption> roomsMonthConsumptionAverage(){
        LocalDateTime maxTimestamp = LocalDateTime.now().minusMonths(MAX_MONTHS);
        List<RoomConsumption> consumptionAverageByRoomByMonth = new ArrayList<>();

        List<RoomConsumption> allConsumptions = coolingRepository.getAllConsumptionsSince(maxTimestamp);

        Map<Integer, List<RoomConsumption>> ConsumptionsByRoom = allConsumptions.stream()
                .filter(roomConsumption-> roomConsumption.getDay() != null && roomConsumption.getConsumption() != null)
                .collect(Collectors.groupingBy(RoomConsumption::getIdRoom));

        ConsumptionsByRoom.forEach((idRoom,byRoomConsumptions) -> {
            Map <LocalDate, List < RoomConsumption >> consumptionByRoomByMonth = byRoomConsumptions.stream()
                    .collect(groupingBy(c -> c.getDay().with(TemporalAdjusters.firstDayOfMonth()).toLocalDate()));

            consumptionByRoomByMonth.forEach((month, consumptionsRoom) ->
                consumptionsRoom.stream()
                        .mapToDouble(RoomConsumption::getConsumption)
                        .average().ifPresent(avg -> consumptionAverageByRoomByMonth.add(new RoomConsumption(idRoom, avg, consumptionsRoom.getFirst().getDay())))
            );
        });

        return consumptionAverageByRoomByMonth;
    }

    public List<Cooling> getAlertRooms(){
        LocalDateTime maxTimestamp = LocalDateTime.now().minusHours(MAX_HOURS);
        List<Integer> alertRoomIds = coolingRepository.getRoomInAlert();
        return coolingRepository.getAllLastEntryInAlert(alertRoomIds, maxTimestamp);
    }

    public List<RoomTemperature> dailyAverageTemperatureByIdRoom(Integer id){
        List<RoomTemperature> roomTemperatures = coolingRepository.getTemperaturesByIdRoom(id);

        Map<LocalDate, List<RoomTemperature>> roomTemperaturesByDay = roomTemperatures.stream()
                .filter(roomTemperature-> roomTemperature.getDay() != null && roomTemperature.getTemperature() != null)
                .collect(groupingBy(r -> r.getDay().toLocalDate()));

        List<RoomTemperature> roomTemperatureAverageByDay = new ArrayList<>();
        roomTemperaturesByDay.forEach((day,temperaturesOfDay) ->
            temperaturesOfDay.stream()
                    .mapToDouble(RoomTemperature::getTemperature)
                    .average().ifPresent(avg -> roomTemperatureAverageByDay.add(new RoomTemperature(id, avg, temperaturesOfDay.getFirst().getDay())))
        );

        return roomTemperatureAverageByDay;
    }

    public List<RoomConsumption> dailyAverageConsumptionByIdRoom(Integer id){
        List<RoomConsumption> roomTemperatures = coolingRepository.getConsumptionsByIdRoom(id);

        Map<LocalDate, List<RoomConsumption>> roomConsumptionByDay = roomTemperatures.stream()
                .filter(consumption-> consumption.getDay() != null && consumption.getConsumption() != null)
                .collect(groupingBy(r -> r.getDay().toLocalDate()));

        List<RoomConsumption> roomConsumptionAverageByDay = new ArrayList<>();
        roomConsumptionByDay.forEach((day,consumptionsOfDay) ->
            consumptionsOfDay.stream()
                    .mapToDouble(RoomConsumption::getConsumption)
                    .average().ifPresent(avg -> roomConsumptionAverageByDay.add(new RoomConsumption(id, avg, consumptionsOfDay.getFirst().getDay())))
        );

        return roomConsumptionAverageByDay;
    }
}
