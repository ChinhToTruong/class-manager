package com.zev.studentmanager.mapper;

import com.zev.studentmanager.dto.response.AddressDto;
import com.zev.studentmanager.entity.Address;

public class AddressMapper {
    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .streetNumber(address.getStreetNumber())
                .street(address.getStreet())
                .addressType(address.getAddressType())
                .floor(address.getFloor())
                .city(address.getCity())
                .country(address.getCountry())
                .apartmentNumber(address.getApartmentNumber())
                .building(address.getBuilding())
                .build();
    }

    public Address toEntity(AddressDto address) {
        return Address.builder()
                .streetNumber(address.getStreetNumber())
                .street(address.getStreet())
                .addressType(address.getAddressType())
                .floor(address.getFloor())
                .city(address.getCity())
                .country(address.getCountry())
                .apartmentNumber(address.getApartmentNumber())
                .building(address.getBuilding())
                .build();
    }
}
