package com.bee.reservation.pojo;

import com.bee.reservation.model.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class TrainPojo {
    private long trainId;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SchedulePojo> schedules;

    public TrainPojo() {}
}
