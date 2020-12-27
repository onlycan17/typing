package com.palmcms.api.csboard.controller;

import com.palmcms.api.common.Constants;
import com.palmcms.api.csboard.service.CsBoardService;
import com.palmcms.api.security.AuthoritiesConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Secured({AuthoritiesConstants.MANAGER})
@RequestMapping({Constants.API.API_PREFIX + Constants.API.API_MANAGER,
        Constants.API.API_LANGUAGE_PREFIX + Constants.API.API_MANAGER})
public class BoardManagerController {

    @Autowired
    CsBoardService boardService;

}
