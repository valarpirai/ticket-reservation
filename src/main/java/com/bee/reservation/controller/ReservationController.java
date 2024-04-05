package com.bee.reservation.controller;

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

import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class ReservationController {

    @Autowired
    ReservationServiceImpl reservationService;

    /**
     * Book Ticket
     *  From, To, User, price paid
     *  first and last name, email address
     *
     */
    @PostMapping("/reservation")
    ResponseEntity bookTicket(@RequestBody ReservationPojo reservationPojo) {
        Reservation reservation = null;
        try {
            reservation = reservationService.bookTicket(reservationPojo);
        } catch (NotFoundException | SeatNotAvailableException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reservationService.mapToPojo(reservation), HttpStatus.CREATED);
    }

    @GetMapping("/reservation/{reservationId}")
    ResponseEntity getTicketDetails(@PathVariable Long reservationId) {
        Optional<Reservation> reservation = reservationService.getTicketReservationDetails(reservationId);
        if(reservation.isEmpty()) {
            return new ResponseEntity("Reservation not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(reservationService.mapToPojo(reservation.get()), HttpStatus.OK);
    }

    @DeleteMapping("/reservation/{reservationId}")
    ResponseEntity bookTicket(@PathVariable Long reservationId) {
        long deletedId = reservationService.deleteReservation(reservationId);
        if (deletedId != 0)
            return new ResponseEntity("Ok", HttpStatus.OK);
        else
            return new ResponseEntity("", HttpStatus.NOT_FOUND);
    }
}
