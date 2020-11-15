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
public class ResultVO {

  private Integer status = 200;
  private String message = "";
  private String error = "";

  private String resultCode = "ok";

  public static final String OK = "ok";
  public static final String FAIL = "fail";

  @JsonIgnore
  public boolean isOk() {
    return "ok".equals(resultCode);
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
    this.resultCode = "fail";
    this.message = message;
    this.error = bindingResult.getAllErrors().toString();
  }

  public ResultVO(String message, Exception ex)
  {
    this.resultCode = "fail";
    this.message = message;
    this.error = ex.toString();
  }

}
