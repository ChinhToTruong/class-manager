package com.zev.studentmanager.service.impl;

import com.zev.studentmanager.dto.request.CreateRoleRequest;
import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.RoleDto;
import com.zev.studentmanager.entity.Permission;
import com.zev.studentmanager.entity.Role;
import com.zev.studentmanager.mapper.RoleMapper;
import com.zev.studentmanager.repository.PermissionRepository;
import com.zev.studentmanager.repository.RoleRepository;
import com.zev.studentmanager.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
@PreAuthorize("hasAnyRole('ADMIN')")
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    @Override
    public void addRole(CreateRoleRequest request) {
        Set<Permission> permissions = new HashSet<>();

        for (Integer id : request.getPermissions()){
            var permission = permissionRepository.findById(id)
                    .orElseThrow(
                            () -> new RuntimeException("permission not found: "+ id)
                    );
            permissions.add(permission);
        }

        var role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .permissions(permissions)
                .build();

        try {
            roleRepository.save(role);
            log.info("---------- add success role: {}------------", role.getName());
        }
        catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw new DataIntegrityViolationException("role already exists");
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }



    @Override
    public void deleteRoleById(Integer id) {
        var role = roleMapper.toEntity(getRoleById(id));

        roleRepository.delete(role);
    }

    @Override
    public RoleDto getRoleById(Integer id) {
        return roleMapper.toDto(roleRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Role not found with id: " + id)
                ));
    }

    @Override
    public PageResponse<?> getRoles(Pageable pageable) {
        var roles = roleRepository.findAll(pageable).stream().toList();
        return PageResponse.builder()
                .total(roles.size())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .items(roleMapper.toDto(roles))
                .build();
    }



}
