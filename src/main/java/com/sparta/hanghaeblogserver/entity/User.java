package com.sparta.hanghaeblogserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(nullable = false, unique = true)
    @Size(min = 4, max = 10, message = "아이디는 4자 이상, 10자 이하만 가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String username;

    @Column(nullable = false)
//    @Size(min = 8, max = 15, message = "비밀번호는 8자 이상, 15자 이하만 가능합니다.")
//    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String password;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
}
