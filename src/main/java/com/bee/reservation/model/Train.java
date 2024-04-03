package com.bee.reservation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "trains")
@Data
@NoArgsConstructor
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Integer maxSeats;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "train")
    private List<Schedule> scheduleList;
}
