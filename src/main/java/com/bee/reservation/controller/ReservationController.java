package com.bee.reservation.controller;

import com.bee.reservation.exception.InvalidPayloadException;
import com.bee.reservation.exception.NotFoundException;
import com.bee.reservation.exception.SeatNotAvailableException;
import com.bee.reservation.model.Reservation;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.repository.ReservationRepository;
import com.bee.reservation.service.ReservationServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ReservationController {

    @Autowired
    ReservationServiceImpl reservationService;

    /**
     * Book Ticket
     *  From, To, User, pricePaid
     *  first and last name, email address
     *
     */
    @PostMapping("/reservation")
    ResponseEntity bookTicket(@RequestBody ReservationPojo reservationPojo)
            throws NotFoundException, SeatNotAvailableException {
        Reservation reservation = reservationService.bookTicket(reservationPojo);
        return new ResponseEntity(reservationService.mapToPojo(reservation), HttpStatus.CREATED);
    }

    @GetMapping("/reservation/{reservationId}")
    ResponseEntity getTicketDetails(@PathVariable Long reservationId) throws NotFoundException {
        Optional<Reservation> reservation = reservationService.getTicketReservationDetail(reservationId);
        if(reservation.isEmpty()) {
            throw new NotFoundException("Reservation not found");
        }
        return new ResponseEntity(reservationService.mapToPojo(reservation.get()), HttpStatus.OK);
    }

    @GetMapping("/reservations")
    ResponseEntity getSeatBookingOfSection(
            @RequestParam Long trainId,
            @RequestParam String sectionName,
            @RequestParam LocalDate date) {
        List<ReservationPojo> reservations = reservationService.getTicketReservations(trainId, sectionName, date);

        return new ResponseEntity(reservations, HttpStatus.OK);
    }

    @PatchMapping("/reservation/{reservationId}/changeSeat")
    ResponseEntity changeSeatForAnUser(@PathVariable long reservationId, @RequestBody Map<String, Integer> payload)
            throws NotFoundException, SeatNotAvailableException {
        int newSeatNumber = payload.get("seatNumber");
        ReservationPojo reservation = reservationService.changeAllottedUserSeat(reservationId, newSeatNumber);

        return new ResponseEntity(reservation, HttpStatus.OK);
    }

    /**
     *
     * @param reservationId
     * @return
     * @throws NotFoundException
     */
    @DeleteMapping("/reservation/{reservationId}")
    ResponseEntity deleteTicketReservation(@PathVariable Long reservationId) throws NotFoundException {
        long deletedCount = reservationService.deleteReservation(reservationId);
        if (deletedCount == 0) {
            throw new NotFoundException("Reservation not found");
        }
        return new ResponseEntity("Success", HttpStatus.OK);
    }
}
