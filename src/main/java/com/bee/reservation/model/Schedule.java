package com.bee.reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private String startStation;

    private String endStation;

    @Column(name = "train_id", insertable=false, updatable=false)
    private Long trainId;

    @ManyToOne
    @JoinColumn(name="train_id", nullable=false)
    private Train train;
}
