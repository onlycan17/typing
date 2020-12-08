package com.palmcms.api.cms;

import com.github.pagehelper.Page;
import com.palmcms.api.common.Constants;
import com.palmcms.api.domain.DTO.CmsApplicationDTO;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.DataTableVO;
import com.palmcms.api.domain.VO.PageInfoResultVO;
import com.palmcms.api.domain.VO.ResultVO;
import com.palmcms.api.messages.MessageService;
import com.palmcms.api.messages.Messages;
import com.palmcms.api.security.AuthoritiesConstants;
import com.palmcms.api.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@RestController
@Secured({AuthoritiesConstants.SYSTEM})
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_ADMIN,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_ADMIN})
public class CmsAdminController {

    @Autowired
    CmsService cmsService;

    @Autowired
    MessageService messageService;



    @PostMapping(value = {"/cms/app"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 신청서 등록", notes="CMS 신청서 등록")
    public ResultVO addApp(@Valid CmsApplicationDTO cmsApp, BindingResult bindingResult) {

        if ( bindingResult.hasErrors() )
        {
            return new ResultVO(messageService.getMessage(Messages.AUTH_INVALID_PARAMETER), bindingResult);
        }

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        cmsApp.setUserId(userDTO.getId());

        cmsService.addCmsApp(cmsApp);

        return new ResultVO();
    }

    @GetMapping(value = {"/app"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 신청이력 조회", notes="CMS 신청이력 조회")
    public ResponseEntity<DataTableVO<CmsApplicationDTO>> list(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length
            , @RequestParam(required = false) String keywordType
            , @RequestParam(required = false) String keywordText) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        //PageInfoResultVO<CmsApplicationDTO> result = new PageInfoResultVO<>(cmsService.getAppList(keywordType, keywordText, pageNum, pageSize), 10);
        int pageNum = (start / length) + 1; //Calculate page number
        Page<CmsApplicationDTO> list = cmsService.getAppList(keywordType, keywordText, pageNum, length);
        DataTableVO dataTable = new DataTableVO<>(draw, start, list.getTotal(), list.getTotal(), list);

        return ResponseEntity.ok(dataTable);
    }

    @GetMapping(value = {"/app/{appId}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="CMS 신청이력 상세 조회", notes="CMS 신청이력 상세 조회")
    public ResultVO<CmsApplicationDTO> one(@PathVariable Integer appId) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        Optional<CmsApplicationDTO> oCmsApplicationDTO = cmsService.getAppOne(appId);
        if ( oCmsApplicationDTO.isEmpty())
        {
            return new ResultVO<>(ResultVO.FAIL, "NOF FOUND");
        }

        return new ResultVO<>(oCmsApplicationDTO.get());
    }
}
