package com.zev.studentmanager.dto.response;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AddressDto {
    private Long id;

    private String apartmentNumber;


    private String floor;


    private String building;


    private String streetNumber;


    private String street;


    private String city;


    private String country;

    private Integer addressType;

}
