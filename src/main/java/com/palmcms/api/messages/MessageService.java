package com.palmcms.api.messages;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class MessageService {

  @Autowired
  private MessageSource messageSource;

  public String getMessage(String key) {
    return messageSource.getMessage(key, null, Locale.KOREAN);
  }

}
