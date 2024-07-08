package com.example.cold_room;

import com.example.cold_room.model.Cooling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoolingRepository extends JpaRepository<Cooling, Integer> {

}
