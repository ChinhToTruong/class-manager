package com.zev.studentmanager.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class TokenResponse implements Serializable{

    private String accessToken;

    private String refreshToken;

    private Long userId;

    // add more fields below
}
