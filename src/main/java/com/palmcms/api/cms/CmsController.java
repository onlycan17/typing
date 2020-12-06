package com.palmcms.api.cms;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.PageInfoResultVO;
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
    public ResultVO<UserCmsInfoVO> userCms() {

        Optional<PalmToken> oPalmToken = SecurityUtils.getCurrentToken();
        PalmToken palmToken = oPalmToken.get();

        UserDTO userDTO = palmToken.getUserDTO();

        UserCmsInfoVO userCmsInfoVO = cmsService.getUserCmsInfoVO(userDTO);

        return new ResultVO<>(userCmsInfoVO);
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

        cmsService.addCmsApp(cmsApp);



        return new ResultVO();
    }

    @GetMapping(value = {"/app"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 신청이력 조회", notes="CMS 신청이력 조회")
    public ResultVO<List<CmsApplicationDTO>> list() {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        return new ResultVO<>(cmsService.getAppListByUserId(userDTO.getId()));
    }

    @GetMapping(value = {"/app/{appId}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 신청이력 상세 조회", notes="CMS 신청이력 상세 조회")
    public ResultVO<CmsApplicationDTO> one(@PathVariable Integer appId) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        Optional<CmsApplicationDTO> oCmsApplicationDTO = cmsService.getAppOne(userDTO.getId(), null, appId);
        if ( oCmsApplicationDTO.isEmpty())
        {
            return new ResultVO<>(ResultVO.FAIL, "NOF FOUND");
        }

        return new ResultVO<>(oCmsApplicationDTO.get());
    }
}

