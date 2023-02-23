package com.sparta.hanghaeblogserver.dto.response;

import com.sparta.hanghaeblogserver.dto.request.SignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String username;
    private String password;
    private String email;



}
