package com.example.cold_room.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RoomConsumption {
    private Integer idRoom;
    private Double consumption;
    private LocalDateTime day;
}

