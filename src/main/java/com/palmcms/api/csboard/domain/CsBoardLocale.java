package com.palmcms.api.csboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.palmcms.api.domain.Locale;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CS_BOARD_LOCALE")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.csBoard",
                joinColumns = @JoinColumn(name = "boardId")),
        @AssociationOverride(name = "primaryKey.locale",
                joinColumns = @JoinColumn(name = "localeCode"))})
public class CsBoardLocale implements Serializable {

    // composite-id key
    @EmbeddedId
    private CsBoardLocaleId primaryKey = new CsBoardLocaleId();

    @Transient
    public CsBoard getCsBoard() {
        return getPrimaryKey().getCsBoard();
    }

    public void setCsBoard(CsBoard csBoard) {
        if (primaryKey == null) {
            primaryKey = new CsBoardLocaleId();
        }
        primaryKey.setCsBoard(csBoard);
    }

    @Transient
    public Locale getLocale() {
        return getPrimaryKey().getLocale();
    }

    public void setLocale(Locale locale) {
        if (primaryKey == null) {
            primaryKey = new CsBoardLocaleId();
        }
        primaryKey.setLocale(locale);
    }

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

}
