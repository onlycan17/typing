package com.palmcms.api.domain.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palmcms.api.domain.enums.UserStatusType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class CommonCodeDTO implements Serializable {

    private String codeGroup;

    private String codeKey;

    private String codeVal;

    private Integer dispOrder;

}
