package com.palmcms.api.user.controller;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.security.SecurityUtils;
import com.palmcms.api.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_USER,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_USER})
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/profile"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="사용자 정보조회", notes="사용자 정보조회")
    public ResultVO<UserDTO> profile() {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        return new ResultVO<>(userDTO);
    }

    @GetMapping(value = {"/roles"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="사용자 권한 조회", notes="사용자 권한 조회")
    public ResultVO<List<String>> roles() {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        List<String> roles = userService.selectUserRoleNames(userDTO.getId());
        return new ResultVO<>(roles);
    }


}
