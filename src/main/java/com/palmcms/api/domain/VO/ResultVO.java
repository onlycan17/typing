package com.palmcms.api.domain.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.BindingResult;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ResultVO<T> {

  public static final String OK = "ok";
  public static final String FAIL = "fail";

  private Integer status = 200;
  private String message = "";
  private String error = "";
  private T data = null;

  private String resultCode = ResultVO.OK;

  @JsonIgnore
  public boolean isOk() {
    return ResultVO.OK.equals(resultCode);
  }

  public ResultVO(String resultCode, String message) {
    this.resultCode = resultCode;
    this.message = message;
  }

  public ResultVO(String resultCode, String message, String error) {
    this.resultCode = resultCode;
    this.message = message;
    this.error = error;
  }

  public ResultVO(String message, BindingResult bindingResult)
  {
    this.resultCode = ResultVO.FAIL;
    this.message = message;
    this.error = bindingResult.getAllErrors().toString();
  }

  public ResultVO(String message, Exception ex)
  {
    this.resultCode = ResultVO.FAIL;
    this.message = message;
    this.error = ex.toString();
  }

}
