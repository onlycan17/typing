package com.palmcms.api.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CmsUserDTO implements Serializable {

    private String payerNo;

    private String payerName;

    private String contactNo;

    private String status;

    private String email;

    private String userId;

    private String cmsProductName;

    private String birthday;

}
