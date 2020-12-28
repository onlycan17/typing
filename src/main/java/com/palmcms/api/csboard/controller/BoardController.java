package com.palmcms.api.csboard.controller;

import com.palmcms.api.common.Constants;
import com.palmcms.api.csboard.service.CsBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_USER,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_USER})
public class BoardController {

    @Autowired
    CsBoardService boardService;


}
