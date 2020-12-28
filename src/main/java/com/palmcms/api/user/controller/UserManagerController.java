package com.palmcms.api.user.controller;

import com.github.pagehelper.Page;
import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.DataTableVO;
import com.palmcms.api.security.AuthoritiesConstants;
import com.palmcms.api.security.SecurityUtils;
import com.palmcms.api.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RestController
@Secured({AuthoritiesConstants.MANAGER})
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_MANAGER,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_MANAGER})
public class UserManagerController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/user"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="회원목록", notes="회원목록")
    public ResponseEntity<DataTableVO<UserDTO>> userList(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length
            , @RequestParam(required = false) String keywordType
            , @RequestParam(required = false) String keywordText) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

//        PageInfoResultVO<UserDTO> result = new PageInfoResultVO<>(userService.getUserListByManagerUserId(userDTO.getId(), keywordType, keywordText, pageNum, pageSize), 10);
//        return result;

        int pageNum = (start / length) + 1; //Calculate page number
        Page<UserDTO> list = userService.getUserListByManagerUserId(userDTO.getId(), keywordType, keywordText, pageNum, length);
        DataTableVO dataTable = new DataTableVO(draw, start, list.getTotal(), list.getTotal(), list);

        return ResponseEntity.ok(dataTable);

    }

    @PatchMapping(value = {"/user/approve"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="회원승인", notes="회원승인")
    public ResponseEntity approve(@RequestParam String userIdList) {
        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();
        List<String> convertedUserIdList = Stream.of(userIdList.split(",", -1)).collect(Collectors.toList());
        userService.approveForManager(userDTO.getId(), convertedUserIdList);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = {"/user/young"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="청소년승인", notes="청소년승인")
    public ResponseEntity young(@RequestParam String userIdList) {
        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();
        List<String> convertedUserIdList = Stream.of(userIdList.split(",", -1)).collect(Collectors.toList());
        userService.youngForManager(userDTO.getId(), convertedUserIdList);
        return ResponseEntity.ok().build();
    }
}
