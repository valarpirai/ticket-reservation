package com.bee.reservation.service;


import com.bee.reservation.model.Schedule;
import com.bee.reservation.model.Train;
import com.bee.reservation.pojo.SchedulePojo;
import com.bee.reservation.pojo.TrainPojo;
import com.bee.reservation.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainServiceImpl {
    @Autowired
    private TrainRepository trainRepository;

    public List<TrainPojo> getAllTrains() {
        List<TrainPojo> trains = new ArrayList<TrainPojo>();
        for(Train train: trainRepository.findAll()) {
            trains.add(mapToPojo(train, true));
        }
        return trains;
    }

    public TrainPojo mapToPojo(Train train) {
        return mapToPojo(train, false);
    }
    public TrainPojo mapToPojo(Train train, boolean withSchedules) {
        var trainPojo = new TrainPojo();
        trainPojo.setTrainId(train.getId());
        trainPojo.setName(train.getName());
        if (withSchedules) {
            trainPojo.setSchedules(mapToSchedulePojo(train.getScheduleList()));
        }
        return trainPojo;
    }

    public List<SchedulePojo> mapToSchedulePojo(List<Schedule> schedules) {
        List<SchedulePojo> schedulePojos = new ArrayList<SchedulePojo>();
        for(Schedule s: schedules) {
            schedulePojos.add(
                    new SchedulePojo(
                            s.getId(),
                            s.getFromStation(),
                            s.getToStation(),
                            s.getDepartureTime(),
                            s.getDepartureTime()
                    )
            );
        }
        return schedulePojos;
    }
}
