package com.palmcms.api.user.controller;

import com.github.pagehelper.Page;
import com.palmcms.api.common.Constants;
import com.palmcms.api.common.excel.ExcelGenarator;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.DataTableVO;
import com.palmcms.api.security.AuthoritiesConstants;
import com.palmcms.api.security.SecurityUtils;
import com.palmcms.api.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Secured({AuthoritiesConstants.CS_ALL,AuthoritiesConstants.CS_CMS})
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_ADMIN,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_ADMIN})
public class UserAdminController {

    @Autowired
    UserService userService;

    @GetMapping(value = {"/user"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="회원목록", notes="회원목록")
    public ResponseEntity<DataTableVO<UserDTO>> userList(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length
            , @RequestParam(required = false) String keywordType
            , @RequestParam(required = false) String keywordText) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

//        PageInfoResultVO<UserDTO> result = new PageInfoResultVO<>(userService.getUserList(keywordType, keywordText, pageNum, pageSize), 10);
//        return result;
        int pageNum = (start / length) + 1; //Calculate page number
        Page<UserDTO> list = userService.getUserList(keywordType, keywordText, pageNum, length);
        DataTableVO dataTable = new DataTableVO(draw, start, list.getTotal(), list.getTotal(),
            list);

        return ResponseEntity.ok(dataTable);
    }


    @PostMapping(value = {"/excelDownload"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "회원목록엑셀다운로드", notes = "회원목록엑셀다운로드")
    public void userList(@RequestParam(value = "ItemList[]") List<String> itemList
        , @RequestParam(value = "title") String strTitle
        , @RequestParam(required = false) String keywordType
        , @RequestParam(required = false) String keywordText
        , HttpServletResponse response) throws IOException {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();
        List<UserDTO> list = userService.getUserList(keywordType, keywordText);
        String[][] dataArray = new String[list.size()][9];
        int i = 0;
        for (UserDTO userData : list) {
            String strYoung = "";
            if (userData.getYoungYn().equals("Y")) {
                strYoung = "○";
            } else {
                strYoung = "";
            }
            dataArray[i][1] = userData.getUserLoginId();
            dataArray[i][2] = userData.getUserName();
            dataArray[i][3] = userData.getUserChurchName();
            dataArray[i][4] = userData.getDepartment();
            dataArray[i][5] = userData.getPosition();
            dataArray[i][6] = strYoung;
            dataArray[i][7] = userData.getUserStatusName();
            dataArray[i][8] = userData.getCreationDate().toString();
            i++;
        }
        new ExcelGenarator().userListToExcel(dataArray, strTitle, itemList, response);

    }

    @PatchMapping(value = {"/user/approve"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="회원승인", notes="회원승인")
    public ResponseEntity approve(@RequestParam String userIdList) {
        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();
        List<String> convertedUserIdList = Stream.of(userIdList.split(",", -1)).collect(Collectors.toList());
        userService.approveForAdmin(userDTO.getId(), convertedUserIdList);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = {"/user/young"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="청소년승인", notes="청소년승인")
    public ResponseEntity young(@RequestParam String userIdList) {
        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();
        List<String> convertedUserIdList = Stream.of(userIdList.split(",", -1)).collect(Collectors.toList());
        userService.youngForAdmin(userDTO.getId(), convertedUserIdList);
        return ResponseEntity.ok().build();
    }

}
