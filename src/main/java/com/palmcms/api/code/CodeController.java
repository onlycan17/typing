package com.palmcms.api.code;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.VO.CommonCodeDTO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.messages.LocaleCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="*")
@Slf4j
@Controller
@RequestMapping({Constants.API.API_NOAUTH_PREFIX + Constants.API.API_CODE,
        Constants.API.API_LANGUAGE_NOAUTH_PREFIX + Constants.API.API_CODE})
public class CodeController {

  @Autowired
  CodeService codeService;

  @ResponseBody
  @GetMapping(value = {"/{category}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
  public ResultVO<List<CommonCodeDTO>> code(
          @PathVariable String category
          , @Value("#{request.getAttribute('selectedLanguage')}") LocaleCode localeCode
  ) {
    List<CommonCodeDTO> codes = codeService.selectCodesByCategory(category, localeCode);
    return new ResultVO<>(codes);
  }

}
