package com.bee.reservation.model;

import lombok.AllArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
public class Response implements Serializable {
    boolean isSuccess;
    String message;
    Object data;
}
