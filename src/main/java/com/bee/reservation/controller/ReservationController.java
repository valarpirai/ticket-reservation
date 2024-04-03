package com.bee.reservation.controller;

import com.bee.reservation.model.Reservation;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.repository.ReservationRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationController {

    /**
     * Book Ticket
     *  From, To, User, price paid
     *  first and last name, email address
     *
     */
    @PostMapping
    ResponseEntity bookTicket(@RequestBody ReservationPojo reservationPojo) {

        return new ResponseEntity(null, HttpStatus.CREATED);
    }
}
