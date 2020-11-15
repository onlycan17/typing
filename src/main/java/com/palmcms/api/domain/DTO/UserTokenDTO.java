package com.palmcms.api.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class UserTokenDTO implements Serializable {

    private Integer userId;

    private String token;

    private Timestamp expiredDate;

    private Timestamp creationDate;
}
