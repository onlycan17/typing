package com.palmcms.api.csboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.palmcms.api.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CS_BOARD")
public class CsBoard implements BaseEntity<Integer>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "boardGroupId")
    private Integer boardGroupId;

    @NotNull
    @Column(name = "csCategoryType")
    private String csCategoryType;

    @Column(name = "inquiryNumber")
    private String inquiryNumber;

    @Column(name = "closeDate")
    private String closeDay;

    @Column(name = "publicYn")
    private String publicYn;

    @Column(name = "writerUserId")
    private Integer writerUserId;

    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "deleteYn")
    private String deleteYn;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    @JsonIgnore
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Column(name = "modificationDate")
    private Timestamp modificationDate;

    @JsonIgnore
    @OneToMany(mappedBy = "primaryKey.csBoard", cascade = CascadeType.ALL)
    private Set<CsBoardLocale> csBoardLocales = new HashSet<>();
}
