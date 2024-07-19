package com.zev.studentmanager.service.impl;

import com.zev.studentmanager.dto.request.ChangePasswordRequest;
import com.zev.studentmanager.dto.request.RegisterRequest;
import com.zev.studentmanager.dto.request.UpdateUserInfoRequest;
import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.UserDto;
import com.zev.studentmanager.entity.User;
import com.zev.studentmanager.enums.MessageCode;
import com.zev.studentmanager.mapper.UserMapper;
import com.zev.studentmanager.repository.RoleRepository;
import com.zev.studentmanager.repository.UserRepository;
import com.zev.studentmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;


@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserDetailsService userDetailsService() {
        log.info("----- get user details -----");
        return username -> userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found:" + username)
                );
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public void updateUserInformation(UpdateUserInfoRequest request, Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found: "+ userId)
                );
        updateInfoUser(request, user);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("----- get user by id: {}", id);
        var user = userRepository.findById(id)
                .orElseThrow(
                    () -> new UsernameNotFoundException("user not found: " + id)
                );
        return userMapper.toDto(user);
    }

    @Override
    public void deleteSoftUserById(Long id) {
        try{
            log.info("----- delete user by id: {} -----", id);
            var user = getById(id);
            user.setDeleted(true);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new RuntimeException(MessageCode.FAILURE.getMessage() + e);
        }
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ALL')")
    public void deleteUser(Long id) {
        try{
            log.info("----- delete user by id: {} -----", id);
            var user = getById(id);
            userRepository.delete(user);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public PageResponse<?> getUsers(Pageable pageable) {
        var users = userRepository.findAll(pageable).stream().toList();
        return PageResponse.builder()
                .total(users.size())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .items(userMapper.toDto(users))
                .build();
    }



    @Override
    public PageResponse<?> getActiveUsers(Pageable pageable) {
        var users = userRepository.findUsersDeletedEqualsFalse(pageable).stream().toList();
        return PageResponse.builder()
                .total(users.size())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .items(userMapper.toDto(users))
                .build();
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public void changePassword(ChangePasswordRequest request, Long userId) {

        var user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new UsernameNotFoundException("user not found: "+ userId)
                );

        user.setPassword(request.getNewPassword());

        userRepository.save(user);
    }


    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found: "+ id));
    }

    private void updateInfoUser(UpdateUserInfoRequest  request, User user) {
        // update user information here
        user.setAddress(request.getAddress());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        userRepository.save(user);
    }



}
