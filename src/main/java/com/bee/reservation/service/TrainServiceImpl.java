package com.bee.reservation.service;


import com.bee.reservation.model.Train;
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
            trains.add(mapToPojo(train));
        }
        return trains;
    }

    public TrainPojo mapToPojo(Train train) {
        return new TrainPojo(train.getId(), train.getName());
    }
}
