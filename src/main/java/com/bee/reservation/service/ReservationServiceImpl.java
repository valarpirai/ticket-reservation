package com.bee.reservation.service;

import com.bee.reservation.api.ReservationServiceApi;
import com.bee.reservation.constants.TrainSections;
import com.bee.reservation.exception.NotFoundException;
import com.bee.reservation.exception.SeatNotAvailableException;
import com.bee.reservation.model.Reservation;
import com.bee.reservation.model.Schedule;
import com.bee.reservation.model.Train;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.repository.ReservationRepository;
import com.bee.reservation.repository.ScheduleRepository;
import com.bee.reservation.repository.TrainRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl extends ReservationServiceApi {

    private final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private TrainServiceImpl trainService;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    public Reservation bookTicket(ReservationPojo reservationPojo) throws NotFoundException, SeatNotAvailableException {
//        Validate Request Payload
        var userPojo = reservationPojo.getUser();
        Reservation reservation = new Reservation();

        findAndAssignSeat(reservation, reservationPojo);

        reservation.setUser(userService.findOrCreateUser(userPojo));
        reservation.setBookedAt(LocalDateTime.now());
        reservation.setPaidAmount(reservationPojo.getPaidAmount());
//        Find train name by using from and To
//        Check tickets available for the train

        reservationRepository.save(reservation);
        return reservation;
    }

    public ReservationPojo mapToPojo(Reservation reservation) {
        ReservationPojo reservationPojo = new ReservationPojo();
        reservationPojo.setReservationId(reservation.getId());
        reservationPojo.setFrom(reservation.getSchedule().getStartStation());
        reservationPojo.setTo(reservation.getSchedule().getEndStation());
        reservationPojo.setSection(reservation.getSection());
        reservationPojo.setSeatNumber(reservation.getSeatNumber());
        reservationPojo.setDate(reservation.getDate());
        reservationPojo.setDepartureTime(reservation.getSchedule().getDepartureTime());
        reservationPojo.setArrivalTime(reservation.getSchedule().getArrivalTime());
        reservationPojo.setPaidAmount(reservation.getPaidAmount());
        reservationPojo.setTrain(trainService.mapToPojo(reservation.getTrain()));
        reservationPojo.setUser(userService.mapToPojo(reservation.getUser()));
        return reservationPojo;
    }

    public Optional<Reservation> getTicketReservationDetails(Long reservationId) {

        return reservationRepository.findById(reservationId);
    }

    public List<Reservation> getTicketReservationsForSection(long trainId, String sectionName, LocalDate date) {
        return null;
    }

    Reservation changeAllotedUserSeat(Long userId, int seatId) {
//        Check the expected seat is available, then assign it otherwise throw exception
        return null;
    }

    @Transactional
    public Long deleteReservation(Long reservationId) {
        return reservationRepository.removeById(reservationId);
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
     * Find the next available seat in the Specified Train section.
     * @param reservations
     * @param section
     * @param maxSeatPerSection
     * @return
     */
    private int findNextAvailableSeat(List<Reservation> reservations, TrainSections section, int maxSeatPerSection) {
        var seatNos = reservations
                .stream()
                .filter(reservation -> reservation.getSection().equalsIgnoreCase(section.name()))
                .map(Reservation::getSeatNumber)
                .collect(Collectors.toList());

        logger.info("seatNos =>  " + seatNos.toString());
        if (seatNos.size() == maxSeatPerSection) {
            // No Seat available
            return -1;
        } else if (seatNos.size() == 0) {
            // All seats available. Start with 1st seat
            return 1;
        } else if (Collections.max(seatNos) == maxSeatPerSection) {
            // Return available seat from middle (find previously cancelled seats)
            for (int i = 1; i <= seatNos.size(); i++) {
                if (!seatNos.contains(i))
                    return i;
            }
        }
        // Return next available seat
        return Collections.max(seatNos) + 1;
    }
}
