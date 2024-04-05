package com.bee.reservation.repository;

import com.bee.reservation.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Set<Schedule> findByFromStationAndToStation(String fromStation, String toStation);
}
