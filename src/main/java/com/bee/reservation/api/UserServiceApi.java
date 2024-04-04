package com.bee.reservation.api;

import com.bee.reservation.exception.NotFoundException;
import com.bee.reservation.model.User;
import com.bee.reservation.pojo.UserPojo;
import org.springframework.stereotype.Service;

public abstract class UserServiceApi {
    User findOrGetUserByEmail(UserPojo userPojo)  { return new User(); }
}
