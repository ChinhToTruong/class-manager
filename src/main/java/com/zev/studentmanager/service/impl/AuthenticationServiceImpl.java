package com.zev.studentmanager.service.impl;

import com.zev.studentmanager.dto.request.RefreshTokenRequest;
import com.zev.studentmanager.dto.request.RegisterRequest;
import com.zev.studentmanager.dto.request.AuthenticationRequest;
import com.zev.studentmanager.dto.response.TokenResponse;
import com.zev.studentmanager.entity.User;
import com.zev.studentmanager.repository.RoleRepository;
import com.zev.studentmanager.repository.UserRepository;
import com.zev.studentmanager.service.AuthenticationService;
import com.zev.studentmanager.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    @Override
    public TokenResponse authentication(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("username or password is incorrect")
                );

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .build();
    }


    @Override
    public void logout() {

    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        return null;
    }

    @Override
    public void register(RegisterRequest request) throws RoleNotFoundException {

        log.info("------- register username: {} ----------", request.getUsername());

        var role = roleRepository.findById(request.getRole())
                .orElseThrow(() -> new RoleNotFoundException("role not found: " + request.getRole()));

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        try {
            userRepository.save(user);
            log.info("---------- add success username: {}------------", user.getUsername());
        }
        catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw new DataIntegrityViolationException("user already exists");
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
