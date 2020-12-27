package com.palmcms.api.csboard.controller;

import com.github.pagehelper.Page;
import com.palmcms.api.common.Constants;
import com.palmcms.api.csboard.CsBoardVO;
import com.palmcms.api.csboard.domain.CsBoard;
import com.palmcms.api.csboard.service.CsBoardService;
import com.palmcms.api.domain.DTO.UserDTO;
import com.palmcms.api.domain.VO.DataTableVO;
import com.palmcms.api.messages.LocaleCode;
import com.palmcms.api.security.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_CS,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_CS})
public class QnaController {

    @Autowired
    CsBoardService csBoardService;

    Integer QNA_BOARD_ID = 3;


    @GetMapping(value = {"/"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="문의하기 목록", notes="문의하기 목록")
    public ResponseEntity<DataTableVO<CsBoard>> list(
            @RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        int pageNum = (start / length) + 1;
        LocaleCode localeCode = LocaleCode.kor;
        Page<CsBoard> list = csBoardService.fetchAllByBoardGroupIdAndWriteUserId(pageNum, length, localeCode, QNA_BOARD_ID, userDTO.getId());
        DataTableVO dataTable = new DataTableVO(draw, start, list.getTotal(), list.getTotal(), list);

        return ResponseEntity.ok(dataTable);
    }

    @GetMapping(value = {"/{boardId}"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="문의하기 상세", notes="문의하기 상세")
    public ResponseEntity<CsBoard> detail(@PathVariable int boardId) {

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();

        LocaleCode localeCode = LocaleCode.kor;
        Optional<CsBoard> oCsBoardVO = csBoardService.fetchFirstByBoardGroupIdAndBoardIdAndWriteUserId(localeCode, QNA_BOARD_ID, boardId, userDTO.getId());
        if ( oCsBoardVO.isEmpty() )
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oCsBoardVO.get());
    }



    @PutMapping(value = {"/"}, produces = Constants.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value="문의하기 등록", notes="문의하기 등록")
    public ResponseEntity<CsBoardVO> add(@ModelAttribute @Valid CsBoardVO dto
            , BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
//            model.addAttribute("message", CommonUtils.extractFieldError(bindingResult));
//            return "message_back";
        }

        UserDTO userDTO = SecurityUtils.getCurrentToken().get().getUserDTO();



//        Optional<CsBoardVO> oCsBoardVO = csBoardService.fetchFirstByBoardGroupIdAndBoardIdAndWriteUserId(localeCode, QNA_BOARD_ID, boardId, userDTO.getId());
//        if ( oCsBoardVO.isEmpty() )
//        {
//            return ResponseEntity.notFound().build();
//        }
        return ResponseEntity.ok().build();
    }
}
