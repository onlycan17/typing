package com.palmcms.api.domain.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonCodeDTO implements Serializable {

    private String codeGroup;

    private String code;

    private String codeName;

    private Integer dispOrder;

}
