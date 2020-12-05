package com.palmcms.api.domain.VO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.validation.BindingResult;

import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class PageInfoResultVO<T> extends PageInfo<T> {

  public static final String OK = "ok";
  public static final String FAIL = "fail";

  private Integer status = 200;
  private String message = "";
  private String error = "";
  private T data = null;

  private String resultCode = PageInfoResultVO.OK;

  @JsonIgnore
  public boolean isOk() {
    return PageInfoResultVO.OK.equals(resultCode);
  }

  public PageInfoResultVO(List<T> list, int navigatePages)
  {
    super(list, navigatePages);
  }


  public PageInfoResultVO(String resultCode, String message) {
    this.resultCode = resultCode;
    this.message = message;
  }

  public PageInfoResultVO(String resultCode, String message, String error) {
    this.resultCode = resultCode;
    this.message = message;
    this.error = error;
  }

  public PageInfoResultVO(String message, BindingResult bindingResult)
  {
    this.resultCode = PageInfoResultVO.FAIL;
    this.message = message;
    this.error = bindingResult.getAllErrors().toString();
  }

  public PageInfoResultVO(String message, Exception ex)
  {
    this.resultCode = PageInfoResultVO.FAIL;
    this.message = message;
    this.error = ex.toString();
  }

}
