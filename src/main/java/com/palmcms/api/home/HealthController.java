package com.palmcms.api.home;

import com.palmcms.api.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class HealthController {

  @ResponseBody
  @GetMapping(value = {"/health"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
  public String hello() {
    return "ok";
  }

}
