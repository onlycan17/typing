package com.palmcms.api.csboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.palmcms.api.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CS_BOARD_REPLY")
public class CsBoardReply implements BaseEntity<Integer>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "boardId")
    private Integer boardId;

    @Column(name = "content")
    private String content;

    @Column(name = "adminYn")
    private String adminYn;

    @Column(name = "writerUserId")
    private Integer writerUserId;

    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "deleteYn")
    private String deleteYn;

    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "creationDate")
    private Timestamp creationDate;

    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "modificationDate")
    private Timestamp modificationDate;

}
