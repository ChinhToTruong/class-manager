package com.zev.studentmanager.service;

import com.zev.studentmanager.dto.request.UpdateUserInfoRequest;
import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    void updateUserInformation(UpdateUserInfoRequest request, Long userId);
    void deleteSoftUserById(Long id);

    void deleteUser(Long id);
    PageResponse<?> getUsers(Pageable pageable);

    PageResponse<?> getActiveUsers(Pageable pageable);

    UserDto getUserById(Long id);
}
