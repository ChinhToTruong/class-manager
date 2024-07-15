package com.zev.studentmanager.mapper;

import com.zev.studentmanager.dto.response.AddressDto;
import com.zev.studentmanager.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(Address address);
    Address toEntity(AddressDto addressDto);
}
