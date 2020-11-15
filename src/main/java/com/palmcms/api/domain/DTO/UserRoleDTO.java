package com.palmcms.api.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserRoleDTO implements Serializable {

    private Integer userId;

    private String authorityName;

}
