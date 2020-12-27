package com.palmcms.api.csboard.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.palmcms.api.csboard.domain.CsBoard;
import com.palmcms.api.csboard.mapper.CsBoardMapper;
import com.palmcms.api.messages.LocaleCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CsBoardService {

    @Autowired
    private CsBoardMapper csBoardMapper;

    public Page<CsBoard> fetchAllByBoardGroupId(int pageNum, int pageSize, LocaleCode localeCode, Integer boardGroupId) {
        PageHelper.startPage(pageNum, pageSize);
        return csBoardMapper.fetchAllByBoardGroupId(localeCode, boardGroupId);
    }

    public Optional<CsBoard> fetchFirstByBoardGroupIdAndBoardId(LocaleCode localeCode, Integer boardGroupId, Integer boardId) {
        return csBoardMapper.fetchFirstByBoardGroupIdAndBoardId(localeCode, boardGroupId, boardId);
    }

    public Page<CsBoard> fetchAllByBoardGroupIdAndWriteUserId(int pageNum, int pageSize, LocaleCode localeCode, Integer boardGroupId, Integer writerUserId) {
        PageHelper.startPage(pageNum, pageSize);
        return csBoardMapper.fetchAllByBoardGroupIdAndWriteUserId(localeCode, boardGroupId, writerUserId);
    }

    public Optional<CsBoard> fetchFirstByBoardGroupIdAndBoardIdAndWriteUserId(LocaleCode localeCode, Integer boardGroupId, Integer boardId, Integer writerUserId) {
        return csBoardMapper.fetchFirstByBoardGroupIdAndBoardIdAndWriteUserId(localeCode, boardGroupId, boardId, writerUserId);
    }

}
