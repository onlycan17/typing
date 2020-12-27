package com.palmcms.api.csboard;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class CsBoardReplyVO implements Serializable {

    // CS_BOARD

    private Integer boardId;

    private Integer boardGroupId;

    @NotEmpty
    private String csCategoryType;

    private Integer writerUserId;

    private Timestamp creationDate;

    // CS_BOARD_LOCALE

    private String localeCode;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    // TO_USER

    private String writerUserName;

}
