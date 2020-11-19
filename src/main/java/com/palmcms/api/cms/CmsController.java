package com.palmcms.api.cms;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.domain.enums.UserStatusType;
import com.palmcms.api.messages.MessageService;
import com.palmcms.api.messages.Messages;
import com.palmcms.api.security.PalmToken;
import com.palmcms.api.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = {Constants.PALMCMS_API_CMS})
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


        Optional<UserCmsInfoVO> oUserCmsInfoVO = cmsService.selectUserCmssInfoByUserId(userDTO.getId());
        List<ManagerVO> managers = cmsService.selectManagersByChurchId(userDTO.getChurchId());

        UserCmsInfoResultVO userCmsInfoResultVO = new UserCmsInfoResultVO();
        userCmsInfoResultVO.setUserCmsInfo(oUserCmsInfoVO.get());
        userCmsInfoResultVO.setManagers(managers);
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
    UserCmsInfoVO userCmsInfo ;

    List<ManagerVO> managers;

    List<CmsApplicationVO> application;

}

@Getter
@Setter
class UserCmsInfoVO
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
class ManagerVO
{
    private String managerName;

    private String managerContactNo;

}


@Getter
@Setter
class CmsApplicationVO
{
    private String appStatus;

    private String appStatusName;

    private String payerNo;

    private String payerName;

    private String payerContactNo;

    private String payerSocialRegNumber;
}