package com.bee.reservation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * ReservationPojo is used to receive and send data from and to client
 */
@Data
@AllArgsConstructor
public class ReservationPojo {
    private String from;
    private String to;
    private String paidAmount;
    private LocalDate date;
    private UserPojo user;

    private long reservationId;
    private long trainId;
    private String trainName;
    private String section;
    private int seatNumber;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public ReservationPojo() {}
}
