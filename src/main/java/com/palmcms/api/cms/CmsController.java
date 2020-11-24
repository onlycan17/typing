package com.palmcms.api.cms;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.domain.enums.UserStatusType;
import com.palmcms.api.messages.MessageService;
import com.palmcms.api.messages.Messages;
import com.palmcms.api.security.AuthoritiesConstants;
import com.palmcms.api.security.PalmToken;
import com.palmcms.api.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_CMS,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_CMS})
public class CmsController {

    @Autowired
    CmsService cmsService;

    @Autowired
    MessageService messageService;


    @GetMapping(value = {"/info"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 정보조회", notes="CMS 정보조회")
    public UserCmsInfoResultVO userCms() {

        Optional<PalmToken> oPalmToken = SecurityUtils.getCurrentToken();
        PalmToken palmToken = oPalmToken.get();

        UserDTO userDTO = palmToken.getUserDTO();


        // 사용자정보 with 미납금
        Optional<UserVO> oUserVO = cmsService.selectUserCmssInfoByUserId(userDTO.getId());
        // CMS정보
        Optional<CmsUserVO> oCmsUserVO = cmsService.selectCmsInfoByPayerNo(userDTO.getPayerNo());
        // 담당 매니저 목록
        List<ManagerVO> managers = cmsService.selectManagersByChurchId(userDTO.getChurchId());
        // 신청서 목록
        List<CmsApplicationDTO> applications = cmsService.selectApplicationsByUserId(userDTO.getId());

        UserCmsInfoResultVO userCmsInfoResultVO = new UserCmsInfoResultVO();
        userCmsInfoResultVO.setUser(oUserVO.get());
        if (oCmsUserVO.isPresent())
        {
            userCmsInfoResultVO.setCmsUser(oCmsUserVO.get());
        }
        userCmsInfoResultVO.setManagers(managers);
        userCmsInfoResultVO.setApplication(applications);

        return userCmsInfoResultVO;
    }

    @PostMapping(value = {"/app"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 신청서 등록", notes="CMS 신청서 등록")
    public ResultVO addApp(@Valid CmsApplicationDTO cmsApp, BindingResult bindingResult) {

        if ( bindingResult.hasErrors() )
        {
            return new ResultVO(messageService.getMessage(Messages.AUTH_INVALID_PARAMETER), bindingResult);
        }

        Optional<PalmToken> oPalmToken = SecurityUtils.getCurrentToken();
        PalmToken palmToken = oPalmToken.get();

        UserDTO userDTO = palmToken.getUserDTO();

        cmsApp.setUserId(userDTO.getId());

        cmsService.insertCmsApp(cmsApp);



        return new ResultVO();
    }

}

@Getter
@Setter
class UserCmsInfoResultVO extends ResultVO
{
    UserVO user ;

    CmsUserVO cmsUser ;

    List<ManagerVO> managers;

    List<CmsApplicationDTO> application;

}

@Getter
@Setter
class UserVO
{
    private String userLoginId;

    private String userName;

    private String proviName;

    private String nickName;

    private String contactNo;

    private String socialRegNumber;

    private  String churchName;

    private UserStatusType UserStatus;

    private UserStatusType UserStatusName;

    private int unpaidAmt;
}

@Getter
@Setter
class CmsUserVO
{
    private String payerNo;

    private String payerName;

    private String contactNo;

    private String socialRegNumber;

    private String status;

}

@Getter
@Setter
class ManagerVO
{
    private String managerName;

    private String managerContactNo;

}


@Getter
@Setter
class UserCmsVO
{
    private String cmsStatus;

    private String userName;

    private String proviName;

    private String nickName;

    private String contactNo;

    private String socialRegNumber;

    private  String churchName;

    private UserStatusType UserStatus;

    private UserStatusType UserStatusName;

    private int unpaidAmt;
}


//
//
//@Getter
//@Setter
//class CmsApplicationVO
//{
//    private String appStatus;
//
//    private String appStatusName;
//
//    private String payerNo;
//
//    private String payerName;
//
//    private String payerContactNo;
//
//    private String payerSocialRegNumber;
//}