package com.example.cold_room.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="cooling2")
public class Cooling {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idRoom;
    private Boolean isCooling;
    private Double temperature;
    private Double consumption;
    private LocalDateTime messageDate;
}
