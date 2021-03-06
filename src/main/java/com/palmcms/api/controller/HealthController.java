package com.palmcms.api.controller;

import com.palmcms.api.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin(origins="*")
@Slf4j
@Controller
public class HealthController {

  @ResponseBody
  @GetMapping(value = {"/health"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
  public String hello() {
    return "ok";
  }

}
