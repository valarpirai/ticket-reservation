package com.bee.reservation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * ReservationPojo is used to receive and send data from and to client
 */
@Data
@AllArgsConstructor
public class ReservationPojo {
    private long reservationId;
    private String from;
    private String to;
    private String paidAmount;
    private LocalDate date;
    private UserPojo user;

    private TrainPojo train;
    private String section;
    private int seatNumber;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalDateTime bookedAt;

    public ReservationPojo() {}
}
