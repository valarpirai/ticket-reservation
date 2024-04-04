package com.bee.reservation.service;

import com.bee.reservation.api.ReservationServiceApi;
import com.bee.reservation.model.Reservation;
import com.bee.reservation.model.Schedule;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.repository.ReservationRepository;
import com.bee.reservation.repository.ScheduleRepository;
import com.bee.reservation.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
public class ReservationServiceImpl extends ReservationServiceApi {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TrainRepository trainRepository;

    public Reservation bookTicket(ReservationPojo reservationPojo) {
        var userPojo = reservationPojo.getUserPojo();
        Reservation reservation = new Reservation();
        reservation.setSchedule(findOrGetSchedule(reservationPojo));
        reservation.setUser(userService.findOrGetUserByEmail(userPojo));
        reservation.setBookedAt(LocalDateTime.from(Instant.now()));

//        Find train name by using from and To
//        Check tickets available for the train


        reservationRepository.save(reservation);
        return reservation;
    }

    public Reservation getTicketReservationDetails(Long reservationId) {
        return null;
    }

    Reservation changeAllotedUserSeat(Long userId, int seatId) {
        return null;
    }

    public void deleteReservation(Long reservationId) {

    }

    Schedule findOrGetSchedule(ReservationPojo reservationPojo) {

        return null;
    }
}
