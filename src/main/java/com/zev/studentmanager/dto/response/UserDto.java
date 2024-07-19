package com.zev.studentmanager.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String phone;

    private String email;

    private boolean deleted;

    private String gender;

    private String type;

    private String status;

    private String address;
}
