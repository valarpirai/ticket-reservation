package com.bee.reservation.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReservationServiceImplTest {

    @Autowired
    ReservationServiceImpl reservationService;

    @Test
    void testReserveTicket() {
//        reserve
//        reserve all
//        random Cancel and reserve
//        cancel all and reserver
    }

    @Test
    void testReserveForDifferentDates() {

    }

    @Test
    void testReserveAllTicketsSequencially() {

    }

    @Test
    void testReserveAllTicketsByDeletingMiddle() {

    }

    @Test
    void testTrainNotFound() {

    }

    @Test
    void testSeatsNotAvailable() {

    }

    @Test
    void deleteReservation() {

    }

    @Test
    void testChangeReservedTicket() {

    }

}
