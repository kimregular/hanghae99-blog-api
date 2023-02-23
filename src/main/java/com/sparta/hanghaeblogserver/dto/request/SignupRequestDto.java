package com.sparta.hanghaeblogserver.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {


    private String username;
    private String password;
    private String email;

    @JsonProperty("isAdmin")
    private boolean isAdmin;

    private String adminToken = "";

}
