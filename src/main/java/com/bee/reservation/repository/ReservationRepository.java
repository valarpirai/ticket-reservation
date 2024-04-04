package com.bee.reservation.repository;

import com.bee.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    int countByDateAndScheduleId(LocalDate date, Long scheduleId);
    List<Reservation> findReservationsByDateAndScheduleId(LocalDate date, Long scheduleId);
}
