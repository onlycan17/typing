package com.palmcms.api.csboard.controller;

import com.github.pagehelper.Page;
import com.palmcms.api.common.Constants;
import com.palmcms.api.csboard.domain.CsBoard;
import com.palmcms.api.csboard.service.CsBoardService;
import com.palmcms.api.domain.VO.DataTableVO;
import com.palmcms.api.messages.LocaleCode;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_CS,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_CS})
public class NoticeController {

    @Autowired
    CsBoardService csBoardService;

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

}
