package com.bee.reservation.api;

import com.bee.reservation.model.Reservation;
import com.bee.reservation.model.Schedule;
import com.bee.reservation.model.User;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.pojo.UserPojo;

public abstract class ReservationServiceApi {

    public Reservation bookTicket(ReservationPojo reservationPojo) { return new Reservation(); }

    Schedule findOrGetSchedule(ReservationPojo reservationPojo) { return new Schedule(); }
    User findOrGetUserByEmail(UserPojo userPojo) { return new User(); }
}
