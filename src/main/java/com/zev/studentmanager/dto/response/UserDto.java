package com.zev.studentmanager.dto.response;

import com.zev.studentmanager.entity.Address;
import com.zev.studentmanager.entity.enums.Gender;
import com.zev.studentmanager.entity.enums.UserStatus;
import com.zev.studentmanager.entity.enums.UserType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

//    private Set<Address> addresses;
}
