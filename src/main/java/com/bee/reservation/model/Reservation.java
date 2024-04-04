package com.bee.reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "train_id", insertable=false, updatable=false)
    private Long trainId;

    @Column(name = "schedule_id", insertable=false, updatable=false)
    private Long scheduleId;

    @Column(name = "user_id", insertable=false, updatable=false)
    private Long userId;

    private Long price;

    @Column(updatable = false)
    private LocalDateTime bookedAt;

    private LocalDate date;

    private String section;
    private int seatNumber;

    @ManyToOne
    @JoinColumn(name="train_id", nullable=false)
    private Train train;

    @ManyToOne
    @JoinColumn(name="schedule_id", nullable=false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
