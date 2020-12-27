package com.palmcms.api.csboard;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class CsBoardReplyRegDTO implements Serializable {

    // CS_BOARD

    private Integer id;

    private Integer boardGroupId;

    @NotEmpty
    private String csCategoryType;


    private Integer writerUserId;

    private String korTitle;

    private String korContent;

    private String engTitle;

    private String engContent;

    private String jpnTitle;

    private String jpnContent;

    private String zhoTitle;

    private String zhoContent;


}
