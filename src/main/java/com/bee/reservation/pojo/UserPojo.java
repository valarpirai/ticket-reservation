package com.bee.reservation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPojo {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public UserPojo() {}
}
