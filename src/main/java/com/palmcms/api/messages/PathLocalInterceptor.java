package com.palmcms.api.messages;

import com.palmcms.api.common.Constants.API;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class PathLocalInterceptor extends HandlerInterceptorAdapter {

  private boolean remainLogs;

  public PathLocalInterceptor(boolean remainLogs) {
    this.remainLogs = remainLogs;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws ServletException {

    String requestUri = request.getRequestURI();

    //if (requestUri.startsWith(API.API_PREFIX) || requestUri.startsWith(API.API_V2)) {
    if (requestUri.startsWith(API.API_PREFIX) || requestUri.startsWith(API.API_NOAUTH_PREFIX)) {
      if (remainLogs) {
        log.info("start uri: {}", requestUri);
      }

      LocaleCode selectedLanguage = LocaleCode.kor;

      if (requestUri.contains("/eng/")) {
        selectedLanguage = LocaleCode.eng;
      } else if (requestUri.contains("/jpn/")) {
        selectedLanguage = LocaleCode.jpn;
      } else if (requestUri.contains("/zho/")) {
        selectedLanguage = LocaleCode.zho;
      }

      LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
      if (localeResolver == null) {
        throw new IllegalStateException(
            "No LocaleResolver found: not in a DispatcherServlet request?");
      }

      try {
        if (remainLogs) {
          log.info("{}, [{}]", requestUri, selectedLanguage);
        }

        localeResolver.setLocale(request, response, selectedLanguage.getLocale());
      } catch (IllegalArgumentException e) {
        log.error(e.getMessage());
      }

      request.setAttribute("selectedLanguage", selectedLanguage);
    }

    return true;
  }

}
