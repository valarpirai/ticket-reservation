package com.bee.reservation.controller;

import com.bee.reservation.model.Reservation;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.repository.ReservationRepository;
import com.bee.reservation.service.ReservationServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/reserve")
    ResponseEntity bookTicket(@RequestBody ReservationPojo reservationPojo) {
        reservationService.bookTicket(reservationPojo);
//        Seats not available
//        Train not available
        return new ResponseEntity(null, HttpStatus.CREATED);
    }

    @GetMapping("/reservation/{reservationId}")
    ResponseEntity getTicketDetails(@PathVariable Long reservationId) {
        reservationService.getTicketReservationDetails(reservationId);
        return new ResponseEntity(null, HttpStatus.OK);
    }

    @DeleteMapping("/reserve/{reservationId}")
    ResponseEntity bookTicket(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
