package com.example.cold_room.repository;

import com.example.cold_room.model.Cooling;
import com.example.cold_room.model.RoomConsumption;
import com.example.cold_room.model.RoomTemperature;
import com.example.cold_room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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

    @Query("""
            SELECT cooling.idRoom FROM Cooling cooling
            INNER JOIN Alert alert
            ON alert.idRoom = cooling.idRoom
            AND alert.alertDate = cooling.messageDate
            WHERE cooling.messageDate in
            (SELECT max(cooling.messageDate) FROM Cooling cooling GROUP BY cooling.idRoom)
            """)
    List<Integer> getRoomInAlert();

    @Query("""
            SELECT cooling FROM Cooling cooling
            WHERE cooling.idRoom IN (:alertRoomIds)
            AND cooling.messageDate > :maxTimestamp
            """)
    List<Cooling> getAllLastEntryInAlert(List<Integer> alertRoomIds, LocalDateTime maxTimestamp);
}
