package com.palmcms.api.domain.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class CmsApplicationDTO implements Serializable {

    private Integer id;

    @JsonIgnore
    private Integer userId;

    private String userLoginId;

    @NotEmpty
    private String userName;

    private String proviName;

    @NotEmpty
    private String nickName;

    @NotEmpty
    private String userContactNo;

    @NotEmpty
    private String userBirthDay;

    private String userSign;

    @NotEmpty
    private String productName;

    private Integer productPrice;

    private Integer productServiceMonth;

    @NotEmpty
    private String holderName;

    @NotEmpty
    @Pattern(regexp = "^[YN]$")
    private String selfYN;

    @NotEmpty
    private String relation;

    @NotEmpty
    private String bankCode;

    @NotEmpty
    private String acctNo;

    @NotEmpty
    private String holderContactNo;

    @NotEmpty
    private String holderBirthDay;

    private String holderSign;

    private String cmsAppStatus;

    private Timestamp creationDate;

    @JsonIgnore
    private Timestamp modificationDate;

}
