package com.zev.studentmanager.mapper;

import com.zev.studentmanager.dto.response.UserDto;
import com.zev.studentmanager.entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;



public class UserMapper {

    private final AddressMapper addressMapper = new AddressMapper();
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .phone(user.getPhone())
                .addresses(user.getAddresses().stream().map(addressMapper::toDto).collect(Collectors.toSet()))
                .build();
    }

    public User toEntity(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .gender(userDto.getGender())
                .dateOfBirth(userDto.getDateOfBirth())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .addresses(userDto.getAddresses().stream().map(addressMapper::toEntity).collect(Collectors.toSet()))
                .build();
    }
}
