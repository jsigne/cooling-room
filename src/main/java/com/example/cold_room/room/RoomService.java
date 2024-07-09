package com.example.cold_room.room;

import com.example.cold_room.repository.CoolingRepository;
import com.example.cold_room.room.response.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final CoolingRepository coolingRepository;


    public List<Room> getRooms(){
        return coolingRepository.getAllLastEntry();
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
