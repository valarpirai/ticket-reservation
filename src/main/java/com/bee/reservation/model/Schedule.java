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

    private String startStation;
    private String endStation;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

//    Available seat count

    @Column(name = "train_id", insertable=false, updatable=false)
    private Long trainId;

    @ManyToOne
    @JoinColumn(name="train_id", nullable=false)
    private Train train;

    public int maxSeat() {
        return train.getMaxSeatPerSection();
    }
}
