package com.zev.studentmanager.mapper;

import com.zev.studentmanager.dto.response.UserDto;
import com.zev.studentmanager.entity.AbstractEntity;
import com.zev.studentmanager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(
        config = MapperConfig.class,
        uses = {
                UserMapper.class,
                RoleMapper.class
        }
)
public interface UserMapper extends EntityMapper<User, UserDto>   {

}
