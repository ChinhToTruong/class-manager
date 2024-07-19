package com.zev.studentmanager.service;

import com.zev.studentmanager.dto.request.CreatePermissionRequest;
import com.zev.studentmanager.dto.request.UpdatePermissionRequest;
import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.PermissionDto;
import org.springframework.data.domain.Pageable;

public interface PermissionService {
    void addPermission(CreatePermissionRequest request);
    void deletePermission(Integer id);

    PermissionDto getPermissionById(Integer id);
    PageResponse<?> getPermissions(Pageable pageable);
    
}
