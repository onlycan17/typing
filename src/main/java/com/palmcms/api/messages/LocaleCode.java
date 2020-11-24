package com.palmcms.api.messages;

import java.util.Locale;

/**
 * @author by dotkebi on 2018. 8. 4..
 */
public enum LocaleCode {
  kor(Locale.KOREAN),
  eng(Locale.ENGLISH),
  jpn(Locale.JAPANESE),
  zho(Locale.CHINESE);

  private Locale locale;

  LocaleCode(Locale locale) {
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getValue() {
    return this.name().toLowerCase();
  }

}
