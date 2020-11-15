package com.palmcms.api.auth;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.messages.MessageService;
import com.palmcms.api.messages.Messages;
import com.palmcms.api.security.PalmToken;
import com.palmcms.api.security.PalmTokenService;
import com.palmcms.api.security.SecurityUtils;
import com.palmcms.api.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Optional;

@Slf4j
@RestController
//@RequestMapping(value = {Constants.PALMCMS_API_AUTH})
@RequestMapping(value = {"/auth"})
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PalmTokenService tokenService;


    @PostMapping(value = {"/login"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    public LoginResultVO login(@Valid LoginDTO loginDTO, BindingResult bindingResult) {

        if ( bindingResult.hasErrors() )
        {
            return new LoginResultVO(messageService.getMessage(Messages.AUTH_INVALID_PARAMETER), bindingResult);
        }

        Optional<UserDTO> oUserDTO = userService.selectUserByUserLoginId(loginDTO.getLoginId());
        if ( oUserDTO.isEmpty() )
        {
            return new LoginResultVO("fail", messageService.getMessage(Messages.AUTH_USER_NOT_FOUND));
        }

        UserDTO userDTO = oUserDTO.get();
        if ( ! userService.matchesPassword(loginDTO.getLoginPassword(), userDTO.getUserPasswordHash()))
        {
            return new LoginResultVO("fail", messageService.getMessage(Messages.AUTH_PASSWORDS_DO_NOT_MATCH));
        }

        PalmToken palmToken = tokenService.saveToken(userDTO);
        SecurityUtils.GrantContext(palmToken);

        LoginResultVO loginResultVO = new LoginResultVO(palmToken.getToken());
        return loginResultVO;
    }

    @PostMapping(value = "/logout", produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="로그아웃", notes="로그아웃")
    public ResultVO logout(
            HttpServletRequest request
            , HttpServletResponse response
    ) {
        Optional<PalmToken> oCuboxToken = SecurityUtils.getCurrentToken();
        if (oCuboxToken.isEmpty()) {
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            return new ResultVO();
        }
        tokenService.expireToken(oCuboxToken.get());

        SecurityUtils.logout(request, response);
        return new ResultVO();
    }
}


@Getter
@Setter
class LoginDTO implements Serializable {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String loginPassword;
}


@Getter
@Setter
class LoginResultVO extends ResultVO {
    private String token;
    public LoginResultVO(String resultCode, String message) {
        super(resultCode, message);
    }
    public LoginResultVO(String message, BindingResult bindingResult)
    {
        super(message, bindingResult);
    }

    public LoginResultVO(String token)
    {
        this.token = token;
    }
}


