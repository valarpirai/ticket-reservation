package com.bee.reservation.api;

import com.bee.reservation.exception.NotFoundException;
import com.bee.reservation.exception.SeatNotAvailableException;
import com.bee.reservation.model.Reservation;
import com.bee.reservation.model.Schedule;
import com.bee.reservation.model.User;
import com.bee.reservation.pojo.ReservationPojo;
import com.bee.reservation.pojo.UserPojo;

import java.util.HashSet;
import java.util.Set;

public abstract class ReservationServiceApi {

    public Reservation bookTicket(ReservationPojo reservationPojo) throws NotFoundException, SeatNotAvailableException { return new Reservation(); }

    Set<Schedule> findSchedule(ReservationPojo reservationPojo) throws NotFoundException { return new HashSet<Schedule>(); }
}
