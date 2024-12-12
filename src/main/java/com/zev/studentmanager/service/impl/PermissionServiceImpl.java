package com.zev.studentmanager.service.impl;

import com.zev.studentmanager.dto.request.CreatePermissionRequest;

import com.zev.studentmanager.dto.request.UpdatePermissionRequest;

import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.PermissionDto;

import com.zev.studentmanager.entity.Permission;
import com.zev.studentmanager.mapper.PermissionMapper;
import com.zev.studentmanager.repository.PermissionRepository;
import com.zev.studentmanager.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasAnyAuthority('ALL')")
public class PermissionServiceImpl implements PermissionService {
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;
    @Override
    public void addPermission(CreatePermissionRequest request) {
        var permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        try {
            permissionRepository.save(permission);
            log.info("---------- add success permission: {}------------", permission.getName());
        }
        catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw new DataIntegrityViolationException("permission already exists");
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }


    @Override
    public void deletePermission(Integer id) {
        var permission = permissionMapper.toEntity(getPermissionById(id));

        permissionRepository.delete(permission);
    }

    @Override
    public PermissionDto getPermissionById(Integer id) {
        return permissionMapper.toDto(permissionRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Permission not found with id: " + id)
                ));
    }

    @Override
    public PageResponse<?> getPermissions(Pageable pageable) {
        var permissions = permissionRepository.findAll(pageable).stream().toList();
        return PageResponse.builder()
                .total(permissions.size())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .items(permissionMapper.toDto(permissions))
                .build();
    }

}
