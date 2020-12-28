package com.palmcms.api.csboard;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class CsBoardVO implements Serializable {

    private Integer boardId;

    private String title;

    private String content;

    private String openYn;

    private String closeYn;

    private String closeDay;

    private Timestamp creationDate;

}
