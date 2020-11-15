package com.palmcms.api.domain.enums;

/**
 * @author by dotkebi on 2018. 1. 7..
 */
public enum UserStatusType implements EnumModel {

  NULL(""),
  NEW("신청"),
  PISNET("피스넷확인"),
  CONFIRMED("승인"),
  REJECT("중지");

  private String description;

  UserStatusType(String description) {
    this.description = description;
  }

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return description;
  }

}
