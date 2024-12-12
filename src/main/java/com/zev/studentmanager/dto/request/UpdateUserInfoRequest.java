package com.zev.studentmanager.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zev.studentmanager.entity.Address;
import com.zev.studentmanager.enums.Gender;
import com.zev.studentmanager.validator.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Getter
@Builder
public class UpdateUserInfoRequest {

    @NotBlank(message = "first name must be required")
    @Pattern(regexp = "^[\\p{L}]+([\\s]+[\\p{L}]+)*$",
                message = "name must be one letter uppercase")
    @Schema(defaultValue = "Tran Thi")
    private String firstName;

    @Schema(defaultValue = "Hoa")
    @NotBlank(message = "last name must be required")
    @Pattern(regexp = "^[\\p{L}]+([\\s]+[\\p{L}]+)*$",
            message = "name must be one letter uppercase")
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Pattern(regexp = "^(0?[3789]|84[3789])\\d{8}$",
            message = "phone is incorrect")
    private String phone;

    @Schema(defaultValue = "tranthihoa@gmail.com")
    @Pattern(regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$",
            message = "email is incorrect")
    private String email;

    @ValueOfEnum(enumClass = Gender.class, message = "gender must be male, female or other")
    @NotBlank(message = "gender must be required")
    @Schema(defaultValue = "MALE")
    private String gender;

    private String address;
}
