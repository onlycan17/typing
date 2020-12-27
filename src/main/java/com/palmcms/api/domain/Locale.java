package com.palmcms.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author by ray on 2017. 1. 14..
 */
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "TO_LOCALE")
public class Locale implements Serializable {

  private static final long serialVersionUID = 225309834566906464L;

  @NotNull
  @Size(min = 0, max = 10)
  @Id
  @Column(name = "localeCode")
  private String localeCode;

  @NotNull
  @Column(name = "orderNo")
  private Integer orderNo;

  @Override
  public String toString() {
    return localeCode;
  }

  public Locale(
      @NotNull @Size(min = 0, max = 10) String localeCode,
      @NotNull Integer orderNo) {
    this.localeCode = localeCode;
    this.orderNo = orderNo;
  }
}
