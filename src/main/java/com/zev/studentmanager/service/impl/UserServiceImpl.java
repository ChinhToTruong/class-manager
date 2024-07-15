package com.zev.studentmanager.service.impl;

import com.zev.studentmanager.dto.request.UpdateUserInfoRequest;
import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.UserDto;
import com.zev.studentmanager.entity.User;
import com.zev.studentmanager.mapper.UserMapper;
import com.zev.studentmanager.repository.AddressRepository;
import com.zev.studentmanager.repository.UserRepository;
import com.zev.studentmanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final AddressRepository addressRepository;

    private final UserMapper userMapper;


    @Override
    public UserDetailsService userDetailsService() {

        log.info("----- get user details -----");
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));


    }

    @Override
    public void updateUserInformation(UpdateUserInfoRequest request, Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        updateInforUser(request, user);
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("----- get user by id: {}", id);
        var user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return userMapper.toDto(user);
    }

    @Override
    public void deleteSoftUserById(Long id) {
        try{
            log.info("----- delete user by id: {} -----", id);
            var user = getById(id);
            user.setDeleted(true);
            userRepository.save(user);
            log.info("----- soft delete user successfully -----");
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        try{
            log.info("----- delete user by id: {} -----", id);
            var user = getById(id);
            userRepository.delete(user);
            log.info("----- delete user successfully -----");
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

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void updateInforUser(UpdateUserInfoRequest  request, User user) {
        // update user information here
        user.setAddresses(request.getAddresses());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        userRepository.save(user);
    }
}
