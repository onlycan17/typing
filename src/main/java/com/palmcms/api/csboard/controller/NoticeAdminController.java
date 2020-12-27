package com.palmcms.api.csboard.controller;

import com.github.pagehelper.Page;
import com.palmcms.api.common.Constants;
import com.palmcms.api.csboard.CsBoardRegDTO;
import com.palmcms.api.csboard.domain.CsBoard;
import com.palmcms.api.csboard.domain.CsBoardLocale;
import com.palmcms.api.csboard.service.CsBoardService;
import com.palmcms.api.domain.VO.DataTableVO;
import com.palmcms.api.messages.LocaleCode;
import com.palmcms.api.security.AuthoritiesConstants;
import com.palmcms.api.service.LocaleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@Slf4j
@RestController
@Secured({AuthoritiesConstants.CS_ALL, AuthoritiesConstants.CS_BOARD})
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_ADMIN,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_ADMIN})
public class NoticeAdminController {

    @Autowired
    CsBoardService csBoardService;

    @Autowired
    LocaleService localeService;

    Integer NOTICE_BOARD_ID = 1;


    @GetMapping(value = {"/notice"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="공지사항 목록", notes="공지사항 목록")
    public ResponseEntity<DataTableVO<CsBoard>> list(
            @RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length
            , @RequestAttribute("selectedLanguage") LocaleCode localeCode) {

        int pageNum = (start / length) + 1;
        Page<CsBoard> list = csBoardService.fetchAllByBoardGroupId(pageNum, length, localeCode, NOTICE_BOARD_ID);
        DataTableVO dataTable = new DataTableVO(draw, start, list.getTotal(), list.getTotal(), list);

        return ResponseEntity.ok(dataTable);
    }

    @GetMapping(value = {"/notice/{boardId}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="공지사항 상세", notes="공지사항 상세")
    public ResponseEntity<CsBoard> detail(
            @PathVariable int boardId
            , @RequestAttribute("selectedLanguage") LocaleCode localeCode) {

        Optional<CsBoard> oCsBoardVO = csBoardService.fetchFirstByBoardGroupIdAndBoardId(localeCode, NOTICE_BOARD_ID, boardId);
        if ( oCsBoardVO.isEmpty() )
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCsBoardVO.get());
    }

    @PutMapping(value = {"/notice"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="공지사항 등록", notes="공지사항 등록")
    public ResponseEntity add(
            @RequestAttribute("selectedLanguage") LocaleCode localeCode
            , @ModelAttribute CsBoardRegDTO board) {

        if (StringUtils.isEmpty(board.getKorTitle())
                && StringUtils.isEmpty(board.getEngTitle())
                && StringUtils.isEmpty(board.getJpnTitle())
                && StringUtils.isEmpty(board.getZhoTitle())
        ) {
            return ResponseEntity.badRequest().build();
        }
        if (StringUtils.isEmpty(board.getKorContent())
                && StringUtils.isEmpty(board.getEngContent())
                && StringUtils.isEmpty(board.getJpnContent())
                && StringUtils.isEmpty(board.getZhoContent())
        ) {
            return ResponseEntity.badRequest().build();
        }

        CsBoard csBoard = new CsBoard();

        boolean isValid = false;
        if (!StringUtils.isEmpty(board.getKorTitle()) && !StringUtils.isEmpty(board.getKorContent()) ) {
            CsBoardLocale locale = new CsBoardLocale();
            locale.setLocale(localeService.getLocaleByLocaleCode(LocaleCode.kor));
            locale.setTitle(board.getKorTitle());
            locale.setContent(board.getKorContent());
            csBoard.getCsBoardLocales().add(locale);
            isValid = true;
        }
        if (!StringUtils.isEmpty(board.getEngTitle()) && !StringUtils.isEmpty(board.getEngContent()) ) {
            CsBoardLocale locale = new CsBoardLocale();
            locale.setLocale(localeService.getLocaleByLocaleCode(LocaleCode.eng));
            locale.setTitle(board.getEngTitle());
            locale.setContent(board.getEngContent());
            csBoard.getCsBoardLocales().add(locale);
            isValid = true;
        }
        if (!StringUtils.isEmpty(board.getJpnTitle()) && !StringUtils.isEmpty(board.getJpnContent()) ) {
            CsBoardLocale locale = new CsBoardLocale();
            locale.setLocale(localeService.getLocaleByLocaleCode(LocaleCode.jpn));
            locale.setTitle(board.getJpnTitle());
            locale.setContent(board.getJpnContent());
            csBoard.getCsBoardLocales().add(locale);
            isValid = true;
        }
        if (!StringUtils.isEmpty(board.getZhoTitle()) && !StringUtils.isEmpty(board.getZhoContent()) ) {
            CsBoardLocale locale = new CsBoardLocale();
            locale.setLocale(localeService.getLocaleByLocaleCode(LocaleCode.zho));
            locale.setTitle(board.getZhoTitle());
            locale.setContent(board.getZhoContent());
            csBoard.getCsBoardLocales().add(locale);
            isValid = true;
        }

        csBoard.setPublicYn(board.getPublicYn());

        if (!isValid) {
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = {"/notice/{boardId}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="공지사항 수정", notes="공지사항 수정")
    public ResponseEntity mod(
            @PathVariable int boardId
            , @RequestAttribute("selectedLanguage") LocaleCode localeCode) {

        Optional<CsBoard> oCsBoardVO = csBoardService.fetchFirstByBoardGroupIdAndBoardId(localeCode, NOTICE_BOARD_ID, boardId);
        if ( oCsBoardVO.isEmpty() )
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCsBoardVO.get());
    }
}
