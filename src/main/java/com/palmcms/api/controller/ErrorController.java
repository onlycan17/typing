package com.palmcms.api.controller;

import com.palmcms.api.messages.LocaleCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @author by dotkebi on 2017. 11. 25..
 */
@RestController
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

  @RequestMapping({"/error"})
  public ResponseEntity error(
      Model model
      , HttpServletRequest request
      , @PathVariable(required = false) String language
      , @Value("#{request.getAttribute('selectedLanguage')}") LocaleCode localeCode
  ) {
    if (localeCode == null) {
      localeCode = LocaleCode.kor;
    }
    model.addAttribute("selectedLanguage", localeCode);

    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    if (status == null) {
      return ResponseEntity.status(500).build();
    }

    int statusCode = Integer.valueOf(status.toString());

//    if (statusCode == HttpStatus.NOT_FOUND.value()) {
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
//      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//    } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//    }
//
//    return ResponseEntity.status(500).build();
    return ResponseEntity.status(statusCode).build();

  }

  @Override
  public String getErrorPath() {
    return "/error";
  }

}
