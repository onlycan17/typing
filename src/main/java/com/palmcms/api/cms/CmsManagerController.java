package com.palmcms.api.cms;

import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.PageInfoResultVO;
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
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_MANAGER,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_MANAGER})
public class CmsManagerController {

    @Autowired
    CmsService cmsService;

    @Autowired
    MessageService messageService;


    @GetMapping(value = {"/cms"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 정보조회", notes="CMS 정보조회")
    public ResultVO<UserCmsInfoVO> userCms(@RequestParam Integer userId) {

        UserCmsInfoVO userCmsInfoVO = cmsService.getUserCmsInfoVO(userId);

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
    public PageInfoResultVO<CmsApplicationDTO> list(
            @RequestParam(required = false) String keywordType
            , @RequestParam(required = false) String keywordText
            , @RequestParam(required = false, defaultValue = "1") int pageNum
            , @RequestParam(required = false, defaultValue = "10") int pageSize) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        PageInfoResultVO<CmsApplicationDTO> result = new PageInfoResultVO<>(cmsService.getAppList(null, userDTO.getChurchId(), keywordType, keywordText, pageNum, pageSize), 10);

        return result;
    }

    @GetMapping(value = {"/app/{appId}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 신청이력 상세 조회", notes="CMS 신청이력 상세 조회")
    public ResultVO<CmsApplicationDTO> one(@PathVariable Integer appId) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        Optional<CmsApplicationDTO> oCmsApplicationDTO = cmsService.getAppOne(userDTO.getId(), appId);
        if ( oCmsApplicationDTO.isEmpty())
        {
            return new ResultVO<>(ResultVO.FAIL, "NOF FOUND");
        }

        return new ResultVO<>(oCmsApplicationDTO.get());
    }
}
