package com.example.cold_room.room.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Room {
    private Integer idRoom;
    private Boolean isCooling;
    private Double temperature;
    private Double consumption;
    private LocalDateTime messageDate;
    private Boolean isAlert;
    private Boolean isWarning;
}
