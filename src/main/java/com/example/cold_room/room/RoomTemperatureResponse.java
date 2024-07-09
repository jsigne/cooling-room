package com.example.cold_room.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RoomTemperatureResponse {
    private Integer idRoom;
    private Double temperature;
    private LocalDate date;
}
