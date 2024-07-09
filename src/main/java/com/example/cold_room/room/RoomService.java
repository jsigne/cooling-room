package com.example.cold_room.room;

import com.example.cold_room.repository.CoolingRepository;
import com.example.cold_room.room.response.RoomConsumptionResponse;
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

    public List<RoomTemperature> dailyAverageTemperatureByIdRoom(Integer id){
        List<RoomTemperature> roomTemperatures = coolingRepository.getTemperaturesById(id);

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

    public List<RoomConsumptionResponse> averageConsumptionByDay(Integer id){
        List<RoomConsumption> roomTemperatures = coolingRepository.consumptions(id);

        List<RoomTemperatureResponse> roomTemperatureAverageByDay = new ArrayList<>();
        roomTemperaturesByDay.forEach((day,temperaturesOfDay) ->
            temperaturesOfDay.stream()
                    .mapToDouble(RoomTemperature::getTemperature)
                    .average().ifPresent(avg -> new RoomTemperatureResponse(id, avg, day ))
        );

        return roomTemperatureAverageByDay;
    }
}
