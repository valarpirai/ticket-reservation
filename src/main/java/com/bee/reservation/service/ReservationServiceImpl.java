package com.bee.reservation.service;

import com.bee.reservation.api.ReservationServiceApi;
import com.bee.reservation.constants.TrainSections;
import com.bee.reservation.exception.NotFoundException;
import com.bee.reservation.exception.SeatNotAvailableException;
import com.bee.reservation.model.Reservation;
import com.bee.reservation.model.Schedule;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.repository.ReservationRepository;
import com.bee.reservation.repository.ScheduleRepository;
import com.bee.reservation.repository.TrainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl extends ReservationServiceApi {

    private final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);
    @Autowired
    UserServiceImpl userService;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private TrainRepository trainRepository;

    public Reservation bookTicket(ReservationPojo reservationPojo) throws NotFoundException, SeatNotAvailableException {
//        Validate Request Payload
        var userPojo = reservationPojo.getUser();
        Reservation reservation = new Reservation();

        findAndAssignSeat(reservation, reservationPojo);

        reservation.setUser(userService.findOrGetUserByEmail(userPojo));
        reservation.setBookedAt(LocalDateTime.now());
        reservation.setPaidAmount(reservationPojo.getPaidAmount());
//        Find train name by using from and To
//        Check tickets available for the train

        reservationRepository.save(reservation);
        return reservation;
    }

    public ReservationPojo mapToPojo(Reservation reservation) {
        ReservationPojo reservationPojo = new ReservationPojo();
        reservationPojo.setFrom(reservation.getSchedule().getStartStation());
        reservationPojo.setTo(reservation.getSchedule().getEndStation());
        reservationPojo.setSection(reservation.getSection());
        reservationPojo.setSeatNumber(reservation.getSeatNumber());
        reservationPojo.setDate(reservation.getDate());
        reservationPojo.setPaidAmount(reservation.getPaidAmount());
        reservationPojo.setTrainId(reservation.getTrain().getId());
        reservationPojo.setUser(userService.mapToPojo(reservation.getUser()));
        return reservationPojo;
    }

    public Optional<Reservation> getTicketReservationDetails(Long reservationId) {

        return reservationRepository.findById(reservationId);
    }

    Reservation changeAllotedUserSeat(Long userId, int seatId) {
//        Check the expected seat is available, then assign it otherwise throw exception
        return null;
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    Set<Schedule> findSchedule(ReservationPojo reservationPojo) throws NotFoundException {
        var schedules = scheduleRepository.findByStartStationAndEndStation(reservationPojo.getFrom(), reservationPojo.getTo());
        if(schedules.isEmpty())
            throw new NotFoundException("Train not found for the stations");
        return schedules;
    }

    private void findAndAssignSeat(Reservation reservation, ReservationPojo reservationPojo) throws NotFoundException, SeatNotAvailableException {
        var schedules = findSchedule(reservationPojo);
        logger.info("findAndAssignSeat -- schedules: " + schedules.size());
        for (Schedule schedule: schedules) {
            var maxSeats = schedule.maxSeatsPerSection() * 2;
            logger.info("reservations: Date:" + reservationPojo.getDate() + ", Schedule: " + schedule.getId());
            List<Reservation> reservations = reservationRepository.findByDateAndScheduleId(reservationPojo.getDate(), schedule.getId());
            logger.info("reservations: " + reservations.size());
            if(reservations.size() < maxSeats) {
                // Check whether the section seat is available
                // Find next available seat number
                int seatNo = findNextAvailableSeat(reservations, TrainSections.SECTION_A, schedule.maxSeatsPerSection());
                if (seatNo != -1) {
                    reservation.setSection(TrainSections.SECTION_A.name());
                } else {
                    seatNo = findNextAvailableSeat(reservations, TrainSections.SECTION_B, schedule.maxSeatsPerSection());
                    reservation.setSection(TrainSections.SECTION_B.name());
                }

                reservation.setDate(reservationPojo.getDate());
                reservation.setSeatNumber(seatNo);
                reservation.setSchedule(schedule);
                reservation.setTrain(schedule.getTrain());
                break;
            } else {
                // Move to next available schedule
                continue;
            }
        }

        if(reservation.getSchedule() == null)
            throw new SeatNotAvailableException("No seats available for the date. Please try booking for different date");
    }

    /**
     * Find the next available seat in the Specified Train section
     * @param reservations
     * @param maxSeatPerSection
     * @return
     */
    private int findNextAvailableSeat(List<Reservation> reservations, TrainSections section, int maxSeatPerSection) {
//        Find max seat number
//            IF count is not matching max seat number
        //        find missing number and use it
        // if count == max_seat number
//            Move to next section do the same
        var seatNos = reservations
                .stream()
                .filter(reservation -> reservation.getSection().equalsIgnoreCase(section.name()))
                .map(Reservation::getSeatNumber)
                .collect(Collectors.toList());

        logger.info("seatNos =>  " + seatNos.toString());
        // No Seat available
        if (seatNos.size() == maxSeatPerSection) {
            return -1;
        } else if (seatNos.size() == 0) {
            return 1;
        } else if (seatNos.get(seatNos.size() - 1) < maxSeatPerSection) {
            return seatNos.get(seatNos.size() - 1) + 1;
        } else {
            int xor = 0, i = 0;
            for (; i < seatNos.size(); i++) {
                xor = xor ^ i ^ seatNos.get(i);
            }
            return xor ^ i;
        }
    }
}
