package com.zev.studentmanager.service.impl;

import com.zev.studentmanager.dto.request.UpdateUserInfoRequest;
import com.zev.studentmanager.dto.response.PageResponse;
import com.zev.studentmanager.dto.response.UserDto;
import com.zev.studentmanager.entity.Address;
import com.zev.studentmanager.entity.User;
import com.zev.studentmanager.mapper.AddressMapper;
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
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapper();

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper = new AddressMapper();
    @Override
    public UserDetailsService userDetailsService() {

        log.info("----- get user details -----");
        return username -> userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));


    }

    @Override
    public void updateUserInformation(UpdateUserInfoRequest request, Long userId) {
        try{
            log.info("----- update user by id: {} -----", userId);
            var user = getById(userId);

            // just set input not null
            if (StringUtils.hasLength(request.getEmail())){
                user.setEmail(request.getEmail());
            }
            if (StringUtils.hasLength(request.getFirstName())){
                user.setFirstName(request.getFirstName());
            }
            if (StringUtils.hasLength(request.getLastName())){
                user.setLastName(request.getLastName());
            }
            if (request.getDateOfBirth() != null){
                user.setDateOfBirth(request.getDateOfBirth());
            }
            if (StringUtils.hasLength(request.getGender())){
                user.setGender(request.getGender());
            }
            if (StringUtils.hasLength(request.getPhone())){
                user.setPhone(request.getPhone());
            }
            if (request.getAddresses() != null){
                saveAddressesToUser(request.getAddresses(), user);
            }

            userRepository.save(user);
            log.info("----- update user successfully -----");
        }
        catch (Exception e){
            log.error("error - {}",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("----- get user by id: {} -----", id);
        return userMapper.toDto(getById(id));

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
        log.info("----- get users-------");

        List<UserDto> users = userRepository.findAll(pageable).stream().map(userMapper::toDto).toList();

        return PageResponse.builder()
                .items(users)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(users.size())
                .build();
    }



    @Override
    public PageResponse<?> getActiveUsers(Pageable pageable) {

        log.info("---------------- get active users ----------------");
        var users = userRepository.findUsersDeletedEqualsFalse(pageable).stream().map(userMapper::toDto).toList();;

        return PageResponse.builder()
                .items(users)
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(users.size())
                .build();
    }

    private User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void saveAddressesToUser(Set<Address> addresses, User user) {
        try{
            log.info("------------ Saving addresses ---------------");
            addresses.forEach(address -> address.setUser(user));
            addressRepository.saveAll(addresses);
            user.setAddresses(addresses);
        }catch (Exception e){
            log.error("error - {}", e.getMessage());

            throw new RuntimeException(e);
        }
    }

}
