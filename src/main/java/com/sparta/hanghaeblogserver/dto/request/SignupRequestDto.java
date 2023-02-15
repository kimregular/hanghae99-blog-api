package com.sparta.hanghaeblogserver.dto.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private Long id;
    private String username;
    private String password;
    private String email;

}
