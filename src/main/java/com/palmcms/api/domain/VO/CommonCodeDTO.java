package com.palmcms.api.domain.VO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommonCodeDTO implements Serializable {

    private String codeValue;

    private String codeName;

}
