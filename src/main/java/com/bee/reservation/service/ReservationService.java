package com.bee.reservation.service;

import com.bee.reservation.model.Reservation;
import com.bee.reservation.model.Schedule;
import com.bee.reservation.model.User;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.pojo.UserPojo;
import com.bee.reservation.repository.ReservationRepository;
import com.bee.reservation.repository.ScheduleRepository;
import com.bee.reservation.repository.TrainRepository;
import com.bee.reservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private UserRepository userRepository;

    public Reservation bookTicket(ReservationPojo reservationPojo) {
        var userPojo = reservationPojo.getUserPojo();
        Reservation reservation = new Reservation();
        reservation.setSchedule(findOrGetSchedule(reservationPojo));
        reservation.setUser(findOrGetUserByEmail(userPojo));
        reservation.setBookedAt(LocalDateTime.from(Instant.now()));


        reservationRepository.save(reservation);
        return reservation;
    }

    Schedule findOrGetSchedule(ReservationPojo reservationPojo) {

        return null;
    }
    User findOrGetUserByEmail(UserPojo userPojo) {
        var userRecord = userRepository.findByEmail(userPojo.getEmail());
        if (userRecord.isPresent())
            return userRecord.get();
        return new User(userPojo.getFirstName(), userPojo.getLastName(), userPojo.getEmail());
    }
}
