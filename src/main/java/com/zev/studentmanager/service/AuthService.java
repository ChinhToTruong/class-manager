package com.zev.studentmanager.service;

import com.nimbusds.jose.JOSEException;
import com.zev.studentmanager.dto.request.*;
import com.zev.studentmanager.dto.response.AuthenticationResponse;
import com.zev.studentmanager.dto.response.IntrospectResponse;

import javax.management.relation.RoleNotFoundException;
import java.text.ParseException;

public interface AuthService {
    IntrospectResponse introspect(IntrospectRequest request) throws ParseException, JOSEException;
    AuthenticationResponse authenticate(AuthenticationRequest request);
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;

}
