package com.palmcms.api.cms;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.domain.enums.UserStatusType;
import com.palmcms.api.security.PalmToken;
import com.palmcms.api.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@PreAuthorize("hasAnyAuthority( T(com.palmcms.api.security.AuthoritiesConstants).USER)")
@RequestMapping(value = {Constants.PALMCMS_API_CMS})
public class CmsController {

    @Autowired
    CmsService cmsService;

    @GetMapping(value = {"/"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
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


}

@Getter
@Setter
class UserCmsInfoResultVO extends ResultVO
{
    UserCmsInfoVO userCmsInfo ;

    List<ManagerVO> managers;
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