package com.zev.studentmanager.service;

import com.zev.studentmanager.dto.request.CreateRoleRequest;
import com.zev.studentmanager.dto.request.UpdateRoleRequest;
import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.RoleDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void addRole(CreateRoleRequest request);

    void deleteRoleById(Integer id);

    RoleDto getRoleById(Integer id);
    PageResponse<?> getRoles(Pageable pageable);

}
