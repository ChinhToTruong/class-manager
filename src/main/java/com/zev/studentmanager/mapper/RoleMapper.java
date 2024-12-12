package com.zev.studentmanager.mapper;


import com.zev.studentmanager.dto.response.RoleDto;
import com.zev.studentmanager.entity.Role;
import org.mapstruct.Mapper;

@Mapper(
        config = MapperConfig.class,
        uses = {
                RoleMapper.class,
                PermissionMapper.class
        }
)
public interface RoleMapper extends EntityMapper<Role, RoleDto> {
}
