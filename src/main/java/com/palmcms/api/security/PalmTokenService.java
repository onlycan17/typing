package com.palmcms.api.security;

import com.palmcms.api.domain.DTO.UserRoleDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.user.UserService;
import com.palmcms.api.domain.DTO.UserTokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
@Service
public class PalmTokenService implements TokenService {

  @Autowired
  private UserService userService;

  @Override
  public Token allocateToken(String s) {
    return null;
  }

  @Override
  public Token verifyToken(String s) {
    return null;
  }

  @Value("${palmcms.tokenTimeoutMinute}")
  private int tokenTimeoutMinute;


  private Timestamp getExpiredDate(int minutes)
  {
    Timestamp ts = new Timestamp(System.currentTimeMillis());
    Calendar cal = Calendar.getInstance();
    cal.setTime(ts);
    cal.add(Calendar.MINUTE, minutes);
    ts.setTime(cal.getTime().getTime()); // or
    return ts;
  }

  public PalmToken saveToken(UserDTO userDTO) {

    String token = UUID.randomUUID().toString();

    UserTokenDTO userTokenDTO = new UserTokenDTO();
    userTokenDTO.setUserId(userDTO.getId());
    userTokenDTO.setToken(token);
    userTokenDTO.setExpiredDate(getExpiredDate(tokenTimeoutMinute));

    userService.insertUserToken(userTokenDTO);

    PalmToken palmToken = new PalmToken(userDTO.getUserLoginId(), token, convertAuthorities(userDTO.getId()));
    palmToken.setDetails(userDTO);

    return palmToken;
  }

  public PalmToken getTokenByTokenKey(String token) {

    Optional<UserTokenDTO> oUserTokenDTO = userService.selectUserToken(token);

    if (oUserTokenDTO.isEmpty()) {
      throw new BadCredentialsException("TokenService, Invalid token : " + token);
    }

    UserTokenDTO userTokenDTO = oUserTokenDTO.get();

    Optional<UserDTO> oUserDTO = userService.selectUserById(userTokenDTO.getUserId());
    if (oUserDTO.isEmpty()) {
      throw new BadCredentialsException("TokenService, Invalid token or userId : " + token + ", " + userTokenDTO.getUserId().toString());
    }

    UserDTO userDTO = oUserDTO.get();

    PalmToken palmToken = new PalmToken(userDTO.getUserLoginId(), token, convertAuthorities(userDTO.getId()));
    palmToken.setDetails(userDTO);
    return palmToken;
  }

  public void expireToken(PalmToken palmToken) {
    UserTokenDTO userTokenDTO = new UserTokenDTO();
    userTokenDTO.setUserId(palmToken.getUserDTO().getId());
    userTokenDTO.setToken(palmToken.getToken());
    userTokenDTO.setExpiredDate(getExpiredDate(0));
    userService.updateUserTokenExpiredDate(userTokenDTO);
  }

  public boolean renewalToken(PalmToken palmToken) {

    UserTokenDTO userTokenDTO = new UserTokenDTO();
    userTokenDTO.setUserId(palmToken.getUserDTO().getId());
    userTokenDTO.setToken(palmToken.getToken());
    userTokenDTO.setExpiredDate(getExpiredDate(tokenTimeoutMinute));
    userService.updateUserTokenExpiredDate(userTokenDTO);

    return true;
  }


  private Collection<GrantedAuthority> convertAuthorities(int userId) {
    Set<GrantedAuthority> authorities = new HashSet<> (
        Collections.singletonList(new SimpleGrantedAuthority(AuthoritiesConstants.USER)));

    List<UserRoleDTO> roles = userService.selectUserRoles(userId);

    for(UserRoleDTO role : roles)
    {
      if ( role.getAuthorityName().equals(AuthoritiesConstants.MANAGER))
      {
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.MANAGER));
      }
      else if ( role.getAuthorityName().equals(AuthoritiesConstants.CUSTOMER))
      {
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.CUSTOMER));
      }
      else if ( role.getAuthorityName().equals(AuthoritiesConstants.SYSTEM))
      {
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.SYSTEM));
      }


    }
    return authorities;
  }
}
