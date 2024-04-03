package com.bee.reservation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationPojo {
    private String from;
    private String to;
    private float pricePaid;
    private UserPojo user;
}
