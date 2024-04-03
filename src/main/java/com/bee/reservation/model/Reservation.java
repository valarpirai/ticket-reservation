package com.bee.reservation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long trainId;

    private Long scheduleId;

    private Long userId;

    private Long price;

    private LocalDateTime bookedAt;

//    private SeatInfo

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="train_id", nullable=false, referencedColumnName = "id")
//    private Train train;
//
//    private Schedule schedule;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="user_id", nullable=false, referencedColumnName = "id")
//    private User user;
}
