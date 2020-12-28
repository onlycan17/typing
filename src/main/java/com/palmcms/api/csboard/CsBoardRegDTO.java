package com.palmcms.api.csboard;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class CsBoardRegDTO implements Serializable {

    private Integer id;

    private String csCategoryType;

    private String publicYn;

    private String korTitle;

    private String korContent;

    private String engTitle;

    private String engContent;

    private String jpnTitle;

    private String jpnContent;

    private String zhoTitle;

    private String zhoContent;


}
