package com.example.cold_room.room;

import com.example.cold_room.room.response.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/rooms")
    public List<Room> rooms(){
        return roomService.getRooms();
    }

    @GetMapping("/rooms/{idRoom}/temperature/average")
    public List<RoomTemperature> getDailyAverageTemperature(@PathVariable Integer idRoom){
        return roomService.dailyAverageTemperatureByIdRoom(idRoom);
    }

    @GetMapping("/rooms/{idRoom}/consumption/average")
    public List<RoomConsumption> getDailyAverageConsumption(@PathVariable Integer idRoom){
        return roomService.dailyAverageConsumptionByIdRoom(idRoom);
    }

}
