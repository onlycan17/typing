package com.palmcms.api.csboard.controller;

import com.github.pagehelper.Page;
import com.palmcms.api.common.Constants;
import com.palmcms.api.csboard.domain.CsBoard;
import com.palmcms.api.csboard.service.CsBoardService;
import com.palmcms.api.domain.VO.DataTableVO;
import com.palmcms.api.messages.LocaleCode;
import com.palmcms.api.security.AuthoritiesConstants;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@Secured({AuthoritiesConstants.CS_ALL, AuthoritiesConstants.CS_BOARD})
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_ADMIN,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_ADMIN})
public class FaqAdminController {

    @Autowired
    CsBoardService csBoardService;

    Integer FAQ_BOARD_ID = 2;


    @GetMapping(value = {"/faq"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="자주하는 문의 목록", notes="자주하는 문의 목록")
    public ResponseEntity<DataTableVO<CsBoard>> list(
            @RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length
            , @RequestAttribute("selectedLanguage") LocaleCode localeCode) {

        int pageNum = (start / length) + 1;
        Page<CsBoard> list = csBoardService.fetchAllByBoardGroupId(pageNum, length, localeCode, FAQ_BOARD_ID);
        DataTableVO dataTable = new DataTableVO(draw, start, list.getTotal(), list.getTotal(), list);

        return ResponseEntity.ok(dataTable);
    }

    @GetMapping(value = {"/faq/{boardId}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="자주하는 문의 상세", notes="자주하는 문의 상세")
    public ResponseEntity<CsBoard> detail(
            @PathVariable int boardId
            , @RequestAttribute("selectedLanguage") LocaleCode localeCode) {

        Optional<CsBoard> oCsBoardVO = csBoardService.fetchFirstByBoardGroupIdAndBoardId(localeCode, FAQ_BOARD_ID, boardId);
        if ( oCsBoardVO.isEmpty() )
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCsBoardVO.get());
    }


    @PutMapping(value = {"/faq"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="자주하는 문의 등록", notes="자주하는 문의 등록")
    public ResponseEntity<CsBoard> add(
            @PathVariable int boardId
            , @RequestAttribute("selectedLanguage") LocaleCode localeCode) {

        Optional<CsBoard> oCsBoardVO = csBoardService.fetchFirstByBoardGroupIdAndBoardId(localeCode, FAQ_BOARD_ID, boardId);
        if ( oCsBoardVO.isEmpty() )
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCsBoardVO.get());
    }
}
