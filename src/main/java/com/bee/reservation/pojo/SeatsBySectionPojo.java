package com.bee.reservation.pojo;

import com.bee.reservation.constants.TrainSections;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SeatsBySectionPojo {
    private String trainName;
    private TrainSections sectionName;
    private LocalDate date;
}
