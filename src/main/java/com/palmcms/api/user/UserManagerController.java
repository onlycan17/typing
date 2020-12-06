package com.palmcms.api.user;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.PageInfoResultVO;
import com.palmcms.api.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_MANAGER,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_MANAGER})
public class UserManagerController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/user"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="회원목록", notes="회원목록")
    public PageInfoResultVO<UserDTO> userList(
            @RequestParam(required = false) String keywordType
            , @RequestParam(required = false) String keywordText
            , @RequestParam(required = false, defaultValue = "1") int pageNum
            , @RequestParam(required = false, defaultValue = "10") int pageSize) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        PageInfoResultVO<UserDTO> result = new PageInfoResultVO<>(userService.getUserList(userDTO.getChurchId(), keywordType, keywordText, pageNum, pageSize), 10);
        return result;
    }


}
