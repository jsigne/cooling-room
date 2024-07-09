package com.example.cold_room.repository;

import com.example.cold_room.model.Cooling;
import com.example.cold_room.room.RoomConsumption;
import com.example.cold_room.room.RoomTemperature;
import com.example.cold_room.room.response.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoolingRepository extends JpaRepository<Cooling, Integer> {

    @Query("SELECT new com.example.cold_room.room.RoomTemperature(c.idRoom, c.temperature, c.messageDate) FROM Cooling c WHERE c.idRoom = :idRoom")
    List<RoomTemperature> getTemperaturesByIdRoom(Integer idRoom);

    @Query("SELECT new com.example.cold_room.room.RoomConsumption(c.idRoom, c.consumption, c.messageDate) FROM Cooling c WHERE c.idRoom = :idRoom")
    List<RoomConsumption> getConsumptionsByIdRoom(Integer idRoom);

    @Query("""
            SELECT new com.example.cold_room.room.response.Room(cooling.idRoom, cooling.isCooling, cooling.temperature, cooling.consumption, cooling.messageDate, alert.isAlert, alert.isWarning)
            FROM Cooling cooling
            FULL JOIN Alert alert
            ON alert.idRoom = cooling.idRoom
            AND alert.alertDate = cooling.messageDate
            WHERE cooling.messageDate in
            (SELECT max(cooling.messageDate) FROM Cooling cooling GROUP BY cooling.idRoom)
            """)
    List<Room> getAllLastEntry();
}
