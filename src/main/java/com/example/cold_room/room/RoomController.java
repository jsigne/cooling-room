package com.example.cold_room.room;

import com.example.cold_room.model.Cooling;
import com.example.cold_room.model.Room;
import com.example.cold_room.model.RoomConsumption;
import com.example.cold_room.model.RoomTemperature;
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

    @GetMapping("/rooms/consumption/average/month")
    public List<RoomConsumption> roomsMonthConsumptionAverage(){
        return roomService.roomsMonthConsumptionAverage();
    }

    @GetMapping("/roomalert/last-data")
    public List<Cooling> roomAlert(){
        return roomService.getAlertRooms();
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
