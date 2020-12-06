package com.palmcms.api.auth;

import com.palmcms.api.domain.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseVO {

    String token;

    UserDTO user;

    List<String> roles;

}
