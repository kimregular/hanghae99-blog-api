package com.sparta.hanghaeblogserver.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}

// status code
// base response dto