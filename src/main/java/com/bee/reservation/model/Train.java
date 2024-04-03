package com.bee.reservation.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "trains")
@Data
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Integer maxSeats;

//    @OneToMany(mappedBy = "train")
//    private List<Schedule> scheduleList;
}
