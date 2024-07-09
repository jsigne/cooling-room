package com.example.cold_room.repository;

import com.example.cold_room.model.Cooling;
import com.example.cold_room.room.RoomTemperature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoolingRepository extends JpaRepository<Cooling, Integer> {

    @Query("SELECT c.idRoom as idRoom, c.temperature as temperature, c.messageDate as messageDate FROM Cooling c WHERE c.idRoom = :idRoom")
    List<RoomTemperature> avgTemp(Integer idRoom);
}
