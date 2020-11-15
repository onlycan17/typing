package com.palmcms.api.security;

import com.palmcms.api.domain.enums.UserStatusType;
import com.palmcms.api.messages.MessageService;
import com.palmcms.api.messages.Messages;
import com.palmcms.api.domain.DTO.UserRoleDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class PalmAuthenticationProvider implements AuthenticationProvider {

    private static final String[] ALLOWED_ROLES = {
            AuthoritiesConstants.SYSTEM,
            AuthoritiesConstants.USER,
            AuthoritiesConstants.MANAGER,
            AuthoritiesConstants.CUSTOMER
    };

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        Optional<UserDTO> oUserDTO = userService.selectUserByUserLoginId(loginId);

        if ( oUserDTO.isEmpty() ) {
            throw new BadCredentialsException(messageService.getMessage(Messages.AUTH_USER_NOT_FOUND));
        }

        UserDTO user = oUserDTO.get();

        if (!userService.matchesPassword(password, user.getUserPasswordHash())) {
            throw new BadCredentialsException(
                    messageService.getMessage(Messages.AUTH_PASSWORDS_DO_NOT_MATCH));
        }

        if (user.getUserStatus() != UserStatusType.CONFIRMED) {
            throw new BadCredentialsException(messageService.getMessage(Messages.AUTH_USERSTATUS_IS_NOT_APPROVED));
        }

        List<UserRoleDTO> userRoles = userService.selectUserRoles(user.getId());

        boolean hasRole = false;
        for (String allowedRole : ALLOWED_ROLES) {
            for (UserRoleDTO userRole : userRoles) {
                if (userRole.getAuthorityName().equals(allowedRole)) {
                    hasRole = true;
                    break;
                }
            }
        }

        if (!hasRole) {
            throw new BadCredentialsException(messageService.getMessage(Messages.AUTH_NOT_ALLOWED));
        }

        List<GrantedAuthority> grantedAuthorities = userRoles.stream()
                .map(userRole -> new SimpleGrantedAuthority(userRole.getAuthorityName()))
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(user, password, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
