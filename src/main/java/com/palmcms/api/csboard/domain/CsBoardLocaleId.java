package com.palmcms.api.csboard.domain;

import com.palmcms.api.domain.Locale;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Embeddable
public class CsBoardLocaleId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    private CsBoard csBoard;

    @ManyToOne(cascade = CascadeType.ALL)
    private Locale locale;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CsBoardLocaleId that = (CsBoardLocaleId) o;
        return Objects.equals(csBoard, that.csBoard) &&
                Objects.equals(locale, that.locale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(csBoard, locale);
    }
}
