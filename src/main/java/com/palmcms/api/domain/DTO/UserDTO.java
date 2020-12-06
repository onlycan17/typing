package com.palmcms.api.domain.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palmcms.api.domain.enums.UserStatusType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class UserDTO implements Serializable {

    private Integer id;

    private String userLoginId;

    @JsonIgnore
    private String userPasswordHash;

    private String userName;

    private String proviName;

    private String nickName;

    private String birthDay;

    private String contactNo;

    private UserStatusType userStatus;

    private Integer churchId;

    private String isCmsStop;

    private String payerNo;

    @JsonIgnore
    private Timestamp creationDate;

    @JsonIgnore
    private Timestamp modificationDate;

    private String parishName;

    private String userChurchName;

}
