package com.palmcms.api.user;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.security.AuthoritiesConstants;
import com.palmcms.api.security.PalmToken;
import com.palmcms.api.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_USER,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_USER})
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/profile"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="사용자 정보조회", notes="사용자 정보조회")
    public UserProfileResultVO profile() {

        Optional<PalmToken> oPalmToken = SecurityUtils.getCurrentToken();
        PalmToken palmToken = oPalmToken.get();

        UserDTO userDTO = palmToken.getUserDTO();

        UserProfileResultVO userProfileResultVO = new UserProfileResultVO();
        userProfileResultVO.setUserProfile(userDTO);
//        userProfileResultVO.setRoles(cuboxToken.getAuthorities().toArray());
        userProfileResultVO.setRoles(userService.selectUserRoleNames(userDTO.getId()));
        return userProfileResultVO;
    }


}

@Getter
@Setter
class UserProfileResultVO extends ResultVO
{
    UserDTO userProfile ;

    List<String> roles;
}