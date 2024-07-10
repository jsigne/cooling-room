package com.example.cold_room.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class RoomTemperature {
    private Integer idRoom;
    private Double temperature;
    private LocalDateTime day;
}
