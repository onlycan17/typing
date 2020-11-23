package com.palmcms.api.code;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.VO.CommonCodeDTO;
import com.palmcms.api.domain.VO.ResultVO;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = {Constants.PALMCMS_API_CODE})
public class CodeController {

  @Autowired
  CodeService codeService;

  @ResponseBody
  @GetMapping(value = {"/{category}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
  public CommonCodeResultVO code(
          @PathVariable String category
  ) {
    List<CommonCodeDTO> codes = codeService.selectCodesByCategory(category);
    return new CommonCodeResultVO(codes);
  }

}


@Getter
@Setter
class CommonCodeResultVO extends ResultVO
{
  private List<CommonCodeDTO> codes;

  public CommonCodeResultVO(List<CommonCodeDTO> codes)
  {
    this.codes = codes;
  }

}