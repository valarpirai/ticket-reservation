package com.bee.reservation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservationPojo {
    private String from;
    private String to;
    private long paidAmount;
    private LocalDate date;
    private UserPojo userPojo;
}
