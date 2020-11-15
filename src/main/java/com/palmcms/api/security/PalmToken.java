package com.palmcms.api.security;

import com.palmcms.api.domain.DTO.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.io.Serializable;
import java.util.Collection;

@Slf4j
public class PalmToken extends AbstractAuthenticationToken implements Serializable {

  private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

  private String principal;
  private String credentials;

  public PalmToken(String principal, String credentials, boolean authenticated) {
    super(null);
    this.principal = principal;
    this.credentials = credentials;
    setAuthenticated(authenticated);
  }

  public PalmToken(String principal, String credentials,
                   Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.principal = principal;
    this.credentials = credentials;
    super.setAuthenticated(true); // must use super, as we override
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  public String getToken() {
    return (String) credentials;
  }

  public UserDTO getUserDTO() {
    return (UserDTO) getDetails();
  }

  public int getUserId() {
    UserDTO userDto = getUserDTO();
    if (userDto == null) {
      return 0;
    }
    return userDto.getId();
  }

  private boolean findRole(String role) {
    for (GrantedAuthority authority : getAuthorities()) {
      if (authority.getAuthority().equals(role)) {
        return true;
      }
    }
    return false;
  }

}