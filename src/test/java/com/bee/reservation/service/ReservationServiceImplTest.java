package com.bee.reservation.service;

import com.bee.reservation.exception.NotFoundException;
import com.bee.reservation.exception.SeatNotAvailableException;
import com.bee.reservation.exception.UserAlreadyExistsException;
import com.bee.reservation.model.Reservation;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.pojo.UserPojo;
import com.bee.reservation.repository.ReservationRepository;
import com.bee.reservation.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

@SpringBootTest
public class ReservationServiceImplTest {

    @Autowired
    ReservationServiceImpl reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void init() {
        reservationRepository.deleteAll();
        reservationRepository.flush();
    }

    @Test
    void testReserveTicket() throws NotFoundException, SeatNotAvailableException {
        var reservation = reservationService.bookTicket(buildReservationPojo());
        assertThat(reservation, is(notNullValue()));
        assertTrue(reservation.getId() > 0);
        assertEquals("London", reservation.getSchedule().getFromStation());
        assertEquals("France", reservation.getSchedule().getToStation());
        assertEquals("SECTION_A", reservation.getSection());
        assertTrue(reservation.getSeatNumber() > 0);
        assertEquals("2024-04-05", reservation.getDate().toString());
        assertEquals("$5", reservation.getPaidAmount());
        assertEquals("test", reservation.getUser().getFirstName());
        assertEquals("user", reservation.getUser().getLastName());
        assertEquals("test@hotmail.com", reservation.getUser().getEmail());
    }

    @Test
    void testReserveForDifferentDates() throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        var reservation = reservationService.bookTicket(payload);
        assertEquals("2024-04-05", reservation.getDate().toString());

        payload.setDate(LocalDate.parse("2024-04-06"));
        reservation = reservationService.bookTicket(payload);
        assertEquals("2024-04-06", reservation.getDate().toString());
    }

    @Test
    void testReserveAllTicketsSequencially() throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        Reservation reservation;
        // First train has 10 seat per section
        for(int i = 1; i <= 10; i++) {
            reservation = reservationService.bookTicket(payload);
            assertTrue(reservation.getId() > 0);
            assertEquals("2024-04-05", reservation.getDate().toString());
            assertEquals("SECTION_A", reservation.getSection());
            assertEquals(i, reservation.getSeatNumber());
        }
        for(int i = 1; i <= 10; i++) {
            reservation = reservationService.bookTicket(payload);
            assertTrue(reservation.getId() > 0);
            assertEquals("2024-04-05", reservation.getDate().toString());
            assertEquals("SECTION_B", reservation.getSection());
            assertEquals(i, reservation.getSeatNumber());
        }
    }

    @Test
    void testReserveAllTicketsByDeletingMiddle() throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        long[] ids = new long[20];
        // First train has 10 seat per section. Book 20 tickets
        for(int i = 0; i < 20; i++) {
            var ticket = reservationService.bookTicket(payload);
            ids[i] = ticket.getId();
        }

        reservationService.deleteReservation(ids[3]); // SECTION_A
        reservationService.deleteReservation(ids[6]); // SECTION_A
        reservationService.deleteReservation(ids[10]); // SECTION_B

        Reservation reservation = reservationService.bookTicket(payload);
        assertEquals(4, reservation.getSeatNumber());
        assertEquals("SECTION_A", reservation.getSection());

        reservation = reservationService.bookTicket(payload);
        assertEquals(7, reservation.getSeatNumber());
        assertEquals("SECTION_A", reservation.getSection());

        reservation = reservationService.bookTicket(payload);
        assertEquals(1, reservation.getSeatNumber());
        assertEquals("SECTION_B", reservation.getSection());
    }

    @Test
    void testTrainNotFound() throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        payload.setFrom("Unknown");

        Exception exception = assertThrows(NotFoundException.class, () -> {
            reservationService.bookTicket(payload);
        });
        assertTrue(exception.getMessage().contains("Train not found for the stations"));
    }

    @Test
    void deleteReservation() throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        var reservation = reservationService.bookTicket(payload);
        assertTrue(0 < reservation.getId());

        long count = reservationService.deleteReservation(reservation.getId());
        assertEquals(1, count);
        count = reservationService.deleteReservation(100L); // Unknown
        assertEquals(0, count);
    }

    @Test
    void testChangeReservedTicket() throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        var reservation = reservationService.bookTicket(payload);
        assertTrue(0 < reservation.getId());
        assertEquals(1, reservation.getSeatNumber());

        var reservationPojo = reservationService.changeAllottedUserSeat(reservation.getId(), 3);
        assertEquals(3, reservationPojo.getSeatNumber());
    }

    @Test
    void testBookNextAvailableSeat()  throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        var reservation = reservationService.bookTicket(payload);

        var reservationPojo = reservationService.changeAllottedUserSeat(reservation.getId(), 6);
        assertEquals(6, reservationPojo.getSeatNumber());

        reservation = reservationService.bookTicket(payload);
        assertEquals(7, reservation.getSeatNumber());
    }

    @Test
    void testBookAvailableSeatIfMaxSeatNumberReached()  throws NotFoundException, SeatNotAvailableException {
        var payload = buildReservationPojo();
        var reservation = reservationService.bookTicket(payload);

        var reservationPojo = reservationService.changeAllottedUserSeat(reservation.getId(), 10);
        assertEquals(10, reservationPojo.getSeatNumber());

        reservation = reservationService.bookTicket(payload);
        assertEquals(1, reservation.getSeatNumber());
    }

    private ReservationPojo buildReservationPojo() {
        var reservationPojo = new ReservationPojo();
        reservationPojo.setFrom("London");
        reservationPojo.setTo("France");
        reservationPojo.setPaidAmount("$5");
        reservationPojo.setDate(LocalDate.parse("2024-04-05"));

        reservationPojo.setUser(buildUser());
        return reservationPojo;
    }

    private UserPojo buildUser() {
        var pojo = new UserPojo();
        pojo.setFirstName("test");
        pojo.setLastName("user");
        pojo.setEmail("test@hotmail.com");
        return pojo;
    }

}
