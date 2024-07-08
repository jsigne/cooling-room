package com.example.cold_room.room;

import com.example.cold_room.model.Alert;
import com.example.cold_room.repository.AlertRepository;
import com.example.cold_room.repository.CoolingRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final CoolingRepository coolingRepository;

    public List<RoomTemperature> avergetemperture(Integer id){
        List<RoomTemperature> roomTemperatures = coolingRepository.avgTemp(id);
        return roomTemperatures;
    }
}
