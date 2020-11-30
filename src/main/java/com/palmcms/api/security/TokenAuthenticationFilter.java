package com.palmcms.api.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;

@Slf4j
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  @Autowired
  private PalmTokenService tokenService;

  @Autowired
  private Environment environment;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  public TokenAuthenticationFilter(String defaultFilterProcessesUrl) {
    super(defaultFilterProcessesUrl);
    super.setRequiresAuthenticationRequestMatcher(
        new AntPathRequestMatcher(defaultFilterProcessesUrl));
    setAuthenticationManager(new NoOpAuthenficationManager());
    setAuthenticationSuccessHandler(new TokenSimpleUrlAuthenticationSuccessHandler());
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
                                              HttpServletResponse httpServletResponse)
      throws AuthenticationException, IOException, ServletException {
    String token = getTokenValue(httpServletRequest, AuthoritiesConstants.HEADER_SECURITY_TOKEN);

    PalmToken userAuthenticationToken;
//    try {
      if (StringUtils.isEmpty(token)) {
        throw new AuthenticationServiceException(
            MessageFormat.format("Error | {0}", "Token is null"));
      }

      userAuthenticationToken = tokenService.getTokenByTokenKey(token);

      if (userAuthenticationToken == null) {
        log.error("token error: token is null");
        throw new AuthenticationServiceException(MessageFormat.format("Error | {0}", "Bad Token"));
      }
//    } catch (AuthenticationException e) {
//      resolver.resolveException(httpServletRequest, httpServletResponse, null, e);
//      return null;
//    }

    tokenService.renewalToken(userAuthenticationToken);

    return getAuthenticationManager().authenticate(userAuthenticationToken);
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                          FilterChain chain, Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult);
    chain.doFilter(request, response);
  }

  private String getTokenValue(HttpServletRequest req, String headerPattern) {
    return Collections.list(req.getHeaderNames()).stream()
        .filter(header -> header.equalsIgnoreCase(headerPattern))
        .map(req::getHeader)
        .findFirst()
        .orElse(null);
  }
}
