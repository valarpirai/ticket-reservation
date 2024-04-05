package com.bee.reservation.controller;

import com.bee.reservation.constants.TrainSections;
import com.bee.reservation.pojo.TrainPojo;
import com.bee.reservation.service.TrainServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TrainController {

    @Autowired
    TrainServiceImpl trainService;

    @GetMapping("/trains")
    List<TrainPojo> getAllTrains() {
        return trainService.getAllTrains();
    }

    @GetMapping("/train/sections")
    TrainSections[] getAllSections() {
        return TrainSections.values();
    }
}
